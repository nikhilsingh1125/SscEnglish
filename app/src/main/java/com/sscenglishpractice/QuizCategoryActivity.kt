package com.sscenglishpractice

import HomeQuizAdapter
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sscenglishpractice.model.QuizCategoryModel
import kotlinx.android.synthetic.main.activity_quiz.loader
import kotlinx.android.synthetic.main.activity_quiz_category.recyclerViewQuiz
import kotlinx.android.synthetic.main.custom_toolbar.action_bar_Title
import kotlinx.android.synthetic.main.custom_toolbar.action_bar_back
import kotlinx.android.synthetic.main.custom_toolbar.action_bar_share
import kotlinx.android.synthetic.main.custom_toolbar.btnSubmit

class QuizCategoryActivity : AppCompatActivity() {

    val TAG = "QuizCategoryActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_category)


        loader.setAnimation(R.raw.loader)
        loader.visibility = View.VISIBLE
        loader.playAnimation()

        btnSubmit.visibility = View.GONE
        action_bar_Title.text = "Quizzes"

        action_bar_back.visibility = View.VISIBLE
        action_bar_share.visibility = View.GONE

        action_bar_back.setOnClickListener {
            onBackPressed()
        }


        val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("SSC_QUIZS_ALL")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val quizList: MutableList<QuizCategoryModel> = mutableListOf()

                loader.visibility = View.GONE

                for (quizSnapshot in snapshot.children) {
                    val quiz = quizSnapshot.getValue(QuizCategoryModel::class.java)
                    quiz?.let { quizList.add(it) }
                    Log.e(TAG, "onDataChange: $quiz", )

                    val adapter = HomeQuizAdapter(this@QuizCategoryActivity,quizList)
                    val layoutManager = LinearLayoutManager(this@QuizCategoryActivity)
                    recyclerViewQuiz.layoutManager = layoutManager
                    recyclerViewQuiz.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error
            }
        })
    }
}