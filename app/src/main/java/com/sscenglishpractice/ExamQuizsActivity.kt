package com.sscenglishpractice

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.sscenglishpractice.adapter.ExamAdapter
import com.sscenglishpractice.adapter.QuestionPositionAdapter
import com.sscenglishpractice.model.Question
import com.sscenglishpractice.model.QuizQuestion
import com.sscenglishpractice.model.QuizResult
import kotlinx.android.synthetic.main.activity_exam_quizs.rvQuestions
import kotlinx.android.synthetic.main.activity_exam_quizs.toolbarQuiz
import kotlinx.android.synthetic.main.activity_exam_quizs.vpViewPager
import kotlinx.android.synthetic.main.activity_quiz.loader
import kotlinx.android.synthetic.main.custom_toolbar.action_bar_Title
import kotlinx.android.synthetic.main.custom_toolbar.btnSubmit
import libs.mjn.prettydialog.PrettyDialog

class ExamQuizsActivity : AppCompatActivity() {


    var categoryTest = ""
    val TAG = "ExamQuizsActivity"
    lateinit var rvAdapter: QuestionPositionAdapter
    lateinit var questions: ArrayList<Question>
    lateinit var quizModel: QuizQuestion
    private var quizDurationInMillis: Long = 12 * 60 * 1000
    private var isQuizRunning: Boolean = false
    private var isResult: Boolean = false
    var time = ""
    var timeTaken = ""
    var pos = 0
    var totalTimeInSeconds = 0

    var correctAns = 0
    var wrongAns = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam_quizs)

        initUi()
        handleClicks()


    }

    private fun handleClicks() {

        btnSubmit.setOnClickListener {

            if (questions[pos].isGivenAnswer) {
                if (questions[pos].userSelectAnswer == questions[pos].answer) {
                    correctAns++
                } else {
                    wrongAns++
                }
            }

            val givenAnswer = questions.filter { it.isGivenAnswer}
            val correctAnswer = givenAnswer.filter { it.userSelectAnswer == it.answer }
            val wrongAnswer = givenAnswer.filter { it.userSelectAnswer != it.answer }

            val count = correctAnswer.size
            Log.d("Answers", "givenAnswer ==>${givenAnswer.size}")
            Log.d("Answers", "correctAns ==>${correctAnswer.size}")
            Log.d("Answers", "wrongAnswer ==>${wrongAnswer.size}")
            Log.d("Answers", "questions ==>${questions.size}")

            loader.visibility = View.VISIBLE
            loader.playAnimation()
            val quizResult = QuizResult(questions)
            val databaseReference = FirebaseDatabase.getInstance().getReference("QuizResults")
            databaseReference.push().setValue(quizResult)
                .addOnSuccessListener {
                    loader.visibility = View.GONE
                    val intent = Intent(this, QuizResultActivity::class.java)
                    intent.putExtra("CorrectAnswer",correctAnswer.size)
                    intent.putExtra("WrongAnswer",wrongAnswer.size)
                    intent.putExtra("TotalQuestions",questions.size)
                    intent.putExtra("Time",time)
                    intent.putExtra("TotalTime",timeTaken)
                    startActivity(intent)
                    finish()
                    Log.d(TAG, "Quiz result saved successfully!")
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "Failed to save quiz result: ${e.message}", e)
                }

        }
    }

    private fun initUi() {

        val intentData = intent
        isResult = intentData.getBooleanExtra("ResultSolution", false)
        Log.e(TAG, "isResult: $isResult")

        if (!isResult){
            val receivedBundle = intent.extras
            if (receivedBundle != null) {
                questions = receivedBundle.getParcelableArrayList<Question>("questionsList") as ArrayList<Question>
                val receivedTestTitle = receivedBundle.getString("testTitle")
                timeTaken= receivedBundle.getString("timeTaken").toString()
                if (questions != null) {
                    // Use the receivedQuestions list in your activity as needed
                    Log.e(TAG, "questions ==>: $questions" )
                }
            }
        }


        if (isResult) {
            loader.setAnimation(R.raw.loader)
            loader.visibility = View.VISIBLE
            loader.playAnimation()
            getResultData()
        }
        else {

            if (!isQuizRunning) {
                startQuizTimer(timeTaken)
            }

            loader.visibility = View.GONE
            val adapter = ExamAdapter(this@ExamQuizsActivity, questions, isResult)
            vpViewPager.adapter = adapter


            rvAdapter = QuestionPositionAdapter(this@ExamQuizsActivity, questions)
            val layoutManager = LinearLayoutManager(this@ExamQuizsActivity, RecyclerView.HORIZONTAL, false)
            rvQuestions.layoutManager = layoutManager
            rvQuestions.adapter = rvAdapter

        }


        vpViewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                rvQuestions.scrollToPosition(position)
                val model = questions[position]
                Log.e(TAG, "onPageSelected: $model")
                questions[position].isVisited = true

                pos = position
                val newPosition = vpViewPager.currentItem
                rvAdapter.setSelectedItem(newPosition)

            }
        })

        if (time == "00:00:00"){
            timeEndDialog()
        }
    }


    private fun getResultData() {

        val databaseReference = FirebaseDatabase.getInstance().getReference("QuizResults")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val quizResults = mutableListOf<QuizQuestion>()

                loader.visibility = View.GONE
                toolbarQuiz.visibility = View.VISIBLE
                action_bar_Title.text ="Solutions"
                btnSubmit.text ="Back"


                for (childSnapshot in dataSnapshot.children) {

                     questions = childSnapshot.child("questions")
                         .getValue(object : GenericTypeIndicator<List<Question>>() {}) as ArrayList<Question>

                 /*   val quizResult = questions?.let { QuizQuestion() }
                    if (quizResult != null) {
                        quizResults.add(quizResult)
                        Log.d("SelectedUI", "QuizResults ===>: $questions")
                    }*/

                    Log.d("SelectedUI", "QuizResults ===>: $questions")
                    val adapter = ExamAdapter(
                        this@ExamQuizsActivity,
                        questions as ArrayList<Question>, isResult
                    )
                    vpViewPager.adapter = adapter


                    rvAdapter = QuestionPositionAdapter(this@ExamQuizsActivity, questions)
                    val layoutManager = LinearLayoutManager(
                        this@ExamQuizsActivity,
                        RecyclerView.HORIZONTAL, false
                    )
                    rvQuestions.layoutManager = layoutManager
                    rvQuestions.adapter = rvAdapter
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })

    }

    fun onImageClick(position: Int) {
        vpViewPager.currentItem = position
        rvAdapter.notifyItemChanged(position)
    }

    fun onPrevClick(position: Int) {
        vpViewPager.setCurrentItem(vpViewPager.currentItem - 1, true)
        val newPosition = vpViewPager.currentItem
        rvAdapter.setSelectedItem(newPosition)
    }

    fun onNextClick(position: Int) {
        vpViewPager.setCurrentItem(vpViewPager.currentItem + 1, true)
        val newPosition = vpViewPager.currentItem
        rvAdapter.setSelectedItem(newPosition)
    }


    private fun startQuizTimer(timeTakenAll: String) {

        // Convert the dynamic time to total seconds
        val timeParts = timeTakenAll.split(":")
        val hours = timeParts[0].toInt()
        val minutes = timeParts[1].toInt()
        val seconds = timeParts[2].toInt()
        totalTimeInSeconds = (hours * 3600) + (minutes * 60) + seconds
        object : CountDownTimer(quizDurationInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val timeHours = (millisUntilFinished / (1000 * 60 * 60)) % 24
                val timeMinutes = (millisUntilFinished / (1000 * 60)) % 60
                val timeSeconds = (millisUntilFinished / 1000) % 60

                val timerText = String.format("%02d:%02d:%02d", timeHours, timeMinutes, timeSeconds)
                action_bar_Title.text = timerText
                time = timerText
            }

            override fun onFinish() {
                action_bar_Title.text = "00:00:00"
                isQuizRunning = false
            }
        }.start()

        isQuizRunning = true
    }

    fun timeEndDialog(){
        val pDialog = PrettyDialog(this)
        pDialog
            .setTitle(getString(R.string.app_name))
            .setMessage("Your times up! please submit your test")
            .setIconTint(R.color.purple)
            .addButton(
                "OK",  // button text
                R.color.white,  // button text color
                R.color.purple
            )  // button background color
            {
                pDialog.dismiss()
                val intent = Intent(this,QuizResultActivity::class.java)
                startActivity(intent)
                finish()
            }
            .addButton(
                "Cancel",  // button text
                R.color.white,  // button text color
                R.color.purple
            )  // button background color
            { pDialog.dismiss() }
            .show()
    }

}