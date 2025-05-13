package com.intprog32.caterspot30

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.intprog32.caterspot30.Adapters.CatererAdapter
import com.intprog32.caterspot30.Data.CatererData
import com.intprog32.caterspot30.Intefaces.RetrofitInstance
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardActivity : Activity() {
    private lateinit var recyclerView: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val settingIcon =  findViewById<ImageView>(R.id.nav_settings)
        val addBtn = findViewById<FloatingActionButton>(R.id.add_button)
        val searchBar = findViewById<EditText>(R.id.search_bar)
        val searchButton = findViewById<ImageView>(R.id.search_button)

        addBtn.setOnClickListener{
            startActivity(
                Intent(this, AddActivity::class.java)
            )
        }

        searchButton.setOnClickListener {
            val query = searchBar.text.toString().trim()
            if (query.isEmpty()) {
                Toast.makeText(this, "Please enter a search term", Toast.LENGTH_SHORT).show()
            } else {
                performSearch(query)
            }
        }

        recyclerView = findViewById(R.id.caterer_recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Inside your onCreate or appropriate method
        RetrofitInstance.api.getCaterers().enqueue(object : Callback<List<CatererData>> {
            override fun onResponse(call: Call<List<CatererData>>, response: Response<List<CatererData>>) {
                if (response.isSuccessful) {
                    val caterersFromApi = response.body() ?: emptyList()

                    if (caterersFromApi.isEmpty()) {
                        Toast.makeText(this@DashboardActivity, "No caterers available.", Toast.LENGTH_LONG).show()
                        return
                    }

                    val uniqueCaterers = caterersFromApi.distinctBy { it.name }

                    val adapter = CatererAdapter(
                        uniqueCaterers.toMutableList(),
                        onItemClick = { selectedCaterer ->
                            val intent = Intent(this@DashboardActivity, CatererDetailsActivity::class.java)
                            intent.putExtra("caterer", selectedCaterer)
                            startActivity(intent)
                        },
                        onItemLongClick = { caterer ->
                            showDeleteDialog(caterer)
                        }
                    )
                    recyclerView.adapter = adapter

                } else {
                    Toast.makeText(this@DashboardActivity, "Failed to load caterers", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<CatererData>>, t: Throwable) {
                Toast.makeText(this@DashboardActivity, "Network error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })

        settingIcon.setOnClickListener{
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showDeleteDialog(caterer: CatererData) {
        AlertDialog.Builder(this)
            .setTitle("Delete Caterer")
            .setMessage("Are you sure you want to delete ${caterer.name}?")
            .setPositiveButton("Yes") { dialog, _ ->
                deleteCaterer(caterer)
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun deleteCaterer(caterer: CatererData) {
        RetrofitInstance.api.deleteCaterer(caterer._id).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    // Remove from list and notify adapter
                    // Find index of the caterer
                    val adapter = recyclerView.adapter as CatererAdapter
                    adapter.removeItem(caterer)
                    Toast.makeText(this@DashboardActivity, "Caterer deleted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@DashboardActivity, "Failed to delete caterer", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@DashboardActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun performSearch(query: String) {
        RetrofitInstance.api.getCaterers().enqueue(object : Callback<List<CatererData>> {
            override fun onResponse(call: Call<List<CatererData>>, response: Response<List<CatererData>>) {
                if (response.isSuccessful) {
                    val caterers = response.body() ?: emptyList()
                    val matchedCaterer = caterers.find { it.name.equals(query, ignoreCase = true) }
                    if (matchedCaterer != null) {
                        // Navigate to CatererDetailsActivity with the matched caterer
                        val intent = Intent(this@DashboardActivity, CatererDetailsActivity::class.java)
                        intent.putExtra("caterer", matchedCaterer)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@DashboardActivity, "No matching caterer found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@DashboardActivity, "Failed to load caterers", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<CatererData>>, t: Throwable) {
                Toast.makeText(this@DashboardActivity, "Network error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
