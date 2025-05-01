package com.intprog32.caterspot30

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.RelativeLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DeveloperPage : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developer_page)

        val dev1 = findViewById<RelativeLayout>(R.id.dev1)
        val dev2 = findViewById<RelativeLayout>(R.id.dev2)
        val dev3 = findViewById<RelativeLayout>(R.id.dev3)
        val dev4 = findViewById<RelativeLayout>(R.id.dev4)
        val btnBack = findViewById<ImageButton>(R.id.btnBack)

        dev1.setOnClickListener {
            startActivity(Intent(this, DeveloperPage1::class.java))
        }

        dev2.setOnClickListener {
            startActivity(Intent(this, DeveloperPage2::class.java))
        }

        dev3.setOnClickListener {
            startActivity(Intent(this, DeveloperPage3::class.java))
        }

        dev4.setOnClickListener {
            startActivity(Intent(this, DeveloperPage4::class.java))
        }

        btnBack.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}