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
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.sscenglishpractice.adapter.ViewPagerAdapter
import com.sscenglishpractice.model.QuizResult
import com.sscenglishpractice.model.QuizResultPractice
import com.sscenglishpractice.model.SubmitData
import com.sscenglishpractice.viewModel.QuizViewModel
import com.sscenglishquiz.model.QuestionData
import com.sscenglishquiz.model.QuestionWiseModel
import kotlinx.android.synthetic.main.activity_quiz.idViewPager
import kotlinx.android.synthetic.main.activity_quiz.loader
import kotlinx.android.synthetic.main.custom_dialog_layout.noBtn
import kotlinx.android.synthetic.main.custom_dialog_layout.txtCorrect
import kotlinx.android.synthetic.main.custom_dialog_layout.txtIncorrect
import kotlinx.android.synthetic.main.custom_dialog_layout.txtTotal
import kotlinx.android.synthetic.main.custom_dialog_layout.yesBtn
import libs.mjn.prettydialog.PrettyDialog


class QuizActivity : AppCompatActivity() {

    val TAG = "QuizActivity"
    val TAGAD = "QuizActivityADs"
    var type = ""
    var title = ""
    var category = ""
    lateinit var db: FirebaseFirestore
    var rewardedAd: RewardedAd? = null
    private lateinit var viewModel: QuizViewModel
//    lateinit var adView: AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        loader.setAnimation(R.raw.loader)
        loader.visibility = View.VISIBLE
        loader.playAnimation()

        Log.e(TAG, "onCreate: onCreate ")

        db = FirebaseFirestore.getInstance()

        val intent = intent
        type = intent.getStringExtra("TYPE").toString()
        title = intent.getStringExtra("Title").toString()
        category = intent.getStringExtra("Category").toString()



        Log.e(TAG, "onCreate: $type")
        Log.e("QuizResultsPractice", "category: $category")

        init()
        keepObserve()

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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })

        } else if (type == "1") {
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "2") {
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "3") {
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CHCL_1") {
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


                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CHCL_2") {
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CHCL_3") {
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CHCL_4") {
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CPO_1") {
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CPO_2") {
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CPO_3") {
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CPO_4") {
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }

        if (type == "MTS_1") {
            val database = FirebaseDatabase.getInstance().getReference("SSC_MTS_2022/SYNONYMS_2022")
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })

        } else if (type == "MTS_2") {
            val database = FirebaseDatabase.getInstance().getReference("SSC_MTS_2022/ANTONYMS_2022")
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "MTS_3") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_MTS_2022/ONE_WORDS_2022")
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "MTS_4") {
            val database = FirebaseDatabase.getInstance().getReference("SSC_MTS_2022/IDIOMS_2022")
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "Stenographer_1") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_Stenographer_2022/SYNONYMS_2022")
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "Stenographer_2") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_Stenographer_2022/ANTONYMS_2022")
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "Stenographer_3") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_Stenographer_2022/IDIOMS_2022")
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CGL_2021_1") {
            try {
                val database =
                    FirebaseDatabase.getInstance().getReference("SSC_CGL_2021/SYNONYMS_2021")
                database.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        loader.visibility = View.GONE
                        val description =
                            dataSnapshot.child("Description").getValue(String::class.java)
                        val questions = dataSnapshot.child("Questions")
                            .getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                        // Do something with the quiz object
//                    Log.e(TAG, "onDataChange: $questions")


                        val adapter = ViewPagerAdapter(
                            this@QuizActivity,
                            questions as ArrayList<QuestionData>,
                            title,
                            category
                        )
                        idViewPager.adapter = adapter
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // Handle errors here
                    }
                })
            } catch (_: Exception) {

            }

        } else if (type == "CGL_2021_2") {
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
                        title,
                        category
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CGL_2021_3") {
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CGL_2021_4") {
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CHCL_2021_1") {
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


                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CHCL_2021_2") {
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CHCL_2021_3") {
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CHCL_2021_4") {
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "MTS_2021_1") {
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


                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "MTS_2021_2") {
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "MTS_2021_3") {
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "MTS_2021_4") {
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CGL_2020_1") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CGL_2020/SYNONYMS_2020")
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


                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CGL_2020_2") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CGL_2020/ANTONYMS_2020")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions")
                        .getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    val quiz = QuestionWiseModel(
                        "ANTONYMS_2020",
                        description,
                        questions as ArrayList<QuestionData>
                    )
                    // Do something with the quiz object
//                    Log.e(TAG, "onDataChange: $questions")

                    questions.forEach {
                        it.Solutions
                        Log.e(TAG, "Solutions: ${it.Solutions}")
                    }


                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CGL_2020_3") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CGL_2020/ONEWORD_2020")
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


                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CGL_2020_4") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CGL_2020/IDIOMS_2020")
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


                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "MTS_2020_2") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_MTS_2020/ANTONYMS_2020")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions")
                        .getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    val quiz = QuestionWiseModel(
                        "ANTONYMS_2020",
                        description,
                        questions as ArrayList<QuestionData>
                    )
                    // Do something with the quiz object
//                    Log.e(TAG, "onDataChange: $questions")

                    questions.forEach {
                        it.Solutions
                        Log.e(TAG, "Solutions: ${it.Solutions}")
                    }


                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "MTS_2020_3") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_MTS_2020/ONEWORD_2020")
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


                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "MTS_2020_4") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_MTS_2020/IDIOMS_2020")
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


                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CHSL_2020_1") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CHSL_2020/SYNONYMS_2020")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions")
                        .getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    val quiz = QuestionWiseModel(
                        "SYNONYMS_2021",
                        description,
                        questions as ArrayList<QuestionData>
                    )

                    loader.visibility = View.GONE
                    // Do something with the quiz object
//                    Log.e(TAG, "onDataChange: $questions")

                    questions.forEach {
                        it.Solutions
                        Log.e(TAG, "Solutions: ${it.Solutions}")
                    }


                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CHSL_2020_2") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CHSL_2020/ANTONYMS_2020")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions")
                        .getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    val quiz = QuestionWiseModel(
                        "ANTONYMS_2020",
                        description,
                        questions as ArrayList<QuestionData>
                    )
                    // Do something with the quiz object
//                    Log.e(TAG, "onDataChange: $questions")

                    questions.forEach {
                        it.Solutions
                        Log.e(TAG, "Solutions: ${it.Solutions}")
                    }


                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CHSL_2020_3") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CHSL_2020/ONEWORD_2020")
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


                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CHSL_2020_4") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CHSL_2020/Idioms_2020")
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


                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CPO_2020_1") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CPO_2020/SYNONYMS_2020")
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


                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CPO_2020_2") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CPO_2020/ANTONYMS_2020")
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


                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CPO_2020_3") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CPO_2020/ONEWORD_2020")
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


                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CPO_2020_4") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CPO_2020/Idioms_2020")
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


                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CGL_2019_1") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CGL_2019/SYNONYMS_2019")
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CGL_2019_2") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CGL_2019/ANTONYMS_2019")
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CGL_2019_3") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CGL_2019/ONEWORD_2019")
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CGL_2019_4") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CGL_2019/Idioms_2019")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions")
                        .getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    val quiz = QuestionWiseModel(
                        "IDIOMS_2019",
                        description,
                        questions as ArrayList<QuestionData>
                    )
                    // Do something with the quiz object
                    Log.e(TAG, "onDataChange: $questions")

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "MTS_2019_1") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_MTS_2019/SYNONYMS_2019")
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "MTS_2019_2") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_MTS_2019/ANTONYMS_2019")
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "MTS_2019_3") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_MTS_2019/ONE_WORDS_2019")
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "MTS_2019_4") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_MTS_2019/IDIOMS_2019")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions")
                        .getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    val quiz = QuestionWiseModel(
                        "IDIOMS_2019",
                        description,
                        questions as ArrayList<QuestionData>
                    )
                    // Do something with the quiz object
                    Log.e(TAG, "onDataChange: $questions")

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CPO_2019_1") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CPO_2019/SYNONYMS_2019")
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CPO_2019_2") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CPO_2019/ANTONYMS_2019")
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CPO_2019_3") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CPO_2019/ONE_WORDS_2019")
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CPO_2019_4") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CPO_2019/IDIOMS_2019")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions")
                        .getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    val quiz = QuestionWiseModel(
                        "IDIOMS_2019",
                        description,
                        questions as ArrayList<QuestionData>
                    )
                    // Do something with the quiz object
                    Log.e(TAG, "onDataChange: $questions")

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CHSL_2019_1") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CHSL_2019/SYNONYMS_2019")
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CHSL_2019_2") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CHSL_2019/ANTONYMS_2019")
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CHSL_2019_3") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CHSL_2019/ONE_WORDS_2019")
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CHSL_2019_4") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CHSL_2019/IDIOMS_2019")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions")
                        .getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    val quiz = QuestionWiseModel(
                        "IDIOMS_2019",
                        description,
                        questions as ArrayList<QuestionData>
                    )
                    // Do something with the quiz object
                    Log.e(TAG, "onDataChange: $questions")

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CGL_2023_1") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CGL_2023/SYNONYMS_2023")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions")
                        .getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    val quiz = QuestionWiseModel(
                        "IDIOMS_2019",
                        description,
                        questions as ArrayList<QuestionData>
                    )
                    // Do something with the quiz object
                    Log.e(TAG, "onDataChange: $questions")

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CGL_2023_2") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CGL_2023/ANTONYMS_2023")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions")
                        .getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    val quiz = QuestionWiseModel(
                        "IDIOMS_2019",
                        description,
                        questions as ArrayList<QuestionData>
                    )
                    // Do something with the quiz object
                    Log.e(TAG, "onDataChange: $questions")

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CGL_2023_3") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CGL_2023/ONE_WORDS_2023")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions")
                        .getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    val quiz = QuestionWiseModel(
                        "IDIOMS_2019",
                        description,
                        questions as ArrayList<QuestionData>
                    )
                    // Do something with the quiz object
                    Log.e(TAG, "onDataChange: $questions")

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "CGL_2023_4") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CGL_2023/IDIOMS_2023")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions")
                        .getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    val quiz = QuestionWiseModel(
                        "IDIOMS_2019",
                        description,
                        questions as ArrayList<QuestionData>
                    )
                    // Do something with the quiz object
                    Log.e(TAG, "onDataChange: $questions")

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "PHASE_2023_1") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_PHASE_2023/SYNONYMS_2023")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions")
                        .getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    val quiz = QuestionWiseModel(
                        "IDIOMS_2019",
                        description,
                        questions as ArrayList<QuestionData>
                    )
                    // Do something with the quiz object
                    Log.e(TAG, "onDataChange: $questions")

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "PHASE_2023_2") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_PHASE_2023/ANTONYMS_2023")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions")
                        .getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    val quiz = QuestionWiseModel(
                        "IDIOMS_2019",
                        description,
                        questions as ArrayList<QuestionData>
                    )
                    // Do something with the quiz object
                    Log.e(TAG, "onDataChange: $questions")

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "PHASE_2023_3") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_PHASE_2023/ONE_WORDS_2023")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions")
                        .getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    val quiz = QuestionWiseModel(
                        "IDIOMS_2019",
                        description,
                        questions as ArrayList<QuestionData>
                    )
                    // Do something with the quiz object
                    Log.e(TAG, "onDataChange: $questions")

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        } else if (type == "PHASE_2023_4") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_PHASE_2023/IDIOMS_2023")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions")
                        .getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    val quiz = QuestionWiseModel(
                        "IDIOMS_2019",
                        description,
                        questions as ArrayList<QuestionData>
                    )
                    // Do something with the quiz object
                    Log.e(TAG, "onDataChange: $questions")

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
    }

    private fun keepObserve() {
        viewModel.questions.observe(this) { questions ->
            // Update your UI or do something with the questions
            val adapter = ViewPagerAdapter(
                this@QuizActivity,
                questions as ArrayList<QuestionData>, title, category
            )
            idViewPager.adapter = adapter
        }

    }


    fun init() {
        viewModel = ViewModelProvider(this)[QuizViewModel::class.java]
        viewModel.loadQuiz()
    }

    fun clickOnBtnNext(position: Int) {
        idViewPager.setCurrentItem(idViewPager.currentItem + 1, true)
    }


    fun clickOnSubmitNext(
        title: String,
        category: String,
        arrayList: ArrayList<QuestionData>
    ) {

        loader.setAnimation(R.raw.loader)
        loader.visibility = View.VISIBLE
        loader.playAnimation()
        showDialogSubmit(arrayList,title,category,type)
    }



    private fun showDialogSubmit(
        arrayList: ArrayList<QuestionData>,
        title: String,
        category: String,
        type: String
    ) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog_layout)

        val givenAnswer = arrayList.filter { it.isGivenAnswer }
        val correctAnswer = givenAnswer.filter { it.selectedOptionsAnswer == it.answer }
        val wrongAnswer = givenAnswer.filter { it.selectedOptionsAnswer != it.answer }
// Extract question_count values from answered questions
        val answeredQuestionIds = givenAnswer.map { it.question_count }.toSet()

// Find skipped questions
        val skippedQuestions = arrayList.filter { it.question_count !in answeredQuestionIds }
        Log.d("Answers", "givenAnswer ==>${givenAnswer.size}")
        Log.d("Answers", "correctAns ==>${correctAnswer.size}")
        Log.d("Answers", "wrongAnswer ==>${wrongAnswer.size}")
        Log.d("QuizResultsPractice", "category ==>$category")
        Log.d("QuizResultsPractice", "type ==>$type")

        dialog.txtCorrect.text = givenAnswer.size.toString()

        dialog.txtIncorrect.text = skippedQuestions.size.toString()

        dialog.txtTotal.text = arrayList.size.toString()

        dialog.yesBtn.setOnClickListener {
            dialog.dismiss()

            val quizResult = QuizResultPractice(givenAnswer)
            val categoryName = type
            val databaseReference = FirebaseDatabase.getInstance().getReference("QuizResultsPractice/$categoryName")

            databaseReference.setValue(quizResult)
                .addOnSuccessListener {
                    loader.visibility = View.GONE
                    val intent = Intent(this, QuizSubmitActivity::class.java)
                    intent.putExtra("correctAnswer", correctAnswer.size)
                    intent.putExtra("incorrectAnswer", wrongAnswer.size)
                    intent.putExtra("totalQuestion", arrayList.size)
                    intent.putExtra("Type", type)
                    intent.putExtra("categoryData", category)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "Failed to save quiz result: ${e.message}", e)
                }


            //    bookmarkData(arrayList)

        }

        dialog.noBtn.setOnClickListener {
            loader.visibility = View.GONE
            dialog.dismiss()
        }
        dialog.show()

    }

    private fun bookmarkData(arrayList: ArrayList<QuestionData>) {
        val resultDataList = ArrayList<QuestionData>()
        var serialNumber = 1
// Assume you have a list of questions called 'arrayList'
        for ((index, model) in arrayList.withIndex()) {
            if (model.isBookmark) {

                val resultShow = QuestionData()
                resultShow.question_count = serialNumber.toString()
                resultShow.question = model.question.toString()
                resultShow.answer = model.answer.toString()
                resultShow.option_A = model.option_A.toString()
                resultShow.option_B = model.option_B.toString()
                resultShow.option_C = model.option_C.toString()
                resultShow.option_D = model.option_D.toString()
                resultShow.Solutions = model.Solutions.toString()
                resultShow.isGivenAnswer = true
                resultShow.testCategory = category
                resultShow.isBookmark = model.isBookmark

                resultDataList.add(resultShow)
                serialNumber++
            }


        }

        val firestore = FirebaseFirestore.getInstance()
        val collectionRef = firestore.collection("category")

// Assuming you have a list of ResultShowData objects called 'resultDataList'
        for (question in resultDataList) {
            val questionId = question.question_count ?: ""
            val category = "Your_Category_Name" // Replace with the actual category name
            val bookDataDocumentId =
                "Your_BookData_Document_ID" // Replace with the desired document ID

            // Create a reference to the specific "category" document and its "bookData" subcollection document
            val categoryDocumentRef = collectionRef.document(category)
            val bookDataDocumentRef =
                categoryDocumentRef.collection("bookData").document(bookDataDocumentId)

            bookDataDocumentRef.set(question)
                .addOnSuccessListener {
                    // Data saved successfully for the current question
                    Log.d("SelectedUI", "Data saved successfully for question: $questionId")
                }
                .addOnFailureListener { e ->
                    // Handle any errors
                    Log.e(
                        "SelectedUI",
                        "Error saving data for question: $questionId - ${e.message}",
                        e
                    )
                }
        }

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

    fun onPrevClick(position: Int) {
        idViewPager.setCurrentItem(idViewPager.currentItem - 1, true)
    }


}