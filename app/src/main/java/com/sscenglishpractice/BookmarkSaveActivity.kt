package com.sscenglishpractice

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sscenglishpractice.adapter.BookmarkAdapter
import com.sscenglishpractice.model.BookmarkedModel
import com.sscenglishpractice.utils.Constants
import com.sscenglishquiz.model.QuestionData
import kotlinx.android.synthetic.main.activity_bookmark_save2.noData
import kotlinx.android.synthetic.main.activity_bookmark_save2.noDataFound
import kotlinx.android.synthetic.main.activity_bookmark_save2.recyclerViewBookmark
import kotlinx.android.synthetic.main.activity_quiz.loader
import kotlinx.android.synthetic.main.custom_toolbar.action_bar_Title
import kotlinx.android.synthetic.main.custom_toolbar.action_bar_back
import kotlinx.android.synthetic.main.custom_toolbar.action_bar_share
import kotlinx.android.synthetic.main.custom_toolbar.btnSubmit

class BookmarkSaveActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark_save2)

        btnSubmit.visibility = View.GONE
        action_bar_Title.text = "BookMark"

        action_bar_back.visibility = View.VISIBLE
        action_bar_share.visibility = View.GONE

        loader.setAnimation(R.raw.loader)
        loader.visibility = View.VISIBLE
        loader.playAnimation()
        getResultData()

        action_bar_back.setOnClickListener {
            onBackPressed()
        }

        val savedCategory = Constants.getCategoryFromSharedPreferences(this)

        val sh = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        val category = sh.getString("Category", "")

        Log.e("BookMarkActivity", "category: $category")

        if (category != null) {
            getResultData()
        } else {
            Toast.makeText(this, "Data Not Available", Toast.LENGTH_SHORT).show()
        }

    }

    private fun getResultData() {

        database = FirebaseDatabase.getInstance().reference.child("bookmarked_questions")
            .child("categories")


        // Attach a ValueEventListener to retrieve data from Firebase
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                loader.visibility = View.GONE
                val categoryList = mutableListOf<BookmarkedModel>()
                for (categorySnapshot in snapshot.children) {
                    val categoryName = categorySnapshot.key.toString()
                    val questions = ArrayList<QuestionData>()
                    for (questionSnapshot in categorySnapshot.children) {
                        val question = questionSnapshot.child("question").value.toString()
                        val answer = questionSnapshot.child("answer").value.toString()
                        val option_A = questionSnapshot.child("option_A").value.toString()
                        val option_B = questionSnapshot.child("option_B").value.toString()
                        val option_C = questionSnapshot.child("option_C").value.toString()
                        val option_D = questionSnapshot.child("option_D").value.toString()
                        val Solutions = questionSnapshot.child("Solutions").value.toString()
                        val isBookmark = questionSnapshot.child("isBookmark").value
                        val isSelectedAnswer =
                            questionSnapshot.child("isSelectedAnswer").value.toString()
                        val isSkipAnswer = questionSnapshot.child("isSkipAnswer").value.toString()
                        val isWrongAnswer = questionSnapshot.child("isWrongAnswer").value.toString()
                        val questionIdentifier =
                            questionSnapshot.child("questionIdentifier").value.toString()
                        questions.add(
                            QuestionData(
                                "",
                                question,
                                answer,
                                option_A,
                                option_B,
                                option_C,
                                option_D,
                                Solutions,
                                false,
                                "",
                                "",
                                "",
                                isBookmark as Boolean,
                                false,
                                false,
                                false,
                                questionIdentifier
                            ),
                        )
                    }

                    categoryList.add(BookmarkedModel(categoryName, questions))
                }

                if (categoryList.size == 0) {
                    recyclerViewBookmark.visibility = View.GONE
                    noDataFound.visibility = View.VISIBLE
                    noData.setAnimation(R.raw.no_data)
                    noData.visibility = View.VISIBLE
                    noData.playAnimation()
                } else {
                    recyclerViewBookmark.visibility = View.VISIBLE
                    noDataFound.visibility = View.GONE
                    noData.visibility = View.GONE

                }
                val adapter = BookmarkAdapter(this@BookmarkSaveActivity, categoryList)
                recyclerViewBookmark.adapter = adapter
                recyclerViewBookmark.layoutManager = LinearLayoutManager(this@BookmarkSaveActivity)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error here
            }
        })


    }
}