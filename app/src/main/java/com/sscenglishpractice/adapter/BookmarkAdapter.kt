package com.sscenglishpractice.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sscenglishpractice.BookmarkQuizActivity
import com.sscenglishpractice.R
import com.sscenglishpractice.model.BookmarkedModel
import kotlinx.android.synthetic.main.row_quiz_category.view.btnStart
import kotlinx.android.synthetic.main.row_quiz_category.view.llLocked
import kotlinx.android.synthetic.main.row_quiz_category.view.txtCatTitle

class BookmarkAdapter(
    val context: Context,
    val arrayList: MutableList<BookmarkedModel>
) : RecyclerView.Adapter<BookmarkAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_quiz_category,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = arrayList[position]

        holder.itemView.txtCatTitle.text = model.name
        holder.itemView.llLocked.visibility = View.GONE


        holder.itemView.btnStart.setOnClickListener {
            Log.e("BindViewHolder", "onBindViewHolder: ${model.questions}", )
           val intent = Intent(context, BookmarkQuizActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelableArrayList("questionsList", model.questions)
            bundle.putString("testTitle", model.name)
            intent.putExtras(bundle)
            context.startActivity(intent)


        }


    }

    override fun getItemCount(): Int {
       return arrayList.size
    }




}