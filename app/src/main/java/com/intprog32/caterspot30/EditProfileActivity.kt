package com.intprog32.caterspot30

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class EditProfileActivity : Activity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val fname = findViewById<EditText>(R.id.firstNameEditText)
        val lname = findViewById<EditText>(R.id.lastNameEditText)
        val emails = findViewById<EditText>(R.id.emailEditText)

        val savebutton = findViewById<Button>(R.id.saveButton)
        savebutton.setOnClickListener{
            startActivity(
                Intent(this,ProfileActivity::class.java).apply {
                    putExtra("firstname", fname.text.toString())
                    putExtra("lastname", lname.text.toString())
                    putExtra("email", emails.text.toString())
                }
            )
        }

        val cancelbutton = findViewById<Button>(R.id.cancelButton)
        cancelbutton.setOnClickListener{
            startActivity(
                Intent(this,ProfileActivity::class.java)
            )
        }
    }

}