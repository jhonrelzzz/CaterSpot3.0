package com.intprog32.caterspot30

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView

class DeveloperPage1 : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developer_page1)

        val backButton = findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener {
            startActivity(Intent(this@DeveloperPage1, DeveloperPage::class.java))
            finish()
        }
        val facebookIcon = findViewById<ImageView>(R.id.facebookIcon)

        facebookIcon.setOnClickListener {
            openUrl("https://www.facebook.com/jamesyanyan.canete")
        }
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}