package com.intprog32.caterspot30

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView


class UserInformationActivity : Activity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_information)

        val ebutton = findViewById<Button>(R.id.editButton)
        val bbutton = findViewById<Button>(R.id.BackButton)
        val fnametext = findViewById<TextView>(R.id.firstNameText)
        val lnametext = findViewById<TextView>(R.id.lastNameText)
        val emailtext = findViewById<TextView>(R.id.emailText)


        intent?.let {
            it.getStringExtra("firstname")?.let { firstname ->
                fnametext.text = firstname
            }
        }

        intent?.let {
            it.getStringExtra("lastname")?.let { lastname ->
                lnametext.text = lastname
            }
        }

        intent?.let {
            it.getStringExtra("email")?.let { email ->
                emailtext.text = email
            }
        }


        ebutton.setOnClickListener {
            startActivity(
                Intent(this,EditProfileActivity::class.java)
            )
        }
        bbutton.setOnClickListener {
            startActivity(
                Intent(this,ProfileActivity::class.java).apply {
                    putExtra("firstname", fnametext.text.toString())
                    putExtra("lastname", lnametext.text.toString())
                    putExtra("email", emailtext.text.toString())
                }
            )
        }
    }
}