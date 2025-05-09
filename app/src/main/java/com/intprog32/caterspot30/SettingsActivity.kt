package com.intprog32.caterspot30

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import android.widget.Toast

class SettingsActivity : Activity() {
    @SuppressLint("MissingInflatedId", "UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Back button
        val back = findViewById<ImageView>(R.id.back_button)
        val home = findViewById<ImageView>(R.id.home_nav)
        val book = findViewById<ImageView>(R.id.booking_nav)
        val profile = findViewById<Button>(R.id.profile_button)

        back.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }
        home.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }
        book.setOnClickListener {
            val intent = Intent(this, BookingActivity::class.java)
            startActivity(intent)
        }
        profile.setOnClickListener {
            val intent = Intent(this, NewProfileActivity::class.java)
            startActivity(intent)
        }

        // Switch: Notifications
        val switchNotifications = findViewById<Switch>(R.id.switch_notifications)
        switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            val message = if (isChecked) "Notifications Enabled" else "Notifications Disabled"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        // Switch: Newsletter
        val switchNewsletter = findViewById<Switch>(R.id.switch_newsletter)
        switchNewsletter.setOnCheckedChangeListener { _, isChecked ->
            val message = if (isChecked) "Newsletter Subscribed" else "Newsletter Unsubscribed"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }


    }
}
