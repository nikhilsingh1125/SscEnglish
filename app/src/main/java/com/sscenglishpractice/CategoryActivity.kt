package com.sscenglishpractice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sscenglishpractice.adapter.CategoryAdapter
import com.sscenglishpractice.model.ListCategoryData
import com.sscenglishpractice.utils.Constants
import kotlinx.android.synthetic.main.activity_main.recyclerView
import kotlinx.android.synthetic.main.custom_toolbar.action_bar_back
import kotlinx.android.synthetic.main.custom_toolbar.action_bar_share
import kotlinx.android.synthetic.main.custom_toolbar.btnSubmit

class CategoryActivity : AppCompatActivity() {
    var type = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category2)

        btnSubmit.visibility = View.GONE
        val intent = intent
        type = intent.getStringExtra("TYPE").toString()



        val sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val categoryType = sharedPreferences.getString("categoryData", null)

        Log.d("sharedPreferences", "categoryType: $categoryType")

        Constants.getCategoryNames()

        if (categoryType != null) {
            Constants.getBookmarkDataForCategory(categoryType)
        }


        action_bar_back.visibility = View.VISIBLE
        action_bar_share.visibility = View.GONE

        action_bar_back.setOnClickListener {
            onBackPressed()
        }

        val array = ArrayList<ListCategoryData>()
        if (type == "2022") {
            array.add(ListCategoryData("SSC CGL", "SSC CGL 2022 Tier-I"))
            array.add(ListCategoryData("SSC CHSL", "SSC CHSL 2022 Tier-I"))
            array.add(ListCategoryData("SSC CPO", "SSC CPO 2022 Tier-I"))
            array.add(ListCategoryData("SSC MTS", "SSC MTS 2022 Tier-I"))
            array.add(ListCategoryData("SSC Stenographer", "SSC Stenographer Grade ‘C’ & ‘D’ 2022 Tier-I"))
        } else if (type == "2021") {
            array.add(ListCategoryData("SSC CGL", "SSC CGL 2021 Tier-I"))
            array.add(ListCategoryData("SSC CHSL", "SSC CHSL 2021 Tier-I"))
            array.add(ListCategoryData("SSC MTS", "SSC MTS 2021 Tier-I"))
        } else if (type == "2020") {
            array.add(ListCategoryData("SSC CGL", "SSC CGL 2020 Tier-I"))
            array.add(ListCategoryData("SSC CHSL", "SSC CHSL 2020 Tier-I"))
            array.add(ListCategoryData("SSC MTS", "SSC MTS 2020 Tier-I"))
            array.add(ListCategoryData("SSC CPO", "SSC CPO 2020 Tier-I"))
        } else if (type == "2019") {
            array.add(ListCategoryData("SSC CGL", "SSC CGL 2019 Tier-I"))
            array.add(ListCategoryData("SSC MTS", "SSC MTS 2019 Tier-I"))
            array.add(ListCategoryData("SSC CPO", "SSC CPO 2019 Tier-I"))
            array.add(ListCategoryData("SSC CHSL", "SSC CHSL 2019 Tier-I"))
        } else if (type == "2023") {
            array.add(ListCategoryData("SSC CGL", "SSC CGL 2023 Tier-I"))
            array.add(ListCategoryData("SSC PHASE", "SSC PHASE 11 2023 Tier-I"))
            array.add(ListCategoryData("SSC CHSL", "SSC CHSL 2023 Tier-I"))
        }



        recyclerView.layoutManager = LinearLayoutManager(this@CategoryActivity)
        val rvAdapter = CategoryAdapter(this@CategoryActivity, array)
        recyclerView.adapter = rvAdapter
    }

    fun goToNextExamList(model: ListCategoryData, position: Int) {
        val intent = Intent(this, MainActivity::class.java)

        if (type == "2022") {
            if (position == 0) {
                intent.putExtra("TYPE", "CGL")
            } else if (position == 1) {
                intent.putExtra("TYPE", "CHCL")
            } else if (position == 2) {
                intent.putExtra("TYPE", "CPO")
            } else if (position == 3) {
                intent.putExtra("TYPE", "MTS")
            } else if (position == 4) {
                intent.putExtra("TYPE", "Stenographer")
            }
        } else if (type == "2021") {
            if (position == 0) {
                intent.putExtra("TYPE", "CGL_2021")
            } else if (position == 1) {
                intent.putExtra("TYPE", "CHCL_2021")
            } else if (position == 2) {
                intent.putExtra("TYPE", "MTS_2021")
            }
        } else if (type == "2020") {
            if (position == 0) {
                intent.putExtra("TYPE", "CGL_2020")
            } else if (position == 1) {
                intent.putExtra("TYPE", "MTS_2020")
            } else if (position == 2) {
                intent.putExtra("TYPE", "CHSL_2020")
            } else if (position == 3) {
                intent.putExtra("TYPE", "CPO_2020")
            }
        } else if (type == "2019") {
            if (position == 0) {
                intent.putExtra("TYPE", "CGL_2019")
            } else if (position == 1) {
                intent.putExtra("TYPE", "MTS_2019")
            } else if (position == 2) {
                intent.putExtra("TYPE", "CPO_2019")
            }
            else if (position == 3) {
                intent.putExtra("TYPE", "CHSL_2019")
            }
        }else if (type == "2023") {
            if (position == 0) {
                intent.putExtra("TYPE", "CGL_2023")
            } else if (position == 1) {
                intent.putExtra("TYPE", "PHASE_2023")
            }
            else if (position == 2) {
                intent.putExtra("TYPE", "CHSL_2023")
            }
        }

        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}