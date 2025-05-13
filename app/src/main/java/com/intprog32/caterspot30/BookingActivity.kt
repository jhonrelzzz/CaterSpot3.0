package com.intprog32.caterspot30

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.intprog32.caterspot30.Data.BookingData
import com.intprog32.caterspot30.Intefaces.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class BookingActivity : AppCompatActivity() {

    private lateinit var etEvent: EditText
    private lateinit var etDate: EditText
    private lateinit var etLocation: EditText
    private lateinit var etGuests: EditText
    private lateinit var etCustomRequest: EditText
    private lateinit var etTime: EditText
    private lateinit var btnPickTime: Button
    private lateinit var btnPickDate: Button
    private lateinit var btnPickLocation: Button
    private lateinit var btnNext: Button
    private lateinit var btnCancel: Button

    // CheckBoxes for dietary restrictions
    private lateinit var cbVegan: CheckBox
    private lateinit var cbGlutenFree: CheckBox
    private lateinit var cbVegetarian: CheckBox
    private lateinit var cbLactoseFree: CheckBox
    private lateinit var cbLowSodium: CheckBox
    private lateinit var cbHalal: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {

        val locationPickerLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedLocation = result.data?.getStringExtra("selected_location")
                etLocation.setText(selectedLocation)
            }
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        // Bind views
        etEvent = findViewById(R.id.etEvent)
        etDate = findViewById(R.id.etDate)
        etLocation = findViewById(R.id.etLocation)
        etGuests = findViewById(R.id.etGuests)
        etCustomRequest = findViewById(R.id.etCustomRequest)
        etTime = findViewById(R.id.etTime)
        btnPickTime = findViewById(R.id.btnPickTime)
        btnPickDate = findViewById(R.id.btnPickDate)
        btnPickLocation = findViewById(R.id.btnPickLocation)
        btnNext = findViewById(R.id.btnNext)
        btnCancel = findViewById(R.id.btnCancel)

        cbVegan = findViewById(R.id.checkboxVegan)
        cbGlutenFree = findViewById(R.id.checkboxGlutenFree)
        cbVegetarian = findViewById(R.id.checkboxVegetarian)
        cbLactoseFree = findViewById(R.id.checkboxLactoseFree)
        cbLowSodium = findViewById(R.id.checkboxLowSodium)
        cbHalal = findViewById(R.id.checkboxHalal)

        // Date picker
        btnPickDate.setOnClickListener {
            showDatePicker()
        }

        // Pick location (you can implement a location picker or input)
        btnPickLocation.setOnClickListener {
            val intent = Intent(this@BookingActivity, MapsActivity::class.java)
            locationPickerLauncher.launch(intent)
        }

        btnPickTime.setOnClickListener {
            showTimePicker()
        }

        // Submit booking
        btnNext.setOnClickListener {
            submitBooking()
        }

        // Cancel
        btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun showTimePicker() {
        val cal = Calendar.getInstance()
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        val minute = cal.get(Calendar.MINUTE)

        val timePicker = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            val timeStr = String.format("%02d:%02d", selectedHour, selectedMinute)
            etTime.setText(timeStr)
        }, hour, minute, true)

        timePicker.show()
    }

    private fun showDatePicker() {
        val cal = Calendar.getInstance()
        val datePicker =    DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val dateStr = "${month + 1}/$dayOfMonth/$year"
                etDate.setText(dateStr)
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    private fun submitBooking() {
        val event = etEvent.text.toString()
        val date = etDate.text.toString()
        val location = etLocation.text.toString()
        val guestsStr = etGuests.text.toString()
        val customRequest = etCustomRequest.text.toString()
        val time = etTime.text.toString()

        if (event.isBlank() || date.isBlank() || location.isBlank() || guestsStr.isBlank()) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (time.isBlank()) {
            Toast.makeText(this, "Please select a time", Toast.LENGTH_SHORT).show()
            return
        }

        val guests = guestsStr.toIntOrNull()
        if (guests == null || guests <= 0) {
            Toast.makeText(this, "Enter a valid number of guests", Toast.LENGTH_SHORT).show()
            return
        }

        // Collect dietary restrictions
        val restrictions = mutableListOf<String>()
        if (cbVegan.isChecked) restrictions.add("Vegan")
        if (cbGlutenFree.isChecked) restrictions.add("Gluten-Free")
        if (cbVegetarian.isChecked) restrictions.add("Vegetarian")
        if (cbLactoseFree.isChecked) restrictions.add("Lactose-Free")
        if (cbLowSodium.isChecked) restrictions.add("Low sodium")
        if (cbHalal.isChecked) restrictions.add("Halal")
        val restrictionsStr = restrictions.joinToString(", ")

        val booking = BookingData(
            event = event,
            pickDate = date,
            pickTime = time,
            pickLocation = location,
            noOfGuests = guests,
            customRequest = customRequest + if (restrictions.isNotEmpty()) "\nRestrictions: $restrictionsStr" else ""
        )

        // Show preview BEFORE sending API call
        showPreview(booking)
    }

    private fun showPreview(booking: BookingData) {
        val message = """
        Event: ${booking.event}
        Date: ${booking.pickDate}
        Time: ${booking.pickTime}
        Location: ${booking.pickLocation}
        Guests: ${booking.noOfGuests}
        Request: ${booking.customRequest}
    """.trimIndent()

        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setTitle("Booking Preview")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()

                // API call moved here, only done after user confirms
                RetrofitInstance.api.addBooking(booking).enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@BookingActivity, "Booking submitted!", Toast.LENGTH_SHORT).show()
                            startActivity(
                                Intent(this@BookingActivity, DashboardActivity::class.java)
                            )
                        } else {
                            Toast.makeText(this@BookingActivity, "Failed to submit booking", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(this@BookingActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss() // Optional: do nothing
            }
            .show()
    }

}