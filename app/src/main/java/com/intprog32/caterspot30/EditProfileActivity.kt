package com.intprog32.caterspot30

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText


class EditProfileActivity : Activity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val fname = findViewById<EditText>(R.id.firstNameEditText)
        val lname = findViewById<EditText>(R.id.lastNameEditText)
        val emails = findViewById<EditText>(R.id.emailEditText)

        intent?.let {
            it.getStringExtra("firstname").let { firstname ->
                fname.setText(firstname)
            }
        }
        intent?.let {
            it.getStringExtra("lastname").let { lastname ->
                lname.setText(lastname)
            }
        }
        intent?.let {
            it.getStringExtra("email").let { email ->
                emails.setText(email)
            }
        }


        val savebutton = findViewById<Button>(R.id.saveButton)
        savebutton.setOnClickListener{
            startActivity(
                Intent(this,UserInformationActivity::class.java).apply {
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