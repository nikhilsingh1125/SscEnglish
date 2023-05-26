package com.sscenglishpractice

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.android.synthetic.main.activity_quiz_submit.btnReattempt
import kotlinx.android.synthetic.main.activity_quiz_submit.pieChart
import kotlinx.android.synthetic.main.activity_quiz_submit.txtCorrectCount
import kotlinx.android.synthetic.main.activity_quiz_submit.txtWrongCount
import kotlinx.android.synthetic.main.custom_toolbar.action_bar_Title
import kotlinx.android.synthetic.main.custom_toolbar.action_bar_share
import kotlinx.android.synthetic.main.custom_toolbar.btnSubmit

class QuizSubmitActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_submit)

        val sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)

        action_bar_Title.text = "Overall Result"
        btnSubmit.visibility = View.GONE
        action_bar_share.visibility = View.GONE


        val correctAnswer = sharedPreferences.getString("correctAnswer", null)?.toIntOrNull()
        val totalQuestion = sharedPreferences.getString("totalQuestion", null)?.toIntOrNull()
        val incorrectAnswer = sharedPreferences.getString("incorrectAnswer", null)


        val accuracy = if (correctAnswer != null && totalQuestion != null && totalQuestion > 0) {
            (correctAnswer.toFloat() / totalQuestion.toFloat()) * 100
        } else {
            0f
        }

// You can use the accuracy value as needed, such as displaying it in a TextView or logging it
        Log.e("Accuracy", "Accuracy: $accuracy%")
        Log.e("Accuracy", "totalQuestion: $totalQuestion")




        txtCorrectCount.text = correctAnswer.toString()
        txtWrongCount.text = incorrectAnswer



        val correctAnswerValue = correctAnswer?.toFloat() ?: 0f // Replace with the actual value of correct answers
        val incorrectAnswerValue = incorrectAnswer?.toFloat() ?: 0f // Replace with the actual value of incorrect answers


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
            startActivity(Intent(this,HomeActivity::class.java))
        }

        Log.d("QuizSubmitActivity", "correctAnswer 1: $correctAnswer")
        Log.d("QuizSubmitActivity", "incorrectAnswer 2: $incorrectAnswer")
        Log.d("QuizSubmitActivity", "totalQuestion 3: $totalQuestion")



    }
}