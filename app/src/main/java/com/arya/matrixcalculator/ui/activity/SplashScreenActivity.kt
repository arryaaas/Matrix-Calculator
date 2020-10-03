package com.arya.matrixcalculator.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.arya.matrixcalculator.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({

            startActivity(Intent(this, MainActivity::class.java))
            finish()

        }, 2000)

        val stb = AnimationUtils.loadAnimation(this, R.anim.stb)

        val imageView = findViewById<ImageView>(R.id.imageview_splash)

        imageView.startAnimation(stb)
    }
}