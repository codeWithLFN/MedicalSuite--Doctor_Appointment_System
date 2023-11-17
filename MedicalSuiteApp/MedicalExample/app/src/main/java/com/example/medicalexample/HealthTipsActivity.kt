package com.example.medicalexample

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class HealthTipsActivity : AppCompatActivity() {

    private lateinit var backBtn: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_tips)

        //Assigning values
        val healthTips = getHealthTips()
        val healthTipsTextView: TextView = findViewById(R.id.healthTipsTextView)
        backBtn = findViewById(R.id.btn_back)
        healthTipsTextView.movementMethod = LinkMovementMethod.getInstance()


        //Image click will take you to home page
        backBtn.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()
        }

        // Create a SpannableString with clickable link
        val spannableString = SpannableString(healthTips[1].description)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                // Open the website when clicked
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.nhs.uk/live-well/eat-well/how-to-eat-a-balanced-diet/eight-tips-for-healthy-eating/")
                )
                startActivity(intent)
            }
        }

        val startIndexOfLink = healthTips[1].description.indexOf("www.epa.gov")
        spannableString.setSpan(
            clickableSpan,
            startIndexOfLink,
            startIndexOfLink + 18,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        healthTipsTextView.text = spannableString

        val drinkwaterImageView: ImageView = findViewById(R.id.drinkwater)
        Glide.with(this)
            .asGif()
            .load(R.drawable.drinkwater)
            .into(drinkwaterImageView)

    }

    private fun getHealthTips(): List<HealthTip> {
        return listOf(
            HealthTip(
                "Tip 1",
                "Eat a balanced diet rich in fruits, vegetables, whole grains, and lean proteins."
            ),
            HealthTip(
                "Tip 2",
                "Stay Hydrated and Limit Sugared Beverages. Drink water regularly to stay healthy, but there is NO evidence that drinking water frequently (e.g. every 15 minutes) can help prevent any viral infection. For more information on drinking water and coronavirus, please check the following EPA website: www.epa.gov/coronavirus/coronavirus-and-drinking-water-and-wastewater"
            ),
            HealthTip("Tip 3", "Exercise regularly to maintain physical and mental well-being."),
            HealthTip(
                "Tip 4",
                "Get enough sleep. Aim for 7-9 hours of quality sleep per night. For more information on improving sleep quality, visit the National Sleep Foundation's website: www.sleepfoundation.org"
            ),
            HealthTip(
                "Tip 5",
                "Practice good hygiene. Wash your hands regularly and cover your mouth and nose when coughing or sneezing."
            ),
            HealthTip(
                "Tip 6",
                "Manage stress through relaxation techniques like meditation, deep breathing, and yoga."
            ),
            HealthTip("Tip 7", "Avoid smoking and limit alcohol consumption."),
            HealthTip(
                "Tip 8",
                "Regularly check in with your healthcare provider for preventive screenings and check-ups."
            )


        )
    }
}
