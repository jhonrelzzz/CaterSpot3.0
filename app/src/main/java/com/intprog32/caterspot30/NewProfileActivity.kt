package com.intprog32.caterspot30

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.intprog32.caterspot30.Data.UserData
import com.intprog32.caterspot30.Intefaces.ApiService
import com.intprog32.caterspot30.Intefaces.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewProfileActivity : Activity() {

    private lateinit var profilePhoto: ImageView
    private lateinit var fullNameTextView: TextView
    private lateinit var phoneNumberEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var editButton: Button
    private lateinit var saveButton: Button
    private lateinit var backButton: ImageView
    private lateinit var home: ImageView
    private lateinit var book: ImageView

    private lateinit var apiService: ApiService
    private var currentUser: UserData? = null
    private var currentUserId: String? = null // You need a way to get the user's ID

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Initialize UI elements
        profilePhoto = findViewById(R.id.profile_photo)
        fullNameTextView = findViewById(R.id.full_name)
        phoneNumberEditText = findViewById(R.id.phone_number_edittext)
        emailEditText = findViewById(R.id.email_edittext)
        editButton = findViewById(R.id.edit_button)
        saveButton = findViewById(R.id.save_button)
        backButton = findViewById(R.id.back_button)
        home = findViewById<ImageView>(R.id.home_nav)
        book = findViewById<ImageView>(R.id.booking_nav)

        // Disable save button until user data is loaded
        saveButton.isEnabled = false

        apiService = RetrofitInstance.api

        val sharedPref = getSharedPreferences("CaterspotPrefs", MODE_PRIVATE)
        val loggedInEmail = sharedPref.getString("loggedInEmail", null)

        if (loggedInEmail != null) {
            RetrofitInstance.api.getUserByEmail(loggedInEmail).enqueue(object : Callback<UserData> {
                override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                    if (response.isSuccessful && response.body() != null) {
                        val user = response.body()!!
                        currentUser = user
                        currentUserId = user.id

                        // Populate UI
                        val fullName = "${user.firstName} ${user.lastName}"
                        fullNameTextView.text = fullName
                        phoneNumberEditText.setText(user.phoneNumber)
                        emailEditText.setText(user.email)

                        // Enable editing if needed
                        phoneNumberEditText.isEnabled = false
                        emailEditText.isEnabled = false

                        // Enable save button now that data is loaded
                        saveButton.isEnabled = true

                        Log.d("NewProfileActivity", "User loaded: $user with ID: ${user.id}")
                    } else {
                        Log.e("NewProfileActivity", "No user found or error: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<UserData>, t: Throwable) {
                    Log.e("NewProfileActivity", "Failed to get user: ${t.message}")
                }
            })
        }

        // Set up edit button
        editButton.setOnClickListener {
            phoneNumberEditText.isEnabled = true
            emailEditText.isEnabled = true
            saveButton.visibility = View.VISIBLE
        }

        // Set up save button
        saveButton.setOnClickListener {
            // Check if user data is loaded before trying to save
            if (currentUser == null || currentUserId == null) {
                Log.e("Profile", "Cannot update: user or userId is null")
                Toast.makeText(this, "User data not loaded. Please wait.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updatedPhone = phoneNumberEditText.text.toString()
            val updatedEmail = emailEditText.text.toString()

            val updatedUser = currentUser!!.copy(
                phoneNumber = updatedPhone,
                email = updatedEmail,
                id = currentUser!!.id,
                password = currentUser!!.password
            )

            // Make API call to update
            RetrofitInstance.api.updateUser(currentUserId!!, updatedUser).enqueue(object : Callback<UserData> {
                override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                    if (response.isSuccessful && response.body() != null) {
                        val user = response.body()!!

                        Toast.makeText(this@NewProfileActivity, "Profile Updated Successfully!", Toast.LENGTH_LONG).show()
                        Log.d("NewProfileActivity", "Received user: $user")

                        currentUser = user
                        currentUserId = user.id
                        Log.d("NewProfileActivity", "User ID set to: $currentUserId")

                        val fullName = "${user.firstName} ${user.lastName}"
                        fullNameTextView.text = fullName
                        phoneNumberEditText.setText(user.phoneNumber)
                        emailEditText.setText(user.email)

                        phoneNumberEditText.isEnabled = false
                        emailEditText.isEnabled = false
                        saveButton.isEnabled = true
                        saveButton.visibility = View.INVISIBLE
                    } else {
                        Log.e("NewProfileActivity", "Failed to load user. Code: ${response.code()}, Error: ${response.errorBody()?.string()}")
                    }
                }


                override fun onFailure(call: Call<UserData>, t: Throwable) {
                    Toast.makeText(this@NewProfileActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        backButton.setOnClickListener {
            startActivity(
                Intent(this, SettingsActivity::class.java)
            )
        }
        home.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }
        book.setOnClickListener {
            val intent = Intent(this, BookingActivity::class.java)
            startActivity(intent)
        }
    }
}