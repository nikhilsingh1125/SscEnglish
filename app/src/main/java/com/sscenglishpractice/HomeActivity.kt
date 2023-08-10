package com.sscenglishpractice

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.firebase.messaging.FirebaseMessaging
import com.sscenglishpractice.adapter.BannerAdapter
import com.sscenglishpractice.adapter.HomeAdapter
import com.sscenglishpractice.model.CategoryModel
import com.sscenglishpractice.model.HomeBannerData
import com.sscenglishpractice.utils.Constants
import kotlinx.android.synthetic.main.activity_home.ad_view_home
import kotlinx.android.synthetic.main.activity_home.indicator
import kotlinx.android.synthetic.main.activity_home.viewPagerMain
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_toolbar.*
import libs.mjn.prettydialog.PrettyDialog


class HomeActivity : AppCompatActivity() {

    val TAG = "HomeActivity"
    private var rewardedAd: RewardedAd? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val TAG = "HomeActivity"

        MobileAds.initialize(this)

        Constants.checkAndUpdateApp(this)
        val adRequest = AdRequest.Builder().build()
        ad_view_home.loadAd(adRequest)

        addBannerImages()


        RewardedAd.load(
            this,
            "ca-app-pub-3940256099942544/5224354917",
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    adError.toString().let { Log.d(TAG, it) }
                    rewardedAd = null
                }

                override fun onAdLoaded(ad: RewardedAd) {
                    Log.d(TAG, "Ad was loaded.")
                    rewardedAd = ad
                }
            })


        action_bar_share.setOnClickListener {
            Constants.shareApp(this)
        }
        btnSubmit.visibility = View.GONE


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "22"
            val channelName = "SSC-English"
            val channelDescription = "My Channel Description"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
            }
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
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
        array.add(CategoryModel("2023", R.drawable.study))
        array.add(CategoryModel("2022", R.drawable.study))
        array.add(CategoryModel("2021", R.drawable.study))
        array.add(CategoryModel("2020", R.drawable.study))
        array.add(CategoryModel("2019", R.drawable.study))


        recyclerView.layoutManager = GridLayoutManager(this@HomeActivity, 2)
        val rvAdapter = HomeAdapter(this@HomeActivity, array)
        recyclerView.adapter = rvAdapter
    }

    private fun addBannerImages() {

        val images = ArrayList<HomeBannerData>()
        images.add(HomeBannerData(R.drawable.quotes_banner))
        images.add(HomeBannerData(R.drawable.quotes))


        val mViewPagerAdapter = BannerAdapter(this@HomeActivity, images)

        // Adding the Adapter to the ViewPager

        // Adding the Adapter to the ViewPager
        viewPagerMain.adapter = mViewPagerAdapter
        indicator.setViewPager(viewPagerMain)
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

    fun goToCategory(model: CategoryModel, position: Int) {

        if (position == 0) {
//            Toast.makeText(this, "Upcoming Feature", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ExamQuizsActivity::class.java)
            intent.putExtra("TYPE", "2023")
            startActivity(intent)
        } else if (position == 1) {
            val intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("TYPE", "2022")
            startActivity(intent)
        } else if (position == 2) {
            val intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("TYPE", "2021")
            startActivity(intent)
        } else if (position == 3) {
            val intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("TYPE", "2020")
            startActivity(intent)
        } else if (position == 4) {
            val intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("TYPE", "2019")
            startActivity(intent)
        }

        fun showAd() {
            rewardedAd?.let { ad ->
                ad.show(this, OnUserEarnedRewardListener { rewardItem ->
                    // Handle the reward.
                    val rewardAmount = rewardItem.amount
                    val rewardType = rewardItem.type
                    Toast.makeText(this, "UnLocked", Toast.LENGTH_SHORT).show()
                })
            } ?: run {
                Log.d(TAG, "The rewarded ad wasn't ready yet.")
            }
        }


    }


}