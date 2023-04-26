package com.sscenglishpractice

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.sscenglishpractice.adapter.ViewPagerAdapter
import com.sscenglishquiz.model.QuestionData
import com.sscenglishquiz.model.QuestionWiseModel
import kotlinx.android.synthetic.main.activity_quiz.*
import kotlinx.android.synthetic.main.custom_dialog_layout.*
import libs.mjn.prettydialog.PrettyDialog


class QuizActivity : AppCompatActivity() {

    val TAG = "QuizActivity"
    var type = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        loader.setAnimation(R.raw.loader)
        loader.visibility = View.VISIBLE
        loader.playAnimation()


        val intent = intent
        type = intent.getStringExtra("TYPE").toString()

        Log.e(TAG, "onCreate: $type", )

        if (type == "0") {
            val database = FirebaseDatabase.getInstance().getReference("Quizes/SYNONYMS_2022")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions")
                        .getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    val quiz = QuestionWiseModel(
                        "SYNONYMS_2019",
                        description,
                        questions as ArrayList<QuestionData>
                    )
                    // Do something with the quiz object
                    Log.e(TAG, "onDataChange: $questions")

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "1") {
            val database = FirebaseDatabase.getInstance().getReference("Quizes/ANTONYMS_2022")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions")
                        .getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    val quiz = QuestionWiseModel(
                        "ANTONYMS_2019",
                        description,
                        questions as ArrayList<QuestionData>
                    )
                    // Do something with the quiz object
                    Log.e(TAG, "onDataChange: $questions")

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "2") {
            val database = FirebaseDatabase.getInstance().getReference("Quizes/ONEWORD_2022")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions")
                        .getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    val quiz = QuestionWiseModel(
                        "ONEWORD_2019",
                        description,
                        questions as ArrayList<QuestionData>
                    )
                    // Do something with the quiz object
                    Log.e(TAG, "onDataChange: $questions")

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "3") {
            val database = FirebaseDatabase.getInstance().getReference("Quizes/Idioms_2022")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions")
                        .getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    val quiz = QuestionWiseModel(
                        "Idioms_2019",
                        description,
                        questions as ArrayList<QuestionData>
                    )
                    // Do something with the quiz object
                    Log.e(TAG, "onDataChange: $questions")

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "CHCL_1") {
            val database =
                FirebaseDatabase.getInstance().getReference("Chsl_SYNONYMS_2022/SYNONYMS_2022")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions")
                        .getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    val quiz = QuestionWiseModel(
                        "SYNONYMS_2022",
                        description,
                        questions as ArrayList<QuestionData>
                    )
                    // Do something with the quiz object
                    Log.e(TAG, "onDataChange: $questions")

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "CHCL_2") {
            val database =
                FirebaseDatabase.getInstance().getReference("Chsl_SYNONYMS_2022/ANTONYMS_2022")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions")
                        .getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    val quiz = QuestionWiseModel(
                        "SYNONYMS_2022",
                        description,
                        questions as ArrayList<QuestionData>
                    )
                    // Do something with the quiz object
                    Log.e(TAG, "onDataChange: $questions")

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "CHCL_3") {
            val database =
                FirebaseDatabase.getInstance().getReference("Chsl_SYNONYMS_2022/ONE_WORDS_2022")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions")
                        .getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    val quiz = QuestionWiseModel(
                        "ONE_WORDS_2022",
                        description,
                        questions as ArrayList<QuestionData>
                    )
                    // Do something with the quiz object
                    Log.e(TAG, "onDataChange: $questions")

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "CHCL_4") {
            val database =
                FirebaseDatabase.getInstance().getReference("Chsl_SYNONYMS_2022/IDIOMS_2022")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions")
                        .getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    val quiz = QuestionWiseModel(
                        "IDIOMS_2022",
                        description,
                        questions as ArrayList<QuestionData>
                    )
                    // Do something with the quiz object
                    Log.e(TAG, "onDataChange: $questions")

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "CPO_1") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CPO_2022/SYNONYMS_2022")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions")
                        .getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    val quiz = QuestionWiseModel(
                        "SYNONYMS_2022",
                        description,
                        questions as ArrayList<QuestionData>
                    )
                    // Do something with the quiz object
                    Log.e(TAG, "onDataChange: $questions")

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "CPO_2") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CPO_2022/ANTONYMS_2022")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions")
                        .getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    val quiz = QuestionWiseModel(
                        "ANTONYMS_2022",
                        description,
                        questions as ArrayList<QuestionData>
                    )
                    // Do something with the quiz object
                    Log.e(TAG, "onDataChange: $questions")

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "CPO_3") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CPO_2022/ONEWORD_2022")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions")
                        .getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    val quiz = QuestionWiseModel(
                        "ONEWORD_2022",
                        description,
                        questions as ArrayList<QuestionData>
                    )
                    // Do something with the quiz object
                    Log.e(TAG, "onDataChange: $questions")

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions)
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


    fun clickOnSubmitNext(
        position: Int,
        correctAnswerCount: Int,
        incorrectAnswerCount: Int,
        size: Int
    ) {
        showDialogSubmit(correctAnswerCount, incorrectAnswerCount, size)
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

    override fun onBackPressed() {

        val pDialog = PrettyDialog(this)
        pDialog
            .setTitle(getString(R.string.app_name))
            .setMessage("Are you sure you don't want continue?")
            .setIconTint(R.color.purple)
            .addButton(
                "OK",  // button text
                R.color.black,  // button text color
                R.color.cream
            )  // button background color
            {
                pDialog.dismiss()
                finish()
            }
            .addButton(
                "Cancel",  // button text
                R.color.black,  // button text color
                R.color.cream
            )  // button background color
            { pDialog.dismiss() }
            .show()
    }


}