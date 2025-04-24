package com.intprog32.caterspot30

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val login_btn = findViewById<Button>(R.id.login_Button)
        val login_email = findViewById<EditText>(R.id.email_edit_text)
        val login_password = findViewById<EditText>(R.id.password_edit_text)
        val register_text = findViewById<TextView>(R.id.signup_two)

        val passedEmail = intent.getStringExtra("email")
        val passedPassword = intent.getStringExtra("password")

        login_email.setText(passedEmail?: "")
        login_password.setText(passedPassword?: "")

        login_btn.setOnClickListener {
            val email = login_email.text.toString()
            val password = login_password.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                startActivity(Intent(this, DashboardActivity::class.java))
            }
        }

        register_text.setOnClickListener{
            Toast.makeText(this, "Opening Register Page", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}