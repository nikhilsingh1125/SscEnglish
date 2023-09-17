package com.sscenglishpractice

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.sscenglishpractice.adapter.BannerAdapter
import com.sscenglishpractice.adapter.HomeAdapter
import com.sscenglishpractice.model.CategoryModel
import com.sscenglishpractice.model.HomeBannerData
import com.sscenglishpractice.utils.Constants
import kotlinx.android.synthetic.main.activity_main.recyclerView
import kotlinx.android.synthetic.main.custom_toolbar.action_bar_Title
import kotlinx.android.synthetic.main.custom_toolbar.action_bar_back
import kotlinx.android.synthetic.main.custom_toolbar.action_bar_share
import kotlinx.android.synthetic.main.custom_toolbar.btnSubmit


class HomeActivity : AppCompatActivity() {

    val TAG = "HomeActivity"
    private var rewardedAd: RewardedAd? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val TAG = "HomeActivity"

//        getVersion()

       /* MobileAds.initialize(this)



        val adRequest = AdRequest.Builder().build()
        ad_view_home.c(adRequest)*/

//        addBannerImages()


        action_bar_share.visibility = View.GONE
        btnSubmit.visibility = View.GONE
        action_bar_back.visibility = View.VISIBLE
        action_bar_back.setOnClickListener {
            onBackPressed()
            finish()
        }

        action_bar_Title.text = "Practice"


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
        images.add(HomeBannerData(R.drawable.banner_2))


    }

    private var doubleBackToExitPressedOnce = false

    fun goToCategory(model: CategoryModel, position: Int) {

        if (position == 0) {
            val intent = Intent(this, CategoryActivity::class.java)
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
    fun getVersion() {
        val db = FirebaseFirestore.getInstance()

        // Reference to a collection
        val collectionRef = db.collection("app_version")

        // Get documents in the collection
        collectionRef.get()
            .addOnSuccessListener { querySnapshot ->
                Log.d("FirestoreData", "querySnapshot: $querySnapshot")
                for (doc in querySnapshot) {
                    val versionData = doc.data
                    val documentId = doc.id
                    val versionCodeValue = versionData["version_code"]

                    if (versionCodeValue is Number) {
                        val versionCode = versionCodeValue.toInt()
                        Log.d("FirestoreData", "Document ID: $documentId, versionCode: $versionCode")
                        checkAndUpdateApp(versionCode)
                    } else {
                        Log.e("FirestoreData", "Invalid versionCode format in document: $documentId")
                    }
                }
            }
            .addOnFailureListener { error ->
                Log.e("FirestoreError", "Error getting documents: $error")
            }


    }



    fun checkAndUpdateApp(versionCode: Int) {
        val currentVersionCode = Constants.getCurrentVersionCode(this)

        if (currentVersionCode != versionCode) {
            Constants.showUpdateDialog(this)
        }
    }

}