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
import com.google.firebase.firestore.FirebaseFirestore
import com.sscenglishpractice.adapter.ExamAdapter
import com.sscenglishpractice.adapter.QuestionPositionAdapter
import com.sscenglishpractice.model.QuizQuestion
import com.sscenglishpractice.model.QuizResult
import com.sscenglishpractice.viewModel.QuizViewModel
import kotlinx.android.synthetic.main.activity_exam_quizs.rvQuestions
import kotlinx.android.synthetic.main.activity_exam_quizs.toolbarQuiz
import kotlinx.android.synthetic.main.activity_exam_quizs.vpViewPager
import kotlinx.android.synthetic.main.activity_quiz.loader
import kotlinx.android.synthetic.main.custom_toolbar.action_bar_Title
import kotlinx.android.synthetic.main.custom_toolbar.btnSubmit
import kotlinx.android.synthetic.main.custom_toolbar.toolbar
import libs.mjn.prettydialog.PrettyDialog

class ExamQuizsActivity : AppCompatActivity() {


    var categoryTest = ""
    val TAG = "ExamQuizsActivity"
    lateinit var rvAdapter: QuestionPositionAdapter
    lateinit var questions: ArrayList<QuizQuestion>
    lateinit var quizModel: QuizQuestion
    private var quizDurationInMillis: Long = 12 * 60 * 1000
    private var isQuizRunning: Boolean = false
    private var isResult: Boolean = false
    var time = ""
    var pos = 0
    var totalTimeInSeconds = 0

    var correctAns = 0
    var wrongAns = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam_quizs)

        initUi()
        handleClicks()
        loader.setAnimation(R.raw.loader)
        loader.visibility = View.VISIBLE
        loader.playAnimation()


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
                    startActivity(intent)
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

        if (isResult) {
            getResultData()
        }
        else {
            val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("SSC_QUIZS_ALL/Quizes")

            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    toolbarQuiz.visibility = View.VISIBLE
                    if (!isQuizRunning) {
                        startQuizTimer()
                    }

                    // Fetch the list of QuizQuestion objects
                    val quizQuestionList = dataSnapshot.child("Questions").getValue(object : GenericTypeIndicator<List<QuizQuestion>>() {})
                    if (quizQuestionList != null) {
                        questions = ArrayList(quizQuestionList)
                    } else {
                        // Handle the case when there are no questions in the database
                        questions = ArrayList()
                    }

                    Log.e(TAG, "onDataChange: $questions")
                    Log.d("SelectedUI", "resultDataList ===>: $questions")

                    val adapter = ExamAdapter(this@ExamQuizsActivity, questions, isResult)
                    vpViewPager.adapter = adapter

                    questions[0].isVisited = true

                    rvAdapter = QuestionPositionAdapter(this@ExamQuizsActivity, questions)
                    val layoutManager = LinearLayoutManager(this@ExamQuizsActivity, RecyclerView.HORIZONTAL, false)
                    rvQuestions.layoutManager = layoutManager
                    rvQuestions.adapter = rvAdapter

                    // Process the fetched data here, such as displaying it in the UI
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // This method will be called when there is an error in fetching data
                    // Handle the error here
                }
            })

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

    private fun getData() {
        val database = FirebaseDatabase.getInstance().getReference("SSC_QUIZS_ALL/Quizes")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                loader.visibility = View.GONE
                val description = dataSnapshot.child("Description").getValue(String::class.java)
                val questions = dataSnapshot.child("Questions")
                    .getValue(object : GenericTypeIndicator<List<QuizQuestion>>() {})


                /* val description = dataSnapshot.child("Description").getValue(String::class.java)
                 val questions = dataSnapshot.child("Questions")
                     .getValue(object : GenericTypeIndicator<List<QuizQuestion>>() {})*/

                // Do something with the quiz object
                // Log.e(TAG, "quizQuestions: $quizQuestions")

                /*


                                */
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors here
            }
        })
    }

    private fun getResultData() {

        val databaseReference = FirebaseDatabase.getInstance().getReference("QuizResults")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val quizResults = mutableListOf<QuizQuestion>()

                loader.visibility = View.GONE
                toolbarQuiz.visibility = View.VISIBLE
                action_bar_Title.text ="Your Result"
                btnSubmit.text ="Back"


                for (childSnapshot in dataSnapshot.children) {

                     questions = childSnapshot.child("questions")
                         .getValue(object : GenericTypeIndicator<List<QuizQuestion>>() {}) as ArrayList<QuizQuestion>

                 /*   val quizResult = questions?.let { QuizQuestion() }
                    if (quizResult != null) {
                        quizResults.add(quizResult)
                        Log.d("SelectedUI", "QuizResults ===>: $questions")
                    }*/

                    Log.d("SelectedUI", "QuizResults ===>: $questions")
                    val adapter = ExamAdapter(
                        this@ExamQuizsActivity,
                        questions as ArrayList<QuizQuestion>, isResult
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


    private fun startQuizTimer() {
        object : CountDownTimer(quizDurationInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val hours = (millisUntilFinished / (1000 * 60 * 60)) % 24
                val minutes = (millisUntilFinished / (1000 * 60)) % 60
                val seconds = (millisUntilFinished / 1000) % 60
                totalTimeInSeconds = ((hours * 3600) + (minutes * 60) + seconds).toInt()
                Log.e("startQuizTimer", "totalTimeInSeconds: $totalTimeInSeconds", )
                val timerText = String.format("%02d:%02d:%02d", hours, minutes, seconds)
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