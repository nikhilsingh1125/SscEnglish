package com.sscenglishpractice.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.sscenglishpractice.R
import com.sscenglishquiz.model.QuestionData
import libs.mjn.prettydialog.PrettyDialog

object Constants {

    // Store the latest version code of your app (you can update this manually)
    private val latestVersionCode = 2
    fun shareApp(context: Context) {
        val appUrl = "https://play.google.com/store/apps/details?id=com.sscenglishpractice"
//        val telegramUrl = "https://t.me/sscenglishvocabbooster"

        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Sharing my app")
        sharingIntent.putExtra(
            Intent.EXTRA_TEXT,
            "Check out my awesome app! In this app all latest ssc english previous year questions available check it now!\n$appUrl"
        )
        val chooserIntent = Intent.createChooser(sharingIntent, "Share with")
        context.startActivity(chooserIntent)
    }



    // Fetch the current version code of the installed app
    fun getCurrentVersionCode(context: Context): Int {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            -1 // Error occurred while getting version code
        }
    }

    // Show update dialog and redirect to Play Store
    fun showUpdateDialog(context: Context) {


        val pDialog = PrettyDialog(context)
        pDialog.setCancelable(false) // Set the dialog non-cancelable
        pDialog
            .setTitle("Update Available")
            .setIcon(R.drawable.app)
            .setMessage("A new version of the app is available. Update now to access new features.")
            .addButton(
                "OK",  // button text
                R.color.white,  // button text color
                R.color.black_light
            )  // button background color
            {
                // Handle the update button click to redirect to Play Store
                val appPackageName = context.packageName
                try {
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
                } catch (e: ActivityNotFoundException) {
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/search?q=ssc+vocab+booster&c=apps&hl=en-IN")))
                }
                pDialog.dismiss()
            }
            .show()


    }

    // Check for update and show the popup if available
    fun checkAndUpdateApp(context: Context) {
        val currentVersionCode = getCurrentVersionCode(context)
        if (currentVersionCode < latestVersionCode) {
            showUpdateDialog(context)
        }
    }

    fun getBookmarkDataForCategory(category: String) {
        val firestore = FirebaseFirestore.getInstance()
        val collectionRef = firestore.collection("category").document(category).collection("bookData")

        collectionRef.get()
            .addOnSuccessListener { querySnapshot ->
                val bookmarkedQuestions = ArrayList<QuestionData>()

                for (documentSnapshot in querySnapshot.documents) {
                    val questionData = documentSnapshot.toObject(QuestionData::class.java)
                    if (questionData != null) {
                        bookmarkedQuestions.add(questionData)
                        Log.e("BookmarkData", "bookmarkedQuestions ==>: $bookmarkedQuestions")
                    }
                }

            }
            .addOnFailureListener { e ->
                // Handle any errors
                Log.e("SelectedUI", "Error fetching bookmarked data for category $category: ${e.message}", e)
            }
    }

    fun getCategoryNames(){
        val firestore = FirebaseFirestore.getInstance()
        val collectionRef = firestore.collection("category")

        val categoryNames = ArrayList<String>()

        collectionRef.get()
            .addOnSuccessListener { querySnapshot ->
                for (documentSnapshot in querySnapshot.documents) {
                    val categoryName = documentSnapshot.id
                    categoryNames.add(categoryName)
                }
                // Now you have the list of category names
                // You can use this list as an ArrayList<String> or convert it to an array as needed.
                Log.d("SelectedUI", "Category Names: $categoryNames")
            }
            .addOnFailureListener { e ->
                // Handle any errors
                Log.e("SelectedUI", "Error fetching category names: ${e.message}", e)
            }

    }



}