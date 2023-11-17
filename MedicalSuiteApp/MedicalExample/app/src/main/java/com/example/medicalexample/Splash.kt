package com.example.medicalexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import com.bumptech.glide.Glide


class Splash : AppCompatActivity() {

    private val SplashScreen: Long = 4000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val backgroundImageView = findViewById<ImageView>(R.id.image)
        Glide.with(this)
            .asGif()
            .load(R.drawable.image)
            .into(backgroundImageView)


        Handler(Looper.getMainLooper()).postDelayed({
            // This code will run after the splash screen timeout.
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }, SplashScreen)

    }
}