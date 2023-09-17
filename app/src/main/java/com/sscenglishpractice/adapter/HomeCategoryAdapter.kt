package com.sscenglishpractice.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sscenglishpractice.HomeCategoryActivity
import com.sscenglishpractice.R
import com.sscenglishpractice.model.CategoryModel
import kotlinx.android.synthetic.main.row_home_category.view.catCV
import kotlinx.android.synthetic.main.row_home_category.view.catImg
import kotlinx.android.synthetic.main.row_home_category.view.txtCatTitle

class HomeCategoryAdapter(
    val context: Context,
    val arrayList: ArrayList<CategoryModel>
) : RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder>() {

    val TAG = "HomeActivity"
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_home,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = arrayList[position]
        holder.itemView.txtCatTitle.text = model.title
        Glide.with(context).load(model.image).into(holder.itemView.catImg);
       /* holder.itemView.loaderCat.setAnimation(model.image)
        holder.itemView.loaderCat.playAnimation()*/



        holder.itemView.catCV.setOnClickListener {
            (context as HomeCategoryActivity).goToCategory(model,position)
        }

    }

    override fun getItemCount(): Int {
       return arrayList.size
    }

}