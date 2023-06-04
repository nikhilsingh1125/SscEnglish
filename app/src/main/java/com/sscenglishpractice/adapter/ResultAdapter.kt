package com.sscenglishpractice.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sscenglishpractice.CategoryActivity
import com.sscenglishpractice.QuizSubmitActivity
import com.sscenglishpractice.R
import com.sscenglishpractice.model.SubmitDetailsData
import kotlinx.android.synthetic.main.row_category.view.cardQuestionTitle
import kotlinx.android.synthetic.main.row_category.view.txtContext
import kotlinx.android.synthetic.main.row_note.view.txtTitle
import kotlinx.android.synthetic.main.row_results.view.btnViewSolution

class ResultAdapter(
    val context: Context,
    val arrayList: ArrayList<SubmitDetailsData>
) : RecyclerView.Adapter<ResultAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_results,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = arrayList[position]
        holder.itemView.txtTitle.text = model.titleSubject


        holder.itemView.btnViewSolution.setOnClickListener {
          context.startActivity(Intent(context,QuizSubmitActivity::class.java))
            val sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("correctAnswer", model.correctAnswer)
            editor.putString("incorrectAnswer", model.incorrectAnswer)
            editor.putString("totalQuestion", model.totalQuestion)
            editor.putString("Title",model.paperType)
            editor.putBoolean("key3", true)
            editor.apply()
        }

    }

    override fun getItemCount(): Int {
       return arrayList.size
    }
}