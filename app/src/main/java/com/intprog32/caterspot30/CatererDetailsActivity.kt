package com.intprog32.caterspot30

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.intprog32.caterspot30.Adapters.DishAdapter
import com.intprog32.caterspot30.Data.CatererData
import com.intprog32.caterspot30.Data.DishData

class CatererDetailsActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_caterer_details)

        val dish = intent.getParcelableExtra<DishData>("dish")
        val back = findViewById<ImageView>(R.id.back_button)

        dish?.let {
            findViewById<TextView>(R.id.dish_name).text = it.name
            findViewById<ImageView>(R.id.dish_image).setImageResource(it.imageResId)
        }

        val dishes = listOf(
            DishData("Oven Grilled Fish", R.drawable.fish),
            DishData("Salmon and Cream", R.drawable.salmon),
            DishData("Garlic Butter Shrimp", R.drawable.shrimp),
            DishData("Seafood boil", R.drawable.seafoodboil)
        )

        val dishRecycler = findViewById<RecyclerView>(R.id.dishes_recycler)
        dishRecycler.layoutManager = GridLayoutManager(this, 2)
        dishRecycler.adapter = DishAdapter(dishes) { selectedDish ->
            val intent = Intent(this, DishDetailsActivity::class.java)
            intent.putExtra("dish", selectedDish)
            startActivity(intent)
        }

        val intent = Intent(this, DashboardActivity::class.java)
        back.setOnClickListener{
            startActivity(intent)
        }


        // Example static dishes
        /*val dishes = listOf(
            DishData("Oven Grilled Fish", R.drawable.fish),
            DishData("Salmon and Cream", R.drawable.salmon),
            DishData("Garlic Butter Shrimp", R.drawable.shrimp),
            DishData("Seafood boil", R.drawable.seafoodboil)
        )

        val dishRecycler = findViewById<RecyclerView>(R.id.dishes_recycler)
        dishRecycler.layoutManager = GridLayoutManager(this, 2)
        dishRecycler.adapter = DishAdapter(dishes)*/
    }
}
