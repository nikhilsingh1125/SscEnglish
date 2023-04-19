package com.sscenglishpractice.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sscenglishpractice.CategoryActivity
import com.sscenglishpractice.MainActivity
import com.sscenglishpractice.R
import com.sscenglishpractice.model.CategoryModel
import com.sscenglishpractice.model.ListCategoryData
import kotlinx.android.synthetic.main.row_category.view.*
import kotlinx.android.synthetic.main.row_home_category.view.*
import kotlinx.android.synthetic.main.row_note.view.*
import kotlinx.android.synthetic.main.row_note.view.cardQuestionTitle
import kotlinx.android.synthetic.main.row_note.view.txtTitle

class CategoryAdapter(
    val context: Context,
    val arrayList: ArrayList<ListCategoryData>
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_category,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = arrayList[position]
        holder.itemView.txtTitle.text = model.title
        holder.itemView.txtContext.text = model.context

        holder.itemView.cardQuestionTitle.setOnClickListener {
            (context as CategoryActivity).goToNextExamList(model,position)
        }

    }

    override fun getItemCount(): Int {
       return arrayList.size
    }
}