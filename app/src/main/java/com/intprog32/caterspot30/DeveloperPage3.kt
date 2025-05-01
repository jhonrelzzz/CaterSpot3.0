package com.intprog32.caterspot30

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DeveloperPage3 : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developer_page3)

        val backButton = findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener { view: View? ->
            startActivity(
                Intent(
                    this@DeveloperPage3,
                    DeveloperPage::class.java
                )
            )
            finish()
        }
    }
}