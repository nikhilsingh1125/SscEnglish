package com.sscenglishpractice

import android.app.Dialog
import android.content.Context
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
import com.google.firebase.firestore.FirebaseFirestore
import com.sscenglishpractice.adapter.ViewPagerAdapter
import com.sscenglishpractice.model.SubmitData
import com.sscenglishquiz.model.QuestionData
import com.sscenglishquiz.model.QuestionWiseModel
import kotlinx.android.synthetic.main.activity_quiz.*
import kotlinx.android.synthetic.main.custom_dialog_layout.*
import libs.mjn.prettydialog.PrettyDialog


class QuizActivity : AppCompatActivity() {

    val TAG = "QuizActivity"
    var type = ""
    var title = ""
    lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        loader.setAnimation(R.raw.loader)
        loader.visibility = View.VISIBLE
        loader.playAnimation()

        db = FirebaseFirestore.getInstance()

        val intent = intent
        type = intent.getStringExtra("TYPE").toString()
        title = intent.getStringExtra("Title").toString()

        Log.e(TAG, "onCreate: $type", )

        if (type == "0") {
            val database = FirebaseDatabase.getInstance().getReference("SSC_CGL_2022/SYNONYMS_2022")
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions,title)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })

        }
        else if (type == "1") {
            val database = FirebaseDatabase.getInstance().getReference("SSC_CGL_2022/ANTONYMS_2022")
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
                    Log.e(TAG, "questions: $questions")

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "2") {
            val database = FirebaseDatabase.getInstance().getReference("SSC_CGL_2022/ONEWORD_2022")
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "3") {
            val database = FirebaseDatabase.getInstance().getReference("SSC_CGL_2022/Idioms_2022")
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title)
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
//                    Log.e(TAG, "onDataChange: $questions")

                    questions.forEach {
                        it.Solutions
                        Log.e(TAG, "Solutions: ${it.Solutions}")
                    }


                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title)
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title)
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title)
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title)
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title)
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title)
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "CPO_4") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CPO_2022/IDIOMS_2022")
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }


        else if (type == "CGL_2021_1"){
            try {
                val database =
                    FirebaseDatabase.getInstance().getReference("SSC_CGL_2021/SYNONYMS_2021")
                database.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        loader.visibility = View.GONE
                        val description = dataSnapshot.child("Description").getValue(String::class.java)
                        val questions = dataSnapshot.child("Questions")
                            .getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                        // Do something with the quiz object
//                    Log.e(TAG, "onDataChange: $questions")



                        val adapter = ViewPagerAdapter(
                            this@QuizActivity,
                            questions as ArrayList<QuestionData>,
                            title
                        )
                        idViewPager.adapter = adapter
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // Handle errors here
                    }
                })
            }catch (_:Exception){

            }

        }
        else if (type == "CGL_2021_2"){
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CGL_2021/ANTONYMS_2021")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions")
                        .getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    // Do something with the quiz object
                    Log.e(TAG, "onDataChange: $questions")

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions as ArrayList<QuestionData>,
                        title
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "CGL_2021_3"){
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CGL_2021/ONE_WORDS_2021")
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "CGL_2021_4"){
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CGL_2021/IDIOMS_2021")
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }


        else if (type == "CHCL_2021_1"){
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CHSL_2021/SYNONYMS_2021")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions")
                        .getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    val quiz = QuestionWiseModel(
                        "SYNONYMS_2021",
                        description,
                        questions as ArrayList<QuestionData>
                    )
                    // Do something with the quiz object
//                    Log.e(TAG, "onDataChange: $questions")

                    questions.forEach {
                        it.Solutions
                        Log.e(TAG, "Solutions: ${it.Solutions}")
                    }


                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "CHCL_2021_2"){
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CHSL_2021/ANTONYMS_2021")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions")
                        .getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    val quiz = QuestionWiseModel(
                        "ANTONYMS_2021",
                        description,
                        questions as ArrayList<QuestionData>
                    )
                    // Do something with the quiz object
                    Log.e(TAG, "onDataChange: $questions")

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "CHCL_2021_3"){
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CHSL_2021/ONE_WORDS_2021")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions")
                        .getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    val quiz = QuestionWiseModel(
                        "ONE_WORDS_2021",
                        description,
                        questions as ArrayList<QuestionData>
                    )
                    // Do something with the quiz object
                    Log.e(TAG, "onDataChange: $questions")

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "CHCL_2021_4"){
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CHSL_2021/IDIOMS_2021")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions")
                        .getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    val quiz = QuestionWiseModel(
                        "IDIOMS_2021",
                        description,
                        questions as ArrayList<QuestionData>
                    )
                    // Do something with the quiz object
                    Log.e(TAG, "onDataChange: $questions")

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }


        else if (type == "MTS_2021_1"){
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_MTS_2021/SYNONYMS_2021")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions")
                        .getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    val quiz = QuestionWiseModel(
                        "SYNONYMS_2021",
                        description,
                        questions as ArrayList<QuestionData>
                    )
                    // Do something with the quiz object
//                    Log.e(TAG, "onDataChange: $questions")

                    questions.forEach {
                        it.Solutions
                        Log.e(TAG, "Solutions: ${it.Solutions}")
                    }


                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "MTS_2021_2"){
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_MTS_2021/ANTONYMS_2021")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions")
                        .getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    val quiz = QuestionWiseModel(
                        "ANTONYMS_2021",
                        description,
                        questions as ArrayList<QuestionData>
                    )
                    // Do something with the quiz object
                    Log.e(TAG, "onDataChange: $questions")

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "MTS_2021_3"){
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_MTS_2021/ONE_WORDS_2021")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions")
                        .getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    val quiz = QuestionWiseModel(
                        "ONE_WORDS_2021",
                        description,
                        questions as ArrayList<QuestionData>
                    )
                    // Do something with the quiz object
                    Log.e(TAG, "onDataChange: $questions")

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "MTS_2021_4"){
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_MTS_2021/IDIOMS_2021")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions")
                        .getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    val quiz = QuestionWiseModel(
                        "IDIOMS_2021",
                        description,
                        questions as ArrayList<QuestionData>
                    )
                    // Do something with the quiz object
                    Log.e(TAG, "onDataChange: $questions")

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title)
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
        size: Int,
        givenAnswerCount: Int,
        skipedAnswer: Int
    ) {
        val sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("correctAnswer", correctAnswerCount.toString())
        editor.putString("incorrectAnswer", incorrectAnswerCount.toString())
        editor.putString("totalQuestion", size.toString())
        editor.putString("Title",type)
        editor.putBoolean("key3", true)
        editor.apply()

        if (title != null) {
            addDataToFireStore(title = type,correctAnswerCount,incorrectAnswerCount.toString(),size)
        }

        showDialogSubmit(givenAnswerCount, skipedAnswer, size)
    }

    private fun addDataToFireStore(
        title: String,
        correctAnswer: Int?,
        incorrectAnswer: String?,
        totalQuestion: Int?
    ) {

        val dbNotes = db.collection("Submit_Test")

        val data = SubmitData(title,correctAnswer.toString(),incorrectAnswer)


        dbNotes.add(data).addOnSuccessListener {
            db.collection("Submit_Test").document(it.id).set(SubmitData(it.id, title, correctAnswer = correctAnswer.toString(),totalQuestion.toString(),incorrectAnswer.toString()))
            println("test id"+it.id)

        }
            .addOnFailureListener {
                Toast.makeText(this, "Fail to add Data", Toast.LENGTH_SHORT).show();
            }

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
            startActivity(Intent(this, QuizSubmitActivity::class.java))
            finish()
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
            .setIconTint(R.color.black_light)
            .addButton(
                "OK",  // button text
                R.color.white,  // button text color
                R.color.black_light
            )  // button background color
            {
                pDialog.dismiss()
                finish()
            }
            .addButton(
                "Cancel",  // button text
                R.color.white,  // button text color
                R.color.black_light
            )  // button background color
            { pDialog.dismiss() }
            .show()
    }


}