package com.sscenglishpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.lottie.utils.Utils
import com.sscenglishpractice.adapter.HomeAdapter
import com.sscenglishpractice.model.CategoryModel
import com.sscenglishpractice.utils.Constants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_toolbar.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        action_bar_share.setOnClickListener {
            Constants.shareApp(this)
        }

        val array = ArrayList<CategoryModel>()

        array.add(CategoryModel("Practice", R.drawable.practice))
        array.add(CategoryModel("Practice", R.drawable.practice))

        recyclerView.layoutManager = GridLayoutManager(this@HomeActivity,2)
        val rvAdapter = HomeAdapter(this@HomeActivity, array)
        recyclerView.adapter = rvAdapter
    }
    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            doubleBackToExitPressedOnce = false
        }, 2000)
    }
}