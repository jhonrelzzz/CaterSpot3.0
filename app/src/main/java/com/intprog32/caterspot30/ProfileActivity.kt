package com.intprog32.caterspot30

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ProfileActivity : Activity() {
    @SuppressLint("MissingInflatedId")
    private lateinit var profileImageView: ImageView
    private lateinit var selectImageButton: Button
    val PICK_IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val textviewname = findViewById<TextView>(R.id.nametextview)
        val textviewemail = findViewById<TextView>(R.id.emailtextview)
        profileImageView = findViewById(R.id.ProfileImage)
        selectImageButton = findViewById(R.id.ChangeImageButton)

        intent?.let {
            it.getStringExtra("name")?.let { name ->
                textviewname.text = name
            }
        }

        intent?.let {
            it.getStringExtra("email")?.let { email ->
                textviewemail.text = email
            }
        }

        val ebutton = findViewById<Button>(R.id.editbutton)
        ebutton.setOnClickListener {
            startActivity(
                Intent(this,EditProfileActivity::class.java)
            )
        }

        val addButton = findViewById<Button>(R.id.addmenubutton)
        addButton.setOnClickListener{
            startActivity(
                Intent(this,AddMenuActivity::class.java)
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