package com.sscenglishpractice

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sscenglishpractice.adapter.QuestionAdapter
import com.sscenglishpractice.model.ListCategoryData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_toolbar.*

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    var type = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = intent
        type = intent.getStringExtra("TYPE").toString()
        val array = ArrayList<ListCategoryData>()

        action_bar_back.visibility = View.VISIBLE
        action_bar_share.visibility = View.GONE

        action_bar_back.setOnClickListener {
            onBackPressed()
        }

        if (type == "CGL"){
            array.add(ListCategoryData("SYNONYMS", ""))
            array.add(ListCategoryData("ANTONYMS", ""))
            array.add(ListCategoryData("ONEWORD", ""))
            array.add(ListCategoryData("Idioms and pharses", ""))
        }
        else if (type == "CHCL"){
            array.add(ListCategoryData("SYNONYMS", ""))
        }


        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        val rvAdapter = QuestionAdapter(this@MainActivity, array)
        recyclerView.adapter = rvAdapter

    }

    fun goToQuizActi(model: ListCategoryData, position: Int) {

        val intent = Intent(this, QuizActivity::class.java)
        if (type =="CGL"){
            if (position == 0) {
                intent.putExtra("TYPE", "0")
            } else if (position == 1) {
                intent.putExtra("TYPE", "1")
            } else if (position == 2) {
                intent.putExtra("TYPE", "2")
            } else if (position == 3) {
                intent.putExtra("TYPE", "3")
            }
        }
        else if (type == "CHCL"){
            if (position == 0) {
                intent.putExtra("TYPE", "CHCL_1")
            }
        }



        startActivity(intent)
    }

}