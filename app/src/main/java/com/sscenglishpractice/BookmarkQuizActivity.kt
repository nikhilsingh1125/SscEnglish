package com.sscenglishpractice

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.sscenglishpractice.adapter.BookmarkQuizAdapter
import com.sscenglishquiz.model.QuestionData
import kotlinx.android.synthetic.main.activity_quiz_bookmark.idViewPagerBookmark
import libs.mjn.prettydialog.PrettyDialog

class BookmarkQuizActivity : AppCompatActivity() {

    val TAG = "BookmarkQuizActivity"
    lateinit var questions: ArrayList<QuestionData>
    var receivedTestTitle =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_bookmark)


     /*   loader.setAnimation(R.raw.loader)
        loader.visibility = View.VISIBLE
        loader.playAnimation()

        btnSubmit.visibility = View.GONE
        action_bar_Title.text = "BookMark Question"

        action_bar_back.visibility = View.VISIBLE
        action_bar_share.visibility = View.GONE*/

       /* action_bar_back.setOnClickListener {
            onBackPressed()
        }*/

        initBookmark()
    }

    private fun initBookmark() {
        val receivedBundle = intent.extras
        if (receivedBundle != null) {
            questions =
                receivedBundle.getParcelableArrayList<QuestionData>("questionsList") as ArrayList<QuestionData>
            receivedTestTitle = receivedBundle.getString("testTitle").toString()
            Log.e(TAG, "questions ==>: $questions")
        }

        val adapter = BookmarkQuizAdapter(this@BookmarkQuizActivity, questions, receivedTestTitle, "","")
        idViewPagerBookmark.adapter = adapter
    }



    fun clickOnBtnNextBook() {
        idViewPagerBookmark.setCurrentItem(idViewPagerBookmark.currentItem + 1, true)
    }

    fun onPrevClickBook() {
        idViewPagerBookmark.setCurrentItem(idViewPagerBookmark.currentItem - 1, true)
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