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

        val accuracy = (correctAns.toFloat() / totalQuestions.toFloat()) * 100
        val formattedAccuracy = String.format("%.2f%%", accuracy)

        Log.e(TAG, "correctAns : $correctAns")
        Log.e(TAG, "wrongAns : $wrongAns")
        Log.e(TAG, "totalQuestions : $totalQuestions")
        Log.e(TAG, "Time : $time")
        Log.e(TAG, "formattedAccuracy : $formattedAccuracy")


        timeTaken(time)
        txtCorrectCount.text = correctAns.toString()
        txtWrongCount.text = wrongAns.toString()
        txtCorrectAccuracy.text = formattedAccuracy
        // Split the time string into hours, minutes, and seconds
        val timeParts = time?.split(":")
        val hours = timeParts?.get(0)?.toInt()
        val minutes = timeParts?.get(1)?.toInt()
        val seconds = timeParts?.get(2)?.toInt()

// Format the time as "mm min ss s"
        val formattedTime = String.format("%d min %d s", minutes, seconds)


       // getResultData()
        handleClicks()

        result_bird.setAnimation(R.raw.result_bird)
        result_bird.playAnimation()
    }

    private fun timeTaken(time: String?) {
        val startTime = time
        val endTime = "00:12:00"

        fun timeToSeconds(time: String): Int {
            val timeParts = time.split(":")
            val hours = timeParts[0].toInt()
            val minutes = timeParts[1].toInt()
            val seconds = timeParts[2].toInt()
            return (hours * 3600) + (minutes * 60) + seconds
        }

        val startTimeInSeconds = timeToSeconds(startTime.toString())
        val endTimeInSeconds = timeToSeconds(endTime)


        val differenceInSeconds = endTimeInSeconds - startTimeInSeconds


        val differenceMinutes = differenceInSeconds / 60
        val differenceSeconds = differenceInSeconds % 60

         formattedDifference = String.format("%d min %d s", differenceMinutes, differenceSeconds)

        Log.e(TAG, "formattedTime==> : $formattedDifference")
        txtTimeTaken.text = formattedDifference
    }

    private fun handleClicks() {
        btnViewSolutionQuiz.setOnClickListener {
            isSolutionResult = true
            val intent = Intent(this,ExamQuizsActivity::class.java)
            intent.putExtra("ResultSolution",isSolutionResult)
            startActivity(intent)
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