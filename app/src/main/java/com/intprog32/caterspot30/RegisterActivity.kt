package com.intprog32.caterspot30

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.intprog32.caterspot30.Data.UserData
import com.intprog32.caterspot30.Intefaces.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val reg_firstname = findViewById<EditText>(R.id.first_name_edit_text)
        val reg_lastname = findViewById<EditText>(R.id.last_name_edit_text)
        val reg_email = findViewById<EditText>(R.id.email_edit_text)
        val reg_password = findViewById<EditText>(R.id.password_edit_text)
        val reg_button = findViewById<Button>(R.id.next_button)
        val reg_number = findViewById<EditText>(R.id.phone_number_edit_text)

        reg_button.setOnClickListener {
            val firstName = reg_firstname.text.toString().trim()
            val lastName = reg_lastname.text.toString().trim()
            val email = reg_email.text.toString().trim()
            val password = reg_password.text.toString().trim()
            val phoneNum = reg_number.text.toString().trim()

            // Validation
            if (firstName.isEmpty()) {
                Toast.makeText(this, "First name is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (lastName.isEmpty()) {
                Toast.makeText(this, "Last name is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (email.isEmpty()) {
                Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (phoneNum.isEmpty()) {
                Toast.makeText(this, "Phone number is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (phoneNum.length != 11) {
                Toast.makeText(this, "Phone number must be 11 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create a UserData object
            val userData = UserData(
                firstName = firstName,
                lastName = lastName,
                email = email,
                password = password,
                phoneNumber = phoneNum,
                id = ""
            )

            // Call the API to register the user
            RetrofitInstance.api.registerUser(userData).enqueue(object : Callback<UserData> {
                override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                    if (response.isSuccessful) {
                        // Registration successful, move to OTPActivity
                        Toast.makeText(this@RegisterActivity, "User registered successfully", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this@RegisterActivity, ConfirmActivity::class.java)
                        intent.putExtra("user_data", userData) // Ensure UserData is Serializable or Parcelable
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@RegisterActivity, "Failed to register user: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<UserData>, t: Throwable) {
                    Toast.makeText(this@RegisterActivity, "Network failure: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}

