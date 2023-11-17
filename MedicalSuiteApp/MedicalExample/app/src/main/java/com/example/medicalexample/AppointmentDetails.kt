package com.example.medicalexample

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AppointmentDetails : AppCompatActivity() {

    private lateinit var showMapButton: Button
    private lateinit var backBtn: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment_details)

        showMapButton = findViewById(R.id.showMapButton)
        backBtn = findViewById(R.id.btn_back)
        val sharedPreferences = getSharedPreferences("AppointmentDetails", MODE_PRIVATE)
        val name = sharedPreferences.getString("name", "")
        val phone = sharedPreferences.getString("phone", "")
        val date = sharedPreferences.getString("date", "")
        val time = sharedPreferences.getString("time", "")

        val nameTextView = findViewById<TextView>(R.id.nameTextView)
        nameTextView.text = "Name: $name"

        val phoneTextView = findViewById<TextView>(R.id.phoneTextView)
        phoneTextView.text = "Phone: $phone"

        val dateTextView = findViewById<TextView>(R.id.dateTextView)
        dateTextView.text = "Date: $date"

        val timeTextView = findViewById<TextView>(R.id.timeTextView)
        timeTextView.text = "Time: $time"


        backBtn.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()
        }


        showMapButton.setOnClickListener {
            val intent = Intent(this, Map::class.java)
            startActivity(intent)
            finish()
        }
    }
}
