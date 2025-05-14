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
import androidx.appcompat.app.AlertDialog

class SettingsActivity : Activity() {
    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Back button
        val back = findViewById<ImageView>(R.id.back_button)
        val home = findViewById<ImageView>(R.id.homeimagebutton)
        val book = findViewById<ImageView>(R.id.booking)
        val profile = findViewById<Button>(R.id.profile_buttons)
        val logoutButton = findViewById<Button>(R.id.logout_Button)

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
            startActivity(
                Intent(this,ProfileActivity::class.java)
            )
        }

        logoutButton.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes") { dialog, _ ->
                    startActivity(
                        Intent(this, LoginActivity::class.java)
                    )
                    finish()
                    Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
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
