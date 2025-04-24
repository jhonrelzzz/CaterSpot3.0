package com.intprog32.caterspot30

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.intprog32.caterspot30.Data.UserData

class RegisterActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val reg_firstname = findViewById<EditText>(R.id.first_name_edit_text)
        val reg_lastname = findViewById<EditText>(R.id.last_name_edit_text)
        val reg_email = findViewById<EditText>(R.id.email_edit_text)
        val reg_password = findViewById<EditText>(R.id.password_edit_text)
        val reg_button = findViewById<Button>(R.id.next_button)

        reg_button.setOnClickListener {
            // Create a UserData object
            val userData = UserData(
                firstName = reg_firstname.text.toString(),
                lastName = reg_lastname.text.toString(),
                email = reg_email.text.toString(),
                password = reg_password.text.toString(),
                phoneNumber = "" // Optionally, pass the phone number here
            )

            // Create an Intent to move to the next activity
            val intent = Intent(this, OTPActivity::class.java)
            // Put the UserData object into the Intent
            intent.putExtra("user_data", userData)

            // Start the next activity
            startActivity(intent)
        }
    }
}