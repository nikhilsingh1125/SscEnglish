package com.sscenglishpractice

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.sscenglishpractice.adapter.ViewPagerAdapter
import com.sscenglishquiz.model.QuestionData
import com.sscenglishquiz.model.QuestionWiseModel
import kotlinx.android.synthetic.main.activity_quiz.*
import kotlinx.android.synthetic.main.custom_dialog_layout.*
import kotlinx.android.synthetic.main.custom_toolbar.*


class QuizActivity : AppCompatActivity() {

    val TAG = "QuizActivity"
    var type = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        loader.setAnimation(R.raw.loader)
        loader.visibility = View.VISIBLE
        loader.playAnimation()

        action_bar_back.visibility = View.VISIBLE
        action_bar_share.visibility = View.GONE

        action_bar_back.setOnClickListener {
            onBackPressed()
        }

        val intent = intent
        type = intent.getStringExtra("TYPE").toString()



        if (type == "0"){
            val database = FirebaseDatabase.getInstance().getReference("Quizes/SYNONYMS_2022")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions").getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    val quiz = QuestionWiseModel("SYNONYMS_2019", description, questions as ArrayList<QuestionData>)
                    // Do something with the quiz object
                    Log.e(TAG, "onDataChange: $questions", )

                    val adapter = ViewPagerAdapter(this@QuizActivity,questions)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "1"){
            val database = FirebaseDatabase.getInstance().getReference("Quizes/ANTONYMS_2022")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions").getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    val quiz = QuestionWiseModel("ANTONYMS_2019", description, questions as ArrayList<QuestionData>)
                    // Do something with the quiz object
                    Log.e(TAG, "onDataChange: $questions", )

                    val adapter = ViewPagerAdapter(this@QuizActivity,questions)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "2"){
            val database = FirebaseDatabase.getInstance().getReference("Quizes/ONEWORD_2022")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions").getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    val quiz = QuestionWiseModel("ONEWORD_2019", description, questions as ArrayList<QuestionData>)
                    // Do something with the quiz object
                    Log.e(TAG, "onDataChange: $questions", )

                    val adapter = ViewPagerAdapter(this@QuizActivity,questions)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "3"){
            val database = FirebaseDatabase.getInstance().getReference("Quizes/Idioms_2022")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions").getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    val quiz = QuestionWiseModel("Idioms_2019", description, questions as ArrayList<QuestionData>)
                    // Do something with the quiz object
                    Log.e(TAG, "onDataChange: $questions", )

                    val adapter = ViewPagerAdapter(this@QuizActivity,questions)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "CHCL_1"){
            val database = FirebaseDatabase.getInstance().getReference("Chsl_SYNONYMS_2022/SYNONYMS_2022")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions").getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    val quiz = QuestionWiseModel("SYNONYMS_2022", description, questions as ArrayList<QuestionData>)
                    // Do something with the quiz object
                    Log.e(TAG, "onDataChange: $questions", )

                    val adapter = ViewPagerAdapter(this@QuizActivity,questions)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }


    }

    fun clickOnBtnNext(position: Int) {
        idViewPager.setCurrentItem(idViewPager.currentItem + 1, true)
    }

    fun goToNextQuestion() {
        idViewPager.setCurrentItem(idViewPager.currentItem + 1, true)
    }

    fun clickOnSubmitNext(
        position: Int,
        correctAnswerCount: Int,
        incorrectAnswerCount: Int,
        size: Int
    ) {
        showDialogSubmit(correctAnswerCount, incorrectAnswerCount,size)
    }

    private fun showDialogSubmit(correctAnswerCount: Int, incorrectAnswerCount: Int, size: Int) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog_layout)

        if (correctAnswerCount != 0)
            dialog.txtCorrect.text = correctAnswerCount.toString()

        if (incorrectAnswerCount != 0)
            dialog.txtIncorrect.text = incorrectAnswerCount.toString()

        val total = correctAnswerCount + incorrectAnswerCount
        if (correctAnswerCount != 0 && incorrectAnswerCount != 0)
            dialog.txtTotal.text = total.toString()

        dialog.yesBtn.setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(this, HomeActivity::class.java))
        }
        dialog.noBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }

}