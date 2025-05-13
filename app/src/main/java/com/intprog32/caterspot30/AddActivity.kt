package com.intprog32.caterspot30

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.intprog32.caterspot30.Connections.MyDatabaseHelper
import com.intprog32.caterspot30.Data.CatererData
import com.intprog32.caterspot30.Intefaces.RetrofitInstance
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val cateringName: EditText = findViewById(R.id.Catering_Name_Input)
        val description: EditText = findViewById(R.id.Description_input)
        val image: EditText = findViewById(R.id.ImageUrl_input)
        val addBtn: Button = findViewById(R.id.add_button)
        val back : ImageView = findViewById(R.id.back_button)

        back.setOnClickListener {
            finish()
        }

        addBtn.setOnClickListener {
            val name = cateringName.text.toString().trim()
            val desc = description.text.toString().trim()
            val imageUrl = image.text.toString().trim()

            if (name.isEmpty() || desc.isEmpty() || imageUrl.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show()
            } else {
                // Create your CatererData object
                val caterer = CatererData(
                    _id = "",          // or generate an ID if needed
                    name = name,
                    description = desc,
                    imageUrl = imageUrl
                ) // add imageUrl if needed

                // Make API call
                RetrofitInstance.api.addCaterer(caterer).enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(
                                this@AddActivity,
                                "Catering added successfully!",
                                Toast.LENGTH_LONG
                            ).show()
                            cateringName.text.clear()
                            description.text.clear()
                            image.text.clear()

                            startActivity(
                                Intent(this@AddActivity,DashboardActivity::class.java)
                            )

                        } else {
                            Toast.makeText(
                                this@AddActivity,
                                "Failed to add catering. Error code: ${response.code()}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(
                            this@AddActivity,
                            "Network error: ${t.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
            }
        }
    }
}