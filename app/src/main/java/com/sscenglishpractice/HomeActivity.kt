package com.sscenglishpractice

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.lottie.utils.Utils
import com.google.firebase.messaging.FirebaseMessaging
import com.sscenglishpractice.adapter.HomeAdapter
import com.sscenglishpractice.model.CategoryModel
import com.sscenglishpractice.utils.Constants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_toolbar.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val TAG= "HomeActivity"

        action_bar_share.setOnClickListener {
            Constants.shareApp(this)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "22"
            val channelName = "SSC-English"
            val channelDescription = "My Channel Description"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
            }
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        FirebaseMessaging.getInstance().subscribeToTopic("my_topic")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Subscribed to my_topic")
                } else {
                    Log.e(TAG, "Failed to subscribe to my_topic", task.exception)
                }
            }

        val array = ArrayList<CategoryModel>()

        array.add(CategoryModel("Practice", R.drawable.practice))
        array.add(CategoryModel("Practice", R.drawable.practice))
        array.add(CategoryModel("Practice", R.drawable.practice))
        array.add(CategoryModel("Practice", R.drawable.practice))
        array.add(CategoryModel("Practice", R.drawable.practice))
        array.add(CategoryModel("Practice", R.drawable.practice))
        array.add(CategoryModel("Practice", R.drawable.practice))
        array.add(CategoryModel("Practice", R.drawable.practice))
        array.add(CategoryModel("Practice", R.drawable.practice))
        array.add(CategoryModel("Practice", R.drawable.practice))
        array.add(CategoryModel("Practice", R.drawable.practice))
        array.add(CategoryModel("Practice", R.drawable.practice))

        recyclerView.layoutManager = GridLayoutManager(this@HomeActivity,2)
        val rvAdapter = HomeAdapter(this@HomeActivity, array)
        recyclerView.adapter = rvAdapter
    }
    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            doubleBackToExitPressedOnce = false
        }, 2000)
    }
}