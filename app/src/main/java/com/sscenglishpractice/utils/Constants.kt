package com.sscenglishpractice.utils

import android.content.Context
import android.content.Intent

object Constants {

    fun shareApp(context: Context) {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Sharing my app")
        sharingIntent.putExtra(Intent.EXTRA_TEXT, "Check out my awesome app! It has amazing features and a user-friendly interface.")
        val chooserIntent = Intent.createChooser(sharingIntent, "Share with")
        context.startActivity(chooserIntent)

    }

}