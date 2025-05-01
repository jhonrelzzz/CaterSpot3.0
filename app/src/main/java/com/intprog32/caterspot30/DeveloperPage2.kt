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

class DeveloperPage2 : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developer_page2)

        val backButton = findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener { view: View? ->
            startActivity(
                Intent(
                    this@DeveloperPage2,
                    DeveloperPage::class.java
                )
            )
            finish()
        }

    }
}