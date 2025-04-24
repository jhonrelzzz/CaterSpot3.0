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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val recyclerView = findViewById<RecyclerView>(R.id.caterer_recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val myImageButton: ImageView = findViewById(R.id.booking)
        myImageButton.setOnClickListener {
            // Handle the click event here
            Toast.makeText(this, "Image clicked!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, BookingActivity::class.java)
            startActivity(intent)
        }

        val caterers = listOf(
            CatererData("ABC Catering", "Sea food and more.", R.drawable.sampleseafood),
            CatererData("XYZ Catering", "Buffet specialist.", R.drawable.sampleseafood),
            CatererData("MNO Delights", "Gourmet meals and desserts.", R.drawable.sampleseafood),
            CatererData("ABC Catering", "Sea food and more.", R.drawable.sampleseafood),
            CatererData("XYZ Catering", "Buffet specialist.", R.drawable.sampleseafood),
            CatererData("MNO Delights", "Gourmet meals and desserts.", R.drawable.sampleseafood)
        )

        // Validation: Check if the caterers list is not empty
        if (caterers.isEmpty()) {
            Toast.makeText(this, "No caterers available to display.", Toast.LENGTH_LONG).show()
            return
        }

        // Optional: Remove duplicates based on name (if needed)
        val uniqueCaterers = caterers.distinctBy { it.name }

        recyclerView.adapter = CatererAdapter(uniqueCaterers)
    }
}
