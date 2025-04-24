package com.intprog32.caterspot30

import android.app.Activity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

        val caterers = listOf(
            CatererData("ABC Catering", "Sea food and more.", R.drawable.sampleseafood),
            CatererData("ABC Catering", "Sea food and more.", R.drawable.sampleseafood),
            CatererData("ABC Catering", "Sea food and more.", R.drawable.sampleseafood),
            CatererData("ABC Catering", "Sea food and more.", R.drawable.sampleseafood)
        )

        recyclerView.adapter = CatererAdapter(caterers)
    }
}