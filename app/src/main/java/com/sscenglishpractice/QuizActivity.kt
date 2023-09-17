package com.sscenglishpractice

import android.R.attr.name
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
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
import com.sscenglishpractice.utils.Constants
import com.sscenglishpractice.viewModel.QuizViewModel
import com.sscenglishquiz.model.QuestionData
import com.sscenglishquiz.model.QuestionWiseModel
import kotlinx.android.synthetic.main.activity_home_category.adView
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
    private lateinit var viewModel: QuizViewModel
    private var mInterstitialAd: InterstitialAd? = null
    lateinit var adRequest : AdRequest
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

        Constants.saveCategoryToSharedPreferences(this@QuizActivity,category)

        Log.e(TAG, "onCreate: $type")
        Log.e("QuizResultsPractice", "category: $category")
        val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        val myEdit = sharedPreferences.edit()
        myEdit.putString("Category", category)
        myEdit.apply()

        adRequest = AdRequest.Builder().build()

        MobileAds.initialize(this)
        ad_view_quiz.loadAd(adRequest)



        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                adError.toString().let { Log.d(TAG, it) }
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "Ad was loaded.")
                mInterstitialAd = interstitialAd
            }
        })

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

                    val adapter = ViewPagerAdapter(this@QuizActivity, questions, title, category,type)
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "4") {
            val database = FirebaseDatabase.getInstance().getReference("SSC_CGL_2022/SPELLING_ERROR_2022")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions")
                        .getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    val quiz = QuestionWiseModel(
                        "SPELLING_ERROR_2022",
                        description,
                        questions as ArrayList<QuestionData>
                    )
                    // Do something with the quiz object
                    Log.e(TAG, "onDataChange: $questions")

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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


                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "CHCL_5") {
            val database =
                FirebaseDatabase.getInstance().getReference("Chsl_SYNONYMS_2022/SPELLING_ERROR_2022")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    loader.visibility = View.GONE
                    val description = dataSnapshot.child("Description").getValue(String::class.java)
                    val questions = dataSnapshot.child("Questions")
                        .getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                    val quiz = QuestionWiseModel(
                        "SPELLING_ERROR_2022",
                        description,
                        questions as ArrayList<QuestionData>
                    )
                    // Do something with the quiz object
                    Log.e(TAG, "onDataChange: $questions")

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "CPO_5") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CPO_2022/SPELLING_ERROR_2022")
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "MTS_3") {
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "MTS_5") {
            val database = FirebaseDatabase.getInstance().getReference("SSC_MTS_2022/SPELLING_ERROR_2022")
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }

        else if (type == "Stenographer_1") {
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "Stenographer_2") {
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "Stenographer_3") {
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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
                            category,
                            type
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
                        category,
                        type
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "CGL_2021_5") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CGL_2021/SPELLING_ERROR_2021")
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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


                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "CHCL_2021_5") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CHSL_2021/SPELLING_ERROR_2021")
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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


                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "MTS_2021_4") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_MTS_2021/SPELLING_ERROR_2021")
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "MTS_2021_5") {
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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


                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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


                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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


                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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


                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "CGL_2020_5") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CGL_2020/SPELLING_ERROR_2020")
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


                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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


                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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


                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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


                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "MTS_2020_5") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_MTS_2020/SPELLING_ERROR_2020")
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


                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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


                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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


                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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


                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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


                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "CHSL_2020_5") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CHSL_2020/SPELLING_ERROR_2020")
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


                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }



        else if (type == "CPO_2020_1") {
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


                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "CPO_2020_2") {
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


                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "CPO_2020_3") {
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


                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "CPO_2020_4") {
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


                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "CPO_2020_5") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CPO_2020/SPELLING_ERROR_2020")
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


                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "CGL_2019_5") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CGL_2019/SPELLING_ERROR_2019")
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "MTS_2019_5") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_MTS_2019/SPELLING_ERROR_2019")
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "CPO_2019_5") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CPO_2019/SPELLING_ERROR_2019")
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }


        else if (type == "CHSL_2019_1") {
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "CHSL_2019_2") {
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "CHSL_2019_3") {
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "CHSL_2019_4") {
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "CHSL_2019_5") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CHSL_2019/SPELLING_ERROR_2019")
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }

        else if (type == "CGL_2023_1") {
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "CGL_2023_2") {
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "CGL_2023_3") {
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "CGL_2023_4") {
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "CGL_2023_5") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CGL_2023/SPELLING_ERROR_2023")
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }

        else if (type == "PHASE_2023_1") {
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "PHASE_2023_2") {
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "PHASE_2023_3") {
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "PHASE_2023_4") {
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }



                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "PHASE_2023_5") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_PHASE_2023/SPELLING_ERROR_2023")
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }



                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }

        else if (type == "CHSL_2023_1") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CHSL_2023/SYNONYMS_2023")
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "CHSL_2023_2") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CHSL_2023/ANTONYMS_2023")
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "CHSL_2023_3") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CHSL_2023/ONE_WORDS_2023")
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "CHSL_2023_4") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CHSL_2023/IDIOMS_2023")
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
                    idViewPager.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
        else if (type == "CHSL_2023_5") {
            val database =
                FirebaseDatabase.getInstance().getReference("SSC_CHSL_2023/SPELLING_ERROR_2023")
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

                    val adapter = ViewPagerAdapter(
                        this@QuizActivity,
                        questions,
                        title,
                        category,
                        type
                    )
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
                questions as ArrayList<QuestionData>, title, category, type
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
//            bookmarkData(arrayList)
            if (mInterstitialAd != null) {
                mInterstitialAd?.show(this)
                mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
                    override fun onAdClicked() {
                        // Called when a click is recorded for an ad.
                        Log.d(TAG, "Ad was clicked.")
                    }

                    override fun onAdDismissedFullScreenContent() {
                        // Called when ad is dismissed.
                        Log.d(TAG, "Ad dismissed fullscreen content.")
                        val quizResult = QuizResultPractice(givenAnswer)
                        val categoryName = type
                        val databaseReference = FirebaseDatabase.getInstance().getReference("QuizResultsPractice/$categoryName")

                        databaseReference.setValue(quizResult)
                            .addOnSuccessListener {
                                loader.visibility = View.GONE
                                val intent = Intent(this@QuizActivity, QuizSubmitActivity::class.java)
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
                        mInterstitialAd = null
                    }

                    override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                        // Called when ad fails to show.
                        Log.e(TAG, "Ad failed to show fullscreen content.")
                        mInterstitialAd = null
                    }

                    override fun onAdImpression() {
                        // Called when an impression is recorded for an ad.
                        Log.d(TAG, "Ad recorded an impression.")
                    }

                    override fun onAdShowedFullScreenContent() {
                        // Called when ad is shown.
                        Log.d(TAG, "Ad showed fullscreen content.")
                    }
                }
            }
        }

        dialog.noBtn.setOnClickListener {
            loader.visibility = View.GONE
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

    fun onPrevClick(position: Int) {
        idViewPager.setCurrentItem(idViewPager.currentItem - 1, true)
    }


}