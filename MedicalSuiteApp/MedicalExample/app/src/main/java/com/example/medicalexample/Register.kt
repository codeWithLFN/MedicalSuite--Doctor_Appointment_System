package com.example.medicalexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.w3c.dom.Text

class Register : AppCompatActivity() {

    private lateinit var registerEmailEditText: EditText
    private lateinit var registerPasswordEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var backBtn: ImageButton
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var login: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Assigning values
        registerEmailEditText = findViewById(R.id.registerEmailEditText)
        registerPasswordEditText = findViewById(R.id.registerPasswordEditText)
        registerButton = findViewById(R.id.registerButton)
        backBtn = findViewById(R.id.btn_back)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        login = findViewById(R.id.txt_login)

        login.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        // Signing Up users and storing details within the database
        registerButton.setOnClickListener {
            val username = registerEmailEditText.text.toString()
            val password = registerPasswordEditText.text.toString()

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                auth.createUserWithEmailAndPassword(username, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val message =
                                "Registration successful: email=$username, Password=$password"
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

                            val currentUser = auth.currentUser
                            val user = hashMapOf(
                                "email" to username,
                                "password" to password
                            )

                            if (currentUser != null) {
                                db.collection("patients").document(currentUser.uid)
                                    .set(user)
                                    .addOnSuccessListener {
                                        // Send verification email
                                        currentUser.sendEmailVerification()
                                            .addOnCompleteListener { task ->
                                                if (task.isSuccessful) {
                                                    Toast.makeText(
                                                        this,
                                                        "Verification email sent",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                } else {
                                                    Toast.makeText(
                                                        this,
                                                        "Failed to send verification email",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            }
                                        val intent = Intent(this, Login::class.java)
                                        startActivity(intent)
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(
                                            this,
                                            "Failed to add user to Firestore",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                            }
                        } else {
                            val exception = task.exception
                            Toast.makeText(this, exception?.message, Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        // Takes you back to the login page
        backBtn.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
    }
}
