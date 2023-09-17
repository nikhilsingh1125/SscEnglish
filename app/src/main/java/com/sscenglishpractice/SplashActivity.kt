package com.sscenglishpractice

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.sscenglishpractice.utils.Constants

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val deviceId =  Constants.getDeviceId(this)
        Log.d("SplashActivity", "Device ID: $deviceId")

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this,HomeCategoryActivity::class.java))
            finish()
        }, 2000)
    }


}