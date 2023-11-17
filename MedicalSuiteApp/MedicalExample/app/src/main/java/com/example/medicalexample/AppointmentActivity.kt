package com.example.medicalexample

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AppointmentActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextSurname: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var datePicker1: Button
    private lateinit var timePicker: TimePicker
    private lateinit var bookAppointmentButton: Button
    private lateinit var firestore: FirebaseFirestore
    private lateinit var txtView2: TextView
    private lateinit var backBtn: ImageButton
    private lateinit var firebaseAuth: FirebaseAuth
    private var currentUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment)

        firebaseAuth = FirebaseAuth.getInstance()
        currentUser = firebaseAuth.currentUser

        firestore = FirebaseFirestore.getInstance()
        backBtn = findViewById(R.id.btn_back)

        editTextName = findViewById(R.id.editTextName)
        editTextSurname = findViewById(R.id.editTextSurname)
        editTextPhone = findViewById(R.id.editTextPhone)
        datePicker1 = findViewById(R.id.btnDatePicker)
        timePicker = findViewById(R.id.timePicker)
        bookAppointmentButton = findViewById(R.id.bookAppointmentButton)
        txtView2 = findViewById(R.id.txtView2)

        // Add spinner code
        val doctorSpinner: Spinner = findViewById(R.id.doctorSpinner)
        val doctorNames = arrayOf("Dr Max Smith", "Dr Thabiso Mathews", "Dr N.L.Tshishonga")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, doctorNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        doctorSpinner.adapter = adapter

        backBtn.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()
        }

        val myCalendar = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel(myCalendar)
        }

        datePicker1.setOnClickListener {
            DatePickerDialog(
                this, datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(
                    Calendar.MONTH
                ),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        bookAppointmentButton.setOnClickListener {
            val name = editTextName.text.toString()
            val surname = editTextSurname.text.toString()
            val phone = editTextPhone.text.toString()
            val appointmentdate = txtView2.text.toString()

            val hour = timePicker.hour
            val minute = timePicker.minute

            val appointmenttime = String.format("%02d:%02d", hour, minute)

            currentUser?.let {
                saveAppointmentToFirestore(
                    it,
                    name,
                    surname,
                    phone,
                    appointmentdate,
                    appointmenttime
                )
                saveAppointmentDetailsToSharedPreferences(
                    name,
                    surname,
                    phone,
                    appointmentdate,
                    appointmenttime
                )
                showThankYouMessage()

                val intent = Intent(this, AppointmentDetails::class.java)
                startActivity(intent)
            } ?: run {
                Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun updateLabel(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        txtView2.text = sdf.format(myCalendar.time)
    }

    private fun saveAppointmentToFirestore(
        user: FirebaseUser,
        name: String,
        surname: String,
        phone: String,
        appointmentdate: String,
        appointmenttime: String
    ) {
        val appointment = hashMapOf(
            "name" to name,
            "surname" to surname,
            "phone" to phone,
            "date" to appointmentdate,
            "time" to appointmenttime,
            "doctor" to findViewById<Spinner>(R.id.doctorSpinner).selectedItem.toString()
        )

        val userId = user.uid

        firestore.collection("patients").document(userId).collection("appointments")
            .add(appointment)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(this, "Appointment saved successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error saving appointment: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun showThankYouMessage() {
        Toast.makeText(this, "Thank you for booking an appointment!", Toast.LENGTH_SHORT).show()
    }

    private fun saveAppointmentDetailsToSharedPreferences(
        name: String,
        surname: String,
        phone: String,
        date: String,
        time: String
    ) {
        val sharedPreferences = getSharedPreferences("AppointmentDetails", MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putString("name", name)
            putString("surname", surname)
            putString("phone", phone)
            putString("date", date)
            putString("time", time)
            apply()
        }
    }
}
