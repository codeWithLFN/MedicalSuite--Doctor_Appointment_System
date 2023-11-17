package com.example.medicalexample

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Login : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var forgot:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Assigning values
        emailEditText = findViewById(R.id.edt_email)
        passwordEditText = findViewById(R.id.edt_confirmPassword)
        loginButton = findViewById(R.id.btn_login)
        registerButton = findViewById(R.id.txt_register)
        forgot = findViewById(R.id.forget_password_text)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()


        //Takes you to register button
        registerButton.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
        }
        forgot.setOnClickListener {
            startActivity(Intent(this, forgot_password::class.java))
        }

        //Takes you to home page after registering
        loginButton.setOnClickListener {
            val usernameText = emailEditText.text.toString().trim()
            val passwordText = passwordEditText.text.toString().trim()

            if (!TextUtils.isEmpty(usernameText) && !TextUtils.isEmpty(passwordText)) {
                db.collection("patients").whereEqualTo("email", usernameText).get()
                    .addOnSuccessListener { documents ->
                        if (!documents.isEmpty) {
                            val userData = documents.documents[0].toObject(User::class.java)
                            if (userData != null && userData.password == passwordText) {
                                auth.signInWithEmailAndPassword(usernameText, passwordText)
                                    .addOnCompleteListener(this) { task ->
                                        if (task.isSuccessful) {
                                            val message = "Login successful: email=$usernameText"
                                            val intent = Intent(this, Home::class.java)
                                            startActivity(intent)
                                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                                        } else {
                                            val message = "Login failed: ${task.exception?.message}"
                                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                                        }
                                    }
                            } else {
                                val message = "Incorrect username or password"
                                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            val message = "User not found"
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                        }
                    }.addOnFailureListener { exception ->
                        val message = "Error: ${exception.message}"
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
    fun onEmergencyButtonClick(view: View) {
        // Create an Intent to open the dial pad
        val dialIntent = Intent(Intent.ACTION_DIAL)
        // Set the phone number (you can change this to your local emergency number)
        dialIntent.data = Uri.parse("tel:0127930082")
        // Start the dialer activity
        startActivity(dialIntent)
    }
}