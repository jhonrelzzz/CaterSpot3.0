package com.intprog32.caterspot30

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class ConfirmActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)

        val OTP_number_editText = findViewById<EditText>(R.id.OTP_number)
        val confirm_Button = findViewById<Button>(R.id.OTPconfirm_button)

        confirm_Button.setOnClickListener {
            val email = intent.getStringExtra("email")
            val password = intent.getStringExtra("password")

            Toast.makeText(this, "Opening Login Activity", Toast.LENGTH_LONG).show()

            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("email", email)
            intent.putExtra("password", password)
            startActivity(intent)
        }
    }
}