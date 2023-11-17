package com.example.medicalexample

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore

class Profile : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    private lateinit var tvUsername: TextView
    private lateinit var etCurrentPassword: EditText
    private lateinit var etNewPassword: EditText
    private lateinit var btnChangePassword: Button
    private lateinit var etDeleteAccountPassword: EditText
    private lateinit var btnDeleteAccount: Button
    private lateinit var btnLogout: Button
    private lateinit var backBtn: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        tvUsername = findViewById(R.id.tvUsername)
        etCurrentPassword = findViewById(R.id.etCurrentPassword)
        etNewPassword = findViewById(R.id.etNewPassword)
        btnChangePassword = findViewById(R.id.btnChangePassword)
        etDeleteAccountPassword = findViewById(R.id.etDeleteAccountPassword)
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount)
        btnLogout = findViewById(R.id.btnLogout)
        backBtn = findViewById(R.id.btn_back)

        backBtn.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()}

        val currentUser = auth.currentUser
        currentUser?.let {
            val username = currentUser.displayName
            tvUsername.text = "Username: $username"
        }

        btnChangePassword.setOnClickListener {
            changePassword()
        }

        btnDeleteAccount.setOnClickListener {
            deleteAccount()
        }

        btnLogout.setOnClickListener {
            // Perform logout logic using Firebase Auth
            auth.signOut()

            // Notify the user and start the login activity
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, Login::class.java))
        }
    }

    private fun changePassword() {
        val currentPassword = etCurrentPassword.text.toString().trim()
        val newPassword = etNewPassword.text.toString().trim()

        // Perform change password logic using Firebase Auth
        val patients = auth.currentUser
        if (patients != null && currentPassword.isNotEmpty() && newPassword.isNotEmpty()) {
            val credential = EmailAuthProvider.getCredential(patients.email!!, currentPassword)
            patients.reauthenticate(credential).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    patients.updatePassword(newPassword).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Password updated successfully", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this, "Error updating password", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_LONG).show()
                }
            }
        } else {
            Toast.makeText(this, "Please enter all the fields", Toast.LENGTH_LONG).show()
        }

        etCurrentPassword.text.clear()
        etNewPassword.text.clear()
    }

    private fun deleteAccount() {
        val deleteAccountPassword = etDeleteAccountPassword.text.toString().trim()

        // Perform delete account logic using Firebase Auth
        val userData = auth.currentUser
        if (userData != null && deleteAccountPassword.isNotEmpty()) {
            val credential = EmailAuthProvider.getCredential(userData.email!!, deleteAccountPassword)
            userData.reauthenticate(credential).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    userData.delete().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Account deleted successfully", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this, Login::class.java))
                        } else {
                            Toast.makeText(this, "Failed to delete account", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_LONG).show()
                }
            }
        } else {
            Toast.makeText(this, "Please enter the account password", Toast.LENGTH_LONG).show()
        }

        etDeleteAccountPassword.text.clear()
    }
}
