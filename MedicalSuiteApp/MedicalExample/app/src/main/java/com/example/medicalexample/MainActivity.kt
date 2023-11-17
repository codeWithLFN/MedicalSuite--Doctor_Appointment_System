package com.example.medicalexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var btn_login : Button
    private lateinit var btn_register : Button
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_login = findViewById(R.id.btn_login)
        btn_register = findViewById(R.id.btn_register)

        auth = FirebaseAuth.getInstance()
        checkUser();

        btn_login.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }
        btn_register.setOnClickListener {
            startActivity(Intent(this, Register::class.java))


        }
    }
    private fun checkUser() {

        val firebaseUser = auth.currentUser
        if (firebaseUser == null){

        }
        else{
                startActivity(Intent(this@MainActivity, Home::class.java))
                finish()
                    }
    }
}