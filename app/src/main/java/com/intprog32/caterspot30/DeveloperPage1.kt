package com.intprog32.caterspot30

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView

class DeveloperPage1 : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developer_page1)

        val backButton = findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener { view: View? ->
            startActivity(
                Intent(
                    this@DeveloperPage1,
                    DeveloperPage::class.java
                )
            )
            finish()
        }
    }
}