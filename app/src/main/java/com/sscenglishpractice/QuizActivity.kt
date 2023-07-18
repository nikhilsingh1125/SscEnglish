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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.sscenglishpractice.adapter.ViewPagerAdapter
import com.sscenglishpractice.model.SubmitData
import com.sscenglishpractice.viewModel.QuizViewModel
import com.sscenglishquiz.model.QuestionData
import com.sscenglishquiz.model.QuestionWiseModel
import kotlinx.android.synthetic.main.activity_quiz.ad_view_quiz
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
    var adRequest = AdRequest.Builder().build()
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

        MobileAds.initialize(this)

        ad_view_quiz.loadAd(adRequest)

        RewardedAd.load(
            this,
            "ca-app-pub-7484865336777284/3660075926",
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    adError.toString()?.let { Log.d(TAGAD, it) }
                    rewardedAd = null
                }

                override fun onAdLoaded(ad: RewardedAd) {
                    Log.d(TAGAD, "Ad was loaded.")
                    rewardedAd = ad
                }
            })

        Log.e(TAG, "onCreate: $type")

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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
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


                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category)
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

        }
        else if (type == "MTS_2") {
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
        }
        else if (type == "MTS_3") {
            val database = FirebaseDatabase.getInstance().getReference("SSC_MTS_2022/ONE_WORDS_2022")
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
        }
        else if (type == "MTS_4") {
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
        }

        else if (type == "CGL_2021_1") {
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

        }
        else if (type == "CGL_2021_2") {
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
        }
        else if (type == "CGL_2021_3") {
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
        }
        else if (type == "CGL_2021_4") {
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
        }

        else if (type == "CHCL_2021_1") {
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
        }
        else if (type == "CHCL_2021_2") {
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
        }
        else if (type == "CHCL_2021_3") {
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
        }
        else if (type == "CHCL_2021_4") {
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
        }

        else if (type == "MTS_2021_1") {
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
        }
        else if (type == "MTS_2021_2") {
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
        }
        else if (type == "MTS_2021_3") {
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
        }
        else if (type == "MTS_2021_4") {
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
        }

        else if (type == "CGL_2020_1") {
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
        }
        else if (type == "CGL_2020_2") {
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
        }
        else if (type == "CGL_2020_3") {
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
        }
        else if (type == "CGL_2020_4") {
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
        }



        else if (type == "MTS_2020_2") {
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
        }
        else if (type == "MTS_2020_3") {
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
        }
        else if (type == "MTS_2020_4") {
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
        }

        else if (type == "CHSL_2020_1") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CHSL_2020/SYNONYMS_2020")
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
        }
        else if (type == "CHSL_2020_2") {
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
        }
        else if (type == "CHSL_2020_3") {
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
        }
        else if (type == "CHSL_2020_4") {
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
        }

        else if (type == "CGL_2019_1") {
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
        }
        else if (type == "CGL_2019_2") {
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
        }
        else if (type == "CGL_2019_3") {
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
        }
        else if (type == "CGL_2019_4") {
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
        }

        else if (type == "MTS_2019_1") {
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
        }
        else if (type == "MTS_2019_2") {
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
        }
        else if (type == "MTS_2019_3") {
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
        }
        else if (type == "MTS_2019_4") {
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
        }

        else if (type == "CPO_2019_1") {
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
        }
        else if (type == "CPO_2019_2") {
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
        }
        else if (type == "CPO_2019_3") {
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
        }
        else if (type == "CPO_2019_4") {
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
        }

    }

    private fun keepObserve() {
        viewModel.questions.observe(this) { questions ->
            // Update your UI or do something with the questions
            val adapter = ViewPagerAdapter(this@QuizActivity,
                questions as ArrayList<QuestionData>, title, category)
            idViewPager.adapter = adapter
        }

    }


    fun init(){
        viewModel = ViewModelProvider(this)[QuizViewModel::class.java]
        viewModel.loadQuiz()
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
        skipedAnswer: Int,
        title: String,
        category: String
    ) {

        val sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("correctAnswer", correctAnswerCount.toString())
        editor.putString("incorrectAnswer", incorrectAnswerCount.toString())
        editor.putString("totalQuestion", size.toString())
        editor.putString("Title", type)
        editor.putString("Title_Subject", title)
        editor.putBoolean("key3", true)
        editor.putString("categoryData", category)
        editor.apply()

        if (this.title != null) {
            addDataToFireStore(
                title = type,
                correctAnswerCount,
                incorrectAnswerCount.toString(),
                size,
                title,
                category
            )
        }

        showDialogSubmit(givenAnswerCount, skipedAnswer, size)
    }

    private fun addDataToFireStore(
        title: String,
        correctAnswer: Int?,
        incorrectAnswer: String?,
        totalQuestion: Int?,
        titleSubject: String,
        category: String
    ) {

        val dbNotes = db.collection("Submit_Test")

        val data = SubmitData(title, correctAnswer.toString(), incorrectAnswer)


        dbNotes.add(data).addOnSuccessListener {
            db.collection("Submit_Test").document(it.id).set(
                SubmitData(
                    it.id,
                    title,
                    correctAnswer = correctAnswer.toString(),
                    totalQuestion.toString(),
                    incorrectAnswer.toString(),
                    titleSubject.toString(),
                    category
                )
            )
            println("test id" + it.id)

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
            rewardedAd?.let { ad ->
                ad.show(this, OnUserEarnedRewardListener { rewardItem ->
                    startActivity(Intent(this, QuizSubmitActivity::class.java))
                    finish()
                })
            } ?: run {
                startActivity(Intent(this, QuizSubmitActivity::class.java))
                finish()
            }

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

    /* override fun onPause() {
         adView.pause()
         super.onPause()
         Log.e(TAG, "onPause: onPause ")
     }

     override fun onResume() {
         super.onResume()
         adView.resume()
         Log.e(TAG, "onResume: onResume ")
     }

     override fun onDestroy() {
         adView.destroy()
         super.onDestroy()
         Log.e(TAG, "onDestroy: onDestroy ")

     }*/


}