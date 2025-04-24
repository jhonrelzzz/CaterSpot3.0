package com.intprog32.caterspot30

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.intprog32.caterspot30.Data.UserData

class ConfirmActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)

        val confirm_Button = findViewById<Button>(R.id.OTPconfirm_button)

        confirm_Button.setOnClickListener {
            val userData =  intent.getParcelableExtra<UserData>("user_data")

            if (userData != null) {
                Toast.makeText(this, "Opening Login Activity", Toast.LENGTH_LONG).show()
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("user_data", userData)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Missing user data", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
