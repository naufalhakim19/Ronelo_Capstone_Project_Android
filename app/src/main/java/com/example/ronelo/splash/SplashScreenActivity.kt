package com.example.ronelo.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.example.ronelo.MainActivity
import com.example.ronelo.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spalsh_screen)

        val bottomAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom)

        var tvTitle: TextView = findViewById(R.id.tv_title)
        tvTitle.startAnimation(bottomAnimation)

        var ivSplash: ImageView = findViewById(R.id.iv_splash)
        ivSplash.alpha = 0f
        ivSplash.animate().setDuration(2000).alpha(1f).withEndAction{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}