package com.intprog32.caterspot30

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.intprog32.caterspot30.Data.UserData

class OTPActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otpactivity)

        val OTP_number = findViewById<EditText>(R.id.phone_number_edit_text)
        val OTP_button = findViewById<Button>(R.id.confirm_button)

        // Retrieve the UserData object from the intent
        val userData = intent.getParcelableExtra<UserData>("user_data")

        // You can now use the userData object
        userData?.let {
            // Access user data like:
            val firstName = it.firstName
            val lastName = it.lastName
            val email = it.email
            val password = it.password
            val phoneNumber = it.phoneNumber
        }

        OTP_button.setOnClickListener {
            val OTPNum = OTP_number.toString()
            val email = intent.getStringExtra("email")
            val password = intent.getStringExtra("password")

            if(OTPNum.isEmpty()){
                Toast.makeText(this, "Fill put a number", Toast.LENGTH_LONG).show()
            } else {
                val intent = Intent(this, ConfirmActivity::class.java)
                intent.putExtra("email", email)
                intent.putExtra("password", password)
                startActivity(intent)
            }
        }
    }
}
