package com.sscenglishpractice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.sscenglishpractice.adapter.QuestionPositionAdapter
import com.sscenglishpractice.model.QuizQuestion
import kotlinx.android.synthetic.main.activity_quiz_result.btnHome
import kotlinx.android.synthetic.main.activity_quiz_result.btnViewSolutionQuiz
import kotlinx.android.synthetic.main.activity_quiz_result.result_bird
import kotlinx.android.synthetic.main.activity_quiz_result.txtCorrectAccuracy
import kotlinx.android.synthetic.main.activity_quiz_result.txtCorrectCount
import kotlinx.android.synthetic.main.activity_quiz_result.txtTimeTaken
import kotlinx.android.synthetic.main.activity_quiz_result.txtWrongCount

class QuizResultActivity : AppCompatActivity() {

    val TAG = "QuizResultActivity"
    lateinit var rvAdapter : QuestionPositionAdapter
    var isSolutionResult = false

    var correctAns = 0
    var wrongAns = 0
    var totalQuestions = 0
    var formattedDifference = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_result)


        val intent = intent
        correctAns = intent.getIntExtra("CorrectAnswer",0)
        wrongAns = intent.getIntExtra("WrongAnswer",0)
        totalQuestions = intent.getIntExtra("TotalQuestions",0)
        val time = intent.getStringExtra("Time")
        val totalTime = intent.getStringExtra("TotalTime")

        val accuracy = (correctAns.toFloat() / totalQuestions.toFloat()) * 100
        val formattedAccuracy = String.format("%.2f%%", accuracy)

        Log.e(TAG, "correctAns : $correctAns")
        Log.e(TAG, "wrongAns : $wrongAns")
        Log.e(TAG, "totalQuestions : $totalQuestions")
        Log.e(TAG, "Time : $time")
        Log.e(TAG, "formattedAccuracy : $formattedAccuracy")
        Log.e(TAG, "totalTime : $totalTime")


        if (time != ""){
            timeTaken(time,totalTime)
        }

        txtCorrectCount.text = correctAns.toString()
        txtWrongCount.text = wrongAns.toString()
        txtCorrectAccuracy.text = formattedAccuracy

        handleClicks()

        result_bird.setAnimation(R.raw.result_bird)
        result_bird.playAnimation()
    }

    private fun timeTaken(time: String?, totalTime: String?) {
        if (time.isNullOrEmpty()) {
            // Handle the case when the time is null or empty
            // You might want to display an error message or handle this situation in some way
            return
        }

        val endTime = totalTime.toString()

        fun timeToSeconds(time: String): Int {
            val timeParts = time.split(":")
            val hours = timeParts[0].toInt()
            val minutes = timeParts[1].toInt()
            val seconds = timeParts[2].toInt()
            return (hours * 3600) + (minutes * 60) + seconds
        }

        val startTimeInSeconds = timeToSeconds(time)
        val endTimeInSeconds = timeToSeconds(endTime)

        val differenceInSeconds = endTimeInSeconds - startTimeInSeconds

        val differenceMinutes = differenceInSeconds / 60
        val differenceSeconds = differenceInSeconds % 60

        val formattedDifference = String.format("%d min %d s", differenceMinutes, differenceSeconds)

        Log.e(TAG, "formattedTime==> : $formattedDifference")
        txtTimeTaken.text = formattedDifference
    }


    private fun handleClicks() {
        btnViewSolutionQuiz.setOnClickListener {
            isSolutionResult = true
            val intent = Intent(this,ExamQuizsActivity::class.java)
            intent.putExtra("ResultSolution",isSolutionResult)
            startActivity(intent)
            finish()
        }

        btnHome.setOnClickListener {
            val intent = Intent(this,QuizCategoryActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getResultData() {
        val databaseReference = FirebaseDatabase.getInstance().getReference("QuizResults")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val quizResults = mutableListOf<QuizQuestion>()

                for (childSnapshot in dataSnapshot.children) {
                    val questions = childSnapshot.child("questions")
                        .getValue(object : GenericTypeIndicator<List<QuizQuestion>>() {})

                    val quizResult = questions?.let { QuizQuestion() }
                    if (quizResult != null) {
                        quizResults.add(quizResult)
                        Log.d("SelectedUI", "QuizResults ===>: $questions")
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })

    }

}