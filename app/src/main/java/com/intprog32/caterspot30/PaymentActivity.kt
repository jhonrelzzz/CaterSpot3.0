package com.intprog32.caterspot30

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast

class PaymentActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val hourSpinner: Spinner = findViewById(R.id.spinner_hour)
        val minuteSpinner: Spinner = findViewById(R.id.spinner_minute)
        val ampmSpinner: Spinner = findViewById(R.id.spinner_am_pm)

        val backButton = findViewById<ImageView>(R.id.back_button)
        val bookNav = findViewById<ImageView>(R.id.book_nav)

        backButton.setOnClickListener{
            startActivity(
                Intent(this, DashboardActivity::class.java)
            )
        }

        bookNav.setOnClickListener {
            Toast.makeText(this, "Booked Successfully", Toast.LENGTH_SHORT).show()

            startActivity(
                Intent(this, DashboardActivity::class.java)
            )
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.hours_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            hourSpinner.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.minutes_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            minuteSpinner.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.am_pm_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            ampmSpinner.adapter = adapter
        }


    }
}