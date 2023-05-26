package com.sscenglishpractice.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sscenglishpractice.CategoryActivity
import com.sscenglishpractice.HomeActivity
import com.sscenglishpractice.MainActivity
import com.sscenglishpractice.R
import com.sscenglishpractice.model.CategoryModel
import kotlinx.android.synthetic.main.row_home_category.view.*

class HomeAdapter(
    val context: Context,
    val arrayList: ArrayList<CategoryModel>
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_home_category,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = arrayList[position]
        holder.itemView.txtCatTitle.text = model.title
        Glide.with(context).load(model.image).into(holder.itemView.catImg);

        holder.itemView.catCV.setOnClickListener {
            (context as HomeActivity).goToCategory(model,position)
        }

    }

    override fun getItemCount(): Int {
       return arrayList.size
    }
}