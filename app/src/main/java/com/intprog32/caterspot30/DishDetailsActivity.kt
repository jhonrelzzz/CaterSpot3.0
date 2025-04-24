package com.intprog32.caterspot30

import android.app.Activity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.intprog32.caterspot30.Data.DishData

class DishDetailsActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dish_details)

        val dish = intent.getParcelableExtra<DishData>("dish")

        dish?.let {
            findViewById<TextView>(R.id.dish_name).text = it.name
            findViewById<ImageView>(R.id.dish_image).setImageResource(it.imageResId)
        }
    }
}
