package com.example.medicalexample

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class Home : AppCompatActivity() {

    private lateinit var Dr_Linda_Btn: Button
    private lateinit var Dr_Claire_Blake_Btn: Button
    private lateinit var Dr_Ndivhuho_Lionel_Tshishonga_Btn: Button
    private lateinit var menuButton: ImageButton
    private lateinit var healthTipsButton: Button
    private lateinit var viewAppointmentsButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        Dr_Linda_Btn = findViewById(R.id.Dr_Linda_Btn)
        Dr_Claire_Blake_Btn = findViewById(R.id.Dr_Claire_Blake_Btn)
        Dr_Ndivhuho_Lionel_Tshishonga_Btn = findViewById(R.id.Dr_Ndivhuho_Lionel_Tshishonga_Btn)
        menuButton = findViewById(R.id.menuBtn)
        healthTipsButton = findViewById(R.id.healthTipsButton)
        viewAppointmentsButton = findViewById(R.id.viewAppointment)

        Dr_Linda_Btn.setOnClickListener {
            val intent = Intent(this, AppointmentActivity::class.java)
            startActivity(intent)
        }

        Dr_Claire_Blake_Btn.setOnClickListener {
            val intent = Intent(this, AppointmentActivity::class.java)
            startActivity(intent)
        }

        Dr_Ndivhuho_Lionel_Tshishonga_Btn.setOnClickListener {
            val intent = Intent(this, AppointmentActivity::class.java)
            startActivity(intent)
        }

        viewAppointmentsButton.setOnClickListener {
            val intent = Intent(this, AppointmentDetails::class.java)
            startActivity(intent)
        }

        menuButton.setOnClickListener {
            val popupMenu = PopupMenu(this@Home, menuButton)
            popupMenu.menuInflater.inflate(R.menu.menu1, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.profileBtn -> {
                        showProfile()
                        true
                    }
                    R.id.appointmentsBtn -> {
                        showAppointment()
                        true
                    }
                    R.id.Mapbtn -> {
                        showMap()
                        true
                    }
                    else -> false
                }
            }

            popupMenu.show()
        }

        healthTipsButton.setOnClickListener {
            val intent = Intent(this, HealthTipsActivity::class.java)
            startActivity(intent)
        }
    }



    private fun showMap() {
        startActivity(Intent(this, Map::class.java))
        finish()
    }

    private fun showAppointment() {
        startActivity(Intent(this, AppointmentDetails::class.java))
        finish()
    }

    private fun showProfile() {
        startActivity(Intent(this, Profile::class.java))
        finish()
    }
}
