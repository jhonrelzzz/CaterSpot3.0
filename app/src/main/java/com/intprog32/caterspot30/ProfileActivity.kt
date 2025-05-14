package com.intprog32.caterspot30

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.intprog32.caterspot30.Data.UserData


class ProfileActivity : Activity() {
    @SuppressLint("MissingInflatedId")
    private lateinit var profileImageView: ImageView
    private lateinit var selectImageButton: Button
    val PICK_IMAGE_REQUEST = 1

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val firstTextView = findViewById<TextView>(R.id.firstnametextview)
        val lastTextView= findViewById<TextView>(R.id.lastnametextview)
        val eTextView = findViewById<TextView>(R.id.emailtextview)
        profileImageView = findViewById(R.id.ProfileImage)
        selectImageButton = findViewById(R.id.ChangeImageButton)
        val userData = intent.getParcelableExtra<UserData>("user_data")

        userData?.let{
            firstTextView.text = it.firstName
            lastTextView.text = it.lastName
            eTextView.text = it.email

        }

        intent?.let {
            it.getStringExtra("firstname")?.let { firstname ->
                firstTextView.text = firstname
            }
        }

        intent?.let {
            it.getStringExtra("lastname")?.let { lastname ->
                lastTextView.text = lastname
            }
        }

        intent?.let {
            it.getStringExtra("email")?.let { email ->
                eTextView.text = email
            }
        }

        val uinfobutton = findViewById<Button>(R.id.userinfo)
        uinfobutton.setOnClickListener {
            startActivity(
                Intent(this,UserInformationActivity::class.java).apply {
                    putExtra("firstname", firstTextView.text.toString())
                    putExtra("lastname", lastTextView.text.toString())
                    putExtra("email", eTextView.text.toString())
                }

            )
        }

        val backButton = findViewById<Button>(R.id.button1)
        backButton.setOnClickListener{
            startActivity(
                Intent(this,DashboardActivity::class.java)
            )
        }

        selectImageButton.setOnClickListener {
            openImageChooser()
        }
    }
    private fun openImageChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            val imageUri: Uri = data.data!!
            profileImageView.setImageURI(imageUri)
        }
    }
}