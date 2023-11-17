package com.example.medicalexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

private lateinit var Email:EditText
private lateinit var button: Button
private lateinit var auth: FirebaseAuth
private lateinit var backbtn :ImageButton

class forgot_password : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        Email= findViewById(R.id.edt_email)
        button=findViewById(R.id.btn_forgotPassword)
        auth = FirebaseAuth.getInstance()
        backbtn= findViewById(R.id.btn_back)
        button.setOnClickListener{validateData()}

        backbtn.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
    }

    //local variable
    private var email = ""

    private fun validateData() {
        Email= findViewById(R.id.edt_email)
        email = Email.text.toString().trim()

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(
                baseContext, "Enter email...",
                Toast.LENGTH_SHORT
            ).show()
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(
                baseContext, "Invalid email pattern...",
                Toast.LENGTH_SHORT
            ).show()
        }
        else{
            recoverPassword()
        }
    }

    private fun recoverPassword() {



        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                //sent
                Toast.makeText(
                    baseContext, "Instructions sent to \n$email",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener { e ->
                //failed
                Toast.makeText(baseContext, "Login Failed Due To ${e.message}", Toast.LENGTH_SHORT).show()
            }

        startActivity(Intent(this, Login::class.java))
    }
}

