package com.intprog32.caterspot30

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.intprog32.caterspot30.Data.UserData


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
            val firstname = fname.text.toString()
            val lastname = lname.text.toString()
            val email = emails.text.toString()

            if(firstname.isEmpty()){
                Toast.makeText(this, "First Name is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (lastname.isEmpty()) {
                Toast.makeText(this, "Last name is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val user = UserData(
                firstName = firstname,
                lastName = lastname,
                email = email,
                password = "",
                phoneNumber = ""
            )

            startActivity(
                Intent(this,UserInformationActivity::class.java).apply {
                    putExtra("user_data", user)

                    putExtra("firstname", firstname)
                    putExtra("lastname", lastname)
                    putExtra("email", email)
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