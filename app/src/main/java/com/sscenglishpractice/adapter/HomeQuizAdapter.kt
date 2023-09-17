package com.sscenglishpractice.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sscenglishpractice.ExamQuizsActivity
import com.sscenglishpractice.R
import com.sscenglishpractice.model.QuizCategoryModel
import kotlinx.android.synthetic.main.row_quiz_category.view.btnStart
import kotlinx.android.synthetic.main.row_quiz_category.view.txtCatTitle

class HomeQuizAdapter(
    val context: Context,
    val arrayList: MutableList<QuizCategoryModel>
) : RecyclerView.Adapter<HomeQuizAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_quiz_category,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = arrayList[position]
        holder.itemView.txtCatTitle.text = model.Test_Title

        holder.itemView.btnStart.setOnClickListener {
            val intent = Intent(context, ExamQuizsActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelableArrayList("questionsList", ArrayList(model.Questions))
            bundle.putString("testTitle", model.Test_Title)
            bundle.putString("timeTaken", model.Time_taken)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
       return arrayList.size
    }

}