package com.intprog32.caterspot30

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.intprog32.caterspot30.Adapters.CatererAdapter
import com.intprog32.caterspot30.Data.CatererData

class DashboardActivity : Activity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val settingIcon =  findViewById<ImageView>(R.id.nav_settings)

        val recyclerView = findViewById<RecyclerView>(R.id.caterer_recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val bookingBtn: ImageView = findViewById(R.id.booking)
        bookingBtn.setOnClickListener {
            // Handle the click event here
            Toast.makeText(this, "Image clicked!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, BookingActivity::class.java)
            startActivity(intent)
        }

        val ChatBtn: ImageView = findViewById(R.id.btnTempChat)
        ChatBtn.setOnClickListener {
            val intent = Intent(this, SelectChatActivity::class.java)
            startActivity(intent)
        }


        val caterers = listOf(
            CatererData("ABC Catering", "Sea food and more.", R.drawable.sampleseafood),
            CatererData("XYZ Catering", "Buffet specialist.", R.drawable.shrimp),
            CatererData("MNO Delights", "Gourmet meals and desserts.", R.drawable.fish),
            CatererData("Jhonrel's Catering", "Sea food and more.", R.drawable.seafoodboil),
            CatererData("James' Catering", "Buffet specialist.", R.drawable.salmon),
            CatererData("Ian's Delights", "Gourmet meals and desserts.", R.drawable.shrimp),
            CatererData("Rianel's Catering", "Sea food and more.", R.drawable.sampleseafood)
        )

        // Validation: Check if the caterers list is not empty
        if (caterers.isEmpty()) {
            Toast.makeText(this, "No caterers available to display.", Toast.LENGTH_LONG).show()
            return
        }

        // Optional: Remove duplicates based on name (if needed)
        val uniqueCaterers = caterers.distinctBy { it.name }

        recyclerView.adapter = CatererAdapter(uniqueCaterers) { selectedCaterer ->
            val intent = Intent(this, CatererDetailsActivity::class.java)
            intent.putExtra("caterer", selectedCaterer)
            startActivity(intent)
        }

        /*recyclerView.adapter = CatererAdapter(uniqueCaterers)*/

        settingIcon.setOnClickListener{
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
}
