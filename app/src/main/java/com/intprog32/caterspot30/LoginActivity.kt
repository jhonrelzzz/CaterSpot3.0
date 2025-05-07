package com.intprog32.caterspot30

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.intprog32.caterspot30.Data.UserData
import com.intprog32.caterspot30.Intefaces.RetrofitInstance
import okhttp3.ResponseBody // Import ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val login_btn = findViewById<Button>(R.id.login_Button)
        val login_email = findViewById<EditText>(R.id.email_edit_text)
        val login_password = findViewById<EditText>(R.id.password_edit_text)
        val register_text = findViewById<TextView>(R.id.signup_two)

        login_btn.setOnClickListener {
            val email = login_email.text.toString().trim()
            val password = login_password.text.toString().trim()

            // **ADD THIS LOGGING:**
            Log.d("LoginActivity", "Email entered (trimmed): '$email'") // Use single quotes to see leading/trailing spaces
            Log.d("LoginActivity", "Password entered (trimmed): '$password'") // Use single quotes to see leading/trailing spaces

            // Validate email field
            if (email.isEmpty()) {
                Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // ... rest of your validation ...

            // Make the network request to validate the user
            RetrofitInstance.api.validateUser(email, password).enqueue(object: Callback<ResponseBody> {
                // ... rest of your onResponse and onFailure ...
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    Log.d("LoginActivity", "Received response status code: ${response.code()}")
                    if (response.isSuccessful) {
                        Log.d("LoginActivity", "Response is successful (2xx).") // Add this line
                        val responseBodyString = response.body()?.string()
                        Log.d("LoginActivity", "Successful response body: $responseBodyString") // Log the body

                        Toast.makeText(this@LoginActivity, "Login Successful", Toast.LENGTH_SHORT).show()
                        Log.d("LoginActivity", "Showing 'Login Successful' toast.") // Add this line

                        val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                        Log.d("LoginActivity", "Attempting to start DashboardActivity.") // Add this line
                        startActivity(intent)

                        Log.d("LoginActivity", "startActivity called.") // Add this line
                    } else {
                        val errorBodyString = response.errorBody()?.string()
                        Log.e("LoginActivity", "Error response body: $errorBodyString") // Log error body
                        // ... handle non-successful ...
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("LoginActivity", "Network failure: ${t.message}", t) // Log failure with stack trace
                    // ... handle failure ...
                }
            })
        }

        register_text.setOnClickListener {
            Toast.makeText(this, "Opening Register Page", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}