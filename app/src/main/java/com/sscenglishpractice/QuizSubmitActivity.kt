package com.sscenglishpractice

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import kotlinx.android.synthetic.main.activity_quiz_submit.btnReattempt
import kotlinx.android.synthetic.main.activity_quiz_submit.btnViewSolution
import kotlinx.android.synthetic.main.activity_quiz_submit.pieChart
import kotlinx.android.synthetic.main.activity_quiz_submit.txtCorrectAccuracy
import kotlinx.android.synthetic.main.activity_quiz_submit.txtCorrectCount
import kotlinx.android.synthetic.main.activity_quiz_submit.txtWrongCount
import kotlinx.android.synthetic.main.custom_toolbar.action_bar_Title
import kotlinx.android.synthetic.main.custom_toolbar.action_bar_share
import kotlinx.android.synthetic.main.custom_toolbar.btnSubmit
import kotlin.math.roundToInt

class QuizSubmitActivity : AppCompatActivity() {

    var rewardedAd: RewardedAd? = null
    var adRequest = AdRequest.Builder().build()
    val TAGAD = "QuizSubmitActivity"

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_submit)

        val sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)

        action_bar_Title.text = "Overall Result"
        btnSubmit.visibility = View.GONE
        action_bar_share.visibility = View.GONE

        MobileAds.initialize(this)


        RewardedAd.load(
            this,
            "ca-app-pub-7484865336777284/3660075926",
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    adError.toString()?.let { Log.d(TAGAD, it) }
                    rewardedAd = null
                }

                override fun onAdLoaded(ad: RewardedAd) {
                    Log.d(TAGAD, "Ad was loaded.")
                    rewardedAd = ad
                }
            })


        val correctAnswer = sharedPreferences.getString("correctAnswer", null)?.toIntOrNull()
        val totalQuestion = sharedPreferences.getString("totalQuestion", null)?.toIntOrNull()
        val incorrectAnswer = sharedPreferences.getString("incorrectAnswer", null)
        val title = sharedPreferences.getString("Title", null)
        val category = sharedPreferences.getString("categoryData", null)

        Log.e("Result", "correctAnswer: $correctAnswer")
        Log.e("Result", "totalQuestion: $totalQuestion")
        Log.e("Result", "incorrectAnswer: $incorrectAnswer")

        val accuracy = if (correctAnswer != null && totalQuestion != null && totalQuestion > 0) {
            (correctAnswer.toDouble() / totalQuestion.toDouble()) * 100
        } else {
            0f
        }

// You can use the accuracy value as needed, such as displaying it in a TextView or logging it
        Log.e("Accuracy", "Accuracy: $accuracy%")
        val accuracyPercentage: Double = accuracy as Double
        val accuracy1: Int = accuracyPercentage.roundToInt()
        println("Accuracy aaa: $accuracy1%")
        Log.e("Accuracy", "totalQuestion: $totalQuestion")
        Log.e("Accuracy", "title: $title")

        txtCorrectCount.text = correctAnswer.toString()
        txtWrongCount.text = incorrectAnswer
        txtCorrectAccuracy.text = "$accuracy1%"


        val correctAnswerValue =
            correctAnswer?.toFloat() ?: 0f // Replace with the actual value of correct answers
        val incorrectAnswerValue =
            incorrectAnswer?.toFloat() ?: 0f // Replace with the actual value of incorrect answers


        val entries = listOf(
            PieEntry(correctAnswerValue, ""),
            PieEntry(incorrectAnswerValue, "")
        )
        val dataSet = PieDataSet(entries, "Answer Summary")

        val colorCorrect = ContextCompat.getColor(this, R.color.green_dark)
        val colorWrong = ContextCompat.getColor(this, R.color.light_red)

        dataSet.setColors(colorCorrect, colorWrong)
        dataSet.valueTextColor = Color.WHITE
        dataSet.valueTextSize = 16f // Adjust the size as desired


        val legend: Legend = pieChart.legend
        legend.textColor = Color.WHITE
        pieChart.legend.isEnabled = false


        val data = PieData(dataSet)
        pieChart.holeRadius = 50f
        pieChart.setEntryLabelTextSize(12f)
        pieChart.setDrawEntryLabels(true)
        pieChart.description.isEnabled = false

        pieChart.data = data
        pieChart.invalidate()





        btnReattempt.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        Log.d("QuizSubmitActivity", "correctAnswer 1: $correctAnswer")
        Log.d("QuizSubmitActivity", "incorrectAnswer 2: $incorrectAnswer")
        Log.d("QuizSubmitActivity", "totalQuestion 3: $totalQuestion")

        btnViewSolution.setOnClickListener {
            rewardedAd?.let { ad ->
                ad.show(this, OnUserEarnedRewardListener { rewardItem ->
                    startActivity(Intent(this, ViewSolutionActivity::class.java))
                    finishAffinity()
                })
            } ?: run {
                startActivity(Intent(this, ViewSolutionActivity::class.java))
                finishAffinity()
            }
        }


    }


}