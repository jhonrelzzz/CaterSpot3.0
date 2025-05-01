package com.intprog32.caterspot30

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.app.DatePickerDialog
import android.app.AlertDialog
import android.content.Intent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale



class BookingActivity : Activity() {
    private lateinit var btnPickDate: Button
    private lateinit var etDate: EditText
    private lateinit var btnPickLocation: Button
    private lateinit var etLocation: EditText
    private lateinit var etGuests: EditText
    private lateinit var btnNext: Button
    private lateinit var btnCancel: Button
    // Predefined list of locations
    private val locations = arrayOf(
        "Location A",
        "Location B",
        "Location C",
        "Location D",
        "Location E"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)
        btnPickDate = findViewById(R.id.btnPickDate)
        etDate = findViewById(R.id.etDate)

        etDate = findViewById(R.id.etDate)
        btnPickLocation = findViewById(R.id.btnPickLocation)
        etLocation = findViewById(R.id.etLocation)
        etGuests = findViewById(R.id.etGuests)
        btnNext = findViewById(R.id.btnNext)
        btnCancel = findViewById(R.id.btnCancel)
        btnCancel.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }
        btnPickDate.setOnClickListener {
            showDatePickerDialog()
        }
        btnPickLocation.setOnClickListener {
            showLocationPickerDialog()
        }
        btnNext.setOnClickListener {
            bookNow()
        }

        btnNext.setOnClickListener {
            startActivity(
                Intent(this, PaymentActivity::class.java)
            )
        }
        btnCancel.setOnClickListener {
            startActivity(
                Intent(this, DashboardActivity::class.java)
            )
        }


    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth, selectedDay)
                updateDateInView(selectedDate)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun updateDateInView(selectedDate: Calendar) {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(selectedDate.time)
        etDate.setText(formattedDate)
    }

    private fun showLocationPickerDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Pick a Location")
            .setItems(locations) { _, which ->
                // The 'which' argument contains the index position
                // of the selected item
                etLocation.setText(locations[which])
            }
        builder.create().show()
    }

    private fun bookNow() {
        val guests = etGuests.text.toString()
        val date = etDate.text.toString()
        val location = etLocation.text.toString()

        if ( guests.isEmpty() || date.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val message = "Booking successful!"
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

    }
}