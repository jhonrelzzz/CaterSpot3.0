package com.intprog32.caterspot30

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.intprog32.caterspot30.Data.UserData

class LoginActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val login_btn = findViewById<Button>(R.id.login_Button)
        val login_email = findViewById<EditText>(R.id.email_edit_text)
        val login_password = findViewById<EditText>(R.id.password_edit_text)
        val register_text = findViewById<TextView>(R.id.signup_two)
        val userData = intent.getParcelableExtra<UserData>("user_data")

        userData?.let{
            login_email.setText(it.email)
            login_password.setText(it.password)
        }

        login_btn.setOnClickListener {
            val email = login_email.text.toString().trim()
            val password = login_password.text.toString().trim()

            // Validate email field
            if (email.isEmpty()) {
                Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validate password field
            if (password.isEmpty()) {
                Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // If all validations pass
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, DashboardActivity::class.java))
        }

        register_text.setOnClickListener {
            Toast.makeText(this, "Opening Register Page", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
