package com.sscenglishpractice

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_about_us.btnGmail
import kotlinx.android.synthetic.main.activity_about_us.btnRating
import kotlinx.android.synthetic.main.activity_about_us.btnTelegram
import kotlinx.android.synthetic.main.activity_about_us.txtVersionName
import kotlinx.android.synthetic.main.custom_toolbar.action_bar_Title
import kotlinx.android.synthetic.main.custom_toolbar.action_bar_back
import kotlinx.android.synthetic.main.custom_toolbar.action_bar_share
import kotlinx.android.synthetic.main.custom_toolbar.btnSubmit

class AboutUsActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)


        action_bar_share.visibility = View.GONE
        btnSubmit.visibility = View.GONE
        action_bar_back.visibility = View.VISIBLE
        action_bar_back.setOnClickListener {
            onBackPressed()
        }

        val versionName = getVersionName()

        txtVersionName.text = "Version: $versionName"

        action_bar_Title.text = "Info"

        handleClicks()
    }

    private fun handleClicks() {

        btnTelegram.setOnClickListener {
            val telegramLink = "https://t.me/sscenglishvocabbooster"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(telegramLink))
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }

        btnGmail.setOnClickListener {
            val emailAddress = "ronakpanwar0001@gmail.com"

            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "message/rfc822"
                putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
                putExtra(Intent.EXTRA_SUBJECT, "Subject Here")
                putExtra(Intent.EXTRA_TEXT, "Hello,")
            }

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }

        btnRating.setOnClickListener {
            val playStoreUrl = "https://play.google.com/store/apps/details?id=com.sscenglishpractice"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(playStoreUrl))

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }

        }

    }

    private fun getVersionName(): String {
        try {
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            return packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return "Unknown"
    }
}
