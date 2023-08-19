package com.sscenglishpractice

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sscenglishpractice.utils.Constants
import kotlinx.android.synthetic.main.activity_about_us.btnGmail
import kotlinx.android.synthetic.main.activity_about_us.btnRating
import kotlinx.android.synthetic.main.activity_about_us.btnTelegram
import kotlinx.android.synthetic.main.custom_toolbar.action_about
import kotlinx.android.synthetic.main.custom_toolbar.action_bar_Title
import kotlinx.android.synthetic.main.custom_toolbar.action_bar_back
import kotlinx.android.synthetic.main.custom_toolbar.action_bar_share
import kotlinx.android.synthetic.main.custom_toolbar.btnSubmit

class AboutUsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)


        action_about.visibility = View.GONE
        action_bar_share.visibility = View.GONE
        btnSubmit.visibility = View.GONE
        action_bar_back.visibility = View.VISIBLE
        action_bar_back.setOnClickListener {
            onBackPressed()
        }

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
            val playStoreUrl = "https://play.google.com/store/search?q=ssc+vocab+booster&c=apps&hl=en-IN"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(playStoreUrl))

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }

        }

    }
}
