package com.sscenglishpractice

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.sscenglishpractice.adapter.ResultAdapter
import com.sscenglishpractice.model.SubmitDetailsData
import com.sscenglishquiz.model.QuestionData
import kotlinx.android.synthetic.main.activity_quiz.loader
import kotlinx.android.synthetic.main.activity_result.rvResults
import kotlinx.android.synthetic.main.custom_toolbar.action_bar_back
import kotlinx.android.synthetic.main.custom_toolbar.action_bar_share
import kotlinx.android.synthetic.main.custom_toolbar.btnSubmit

class ResultActivity : AppCompatActivity() {

    lateinit var rvAdapter: ResultAdapter
    lateinit var db : FirebaseFirestore
    private lateinit var arrSubmitDetailsData: ArrayList<SubmitDetailsData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        db = FirebaseFirestore.getInstance()

        arrSubmitDetailsData = arrayListOf()

        loader.setAnimation(R.raw.loader)
        loader.visibility = View.VISIBLE
        loader.playAnimation()

        btnSubmit.visibility = View.GONE
        action_bar_back.visibility = View.VISIBLE
        action_bar_share.visibility = View.GONE

        action_bar_back.setOnClickListener {
            onBackPressed()
        }

        val sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val categoryType = sharedPreferences.getString("categoryData", null)

        Log.d("sharedPreferences", "categoryType: $categoryType")
        if (categoryType != null) {
            getBookmarkDataForCategory(categoryType)
        }
       /* Log.e("Submit_Test", "onCreate:")
        db.collection("Submit_Test").get()
            .addOnSuccessListener {
                loader.visibility = View.GONE
                if (!it.isEmpty) {

                    for (data in it.documents) {
                        Log.e("Submit_Test", "onCreate: $data")
                        System.out.println("test123 data" + it.documents)
                        val submitDetailsData: SubmitDetailsData? = data.toObject(SubmitDetailsData::class.java)
                        Log.e("Submit_Test", "submitDetailsData: $submitDetailsData")
                        if (submitDetailsData != null) {
                            arrSubmitDetailsData.add(submitDetailsData)

                            rvResults.layoutManager = LinearLayoutManager(this@ResultActivity)
                            rvAdapter = ResultAdapter(this@ResultActivity, arrSubmitDetailsData)
                            rvResults.adapter = rvAdapter
                        } else {
                            recyclerView.visibility = View.GONE
                        }
                    }

                    rvAdapter.notifyDataSetChanged()

                }
            }
            .addOnFailureListener {
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }*/




    }
    fun getBookmarkDataForCategory(category: String) {
        val firestore = FirebaseFirestore.getInstance()
        val collectionRef = firestore.collection("category").document(category).collection("bookData")

        collectionRef.get()
            .addOnSuccessListener { querySnapshot ->
                val bookmarkedQuestions = ArrayList<QuestionData>()
                loader.visibility = View.GONE
                for (documentSnapshot in querySnapshot.documents) {
                    val questionData = documentSnapshot.toObject(QuestionData::class.java)
                    if (questionData != null) {
                        bookmarkedQuestions.add(questionData)
                        rvResults.layoutManager = LinearLayoutManager(this@ResultActivity)
                        rvAdapter = ResultAdapter(this@ResultActivity, bookmarkedQuestions)
                        rvResults.adapter = rvAdapter
                        Log.e("BookmarkData", "bookmarkedQuestions ==>: $bookmarkedQuestions", )
                    }
                }

            }
            .addOnFailureListener { e ->
                // Handle any errors
                Log.e("SelectedUI", "Error fetching bookmarked data for category $category: ${e.message}", e)
            }
    }
}