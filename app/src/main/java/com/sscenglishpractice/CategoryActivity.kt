package com.sscenglishpractice

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sscenglishpractice.adapter.CategoryAdapter
import com.sscenglishpractice.model.ListCategoryData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_toolbar.*

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category2)

        action_bar_back.visibility = View.VISIBLE
        action_bar_share.visibility = View.GONE

        action_bar_back.setOnClickListener {
            onBackPressed()
        }

        val array = ArrayList<ListCategoryData>()
        array.add(ListCategoryData("SSC CGL", "SSC CGL 2022 Tier-I"))
        array.add(ListCategoryData("SSC CHSL", "SSC CGL 2022 Tier-I"))

        recyclerView.layoutManager = LinearLayoutManager(this@CategoryActivity)
        val rvAdapter = CategoryAdapter(this@CategoryActivity, array)
        recyclerView.adapter = rvAdapter
    }

    fun goToNextExamList(model: ListCategoryData, position: Int) {
        val intent = Intent(this, MainActivity::class.java)
        if (position == 0) {
            intent.putExtra("TYPE", "CGL")
        } else if (position == 1) {
            intent.putExtra("TYPE", "CHCL")
        }
        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}