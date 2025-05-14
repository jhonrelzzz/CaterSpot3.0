package com.intprog32.caterspot30

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle

import android.widget.ImageView


class DeveloperPage2 : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developer_page2)

        val backButton = findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener {
            startActivity(Intent(this@DeveloperPage2, DeveloperPage::class.java))
            finish()
        }

        val facebookIcon = findViewById<ImageView>(R.id.facebookIcon)

        facebookIcon.setOnClickListener {
            openUrl("https://www.facebook.com/ian.lance.delfino.delacruz")
        }
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}