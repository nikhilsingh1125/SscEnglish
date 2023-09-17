package com.sscenglishpractice.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sscenglishpractice.ExamQuizsActivity
import com.sscenglishpractice.R
import com.sscenglishpractice.model.Question
import com.sscenglishpractice.model.QuizQuestion
import com.sscenglishpractice.model.ResultShowData
import com.sscenglishquiz.model.QuestionData
import kotlinx.android.synthetic.main.row_questions_pos.view.txtQuestionPos

class QuestionPositionAdapter(
    private val context: Context,
    val arrayList: ArrayList<Question>
) : RecyclerView.Adapter<QuestionPositionAdapter.ViewHolder>() {

    private var selectedItem = -1 // -1 indicates no item is initially selected

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Empty ViewHolder, as it doesn't require any additional references
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.row_questions_pos,
            parent, false
        )

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val TAG = "QuestionPositionAdapter"
        Log.e(TAG, "onBindViewHolder: $selectedItem")
        Log.e(TAG, "onBindViewHolder: $position")

        val model = arrayList[position]
        holder.itemView.txtQuestionPos.text = "${position + 1}"

        Log.e("updateOptionSelectedUI", "isGivenAnswer: ${model.isGivenAnswer}")
        Log.e("updateOptionSelectedUI", "isSelectedAnswer: ${model.isSelectedAnswer}")
        Log.e("updateOptionSelectedUI", "isVisited: ${model.isVisited}")
        Log.e("updateOptionSelectedUI", "userSelectAnswer: ${model.userSelectAnswer} answer:${model.answer}")

        val isWrongAnswer = (model.answer != model.userSelectAnswer)
        // Set the background based on the selection state
        if (model.isSelectedAnswer) {
            holder.itemView.txtQuestionPos.background =
                ContextCompat.getDrawable(context, R.drawable.bg_selected_answer)
        }
        else if(model.isVisited){
            holder.itemView.txtQuestionPos.background =
                ContextCompat.getDrawable(context, R.drawable.bg_visited_answer)
        }
        else if(model.isGivenAnswer && model.answer != model.userSelectAnswer) {
            holder.itemView.txtQuestionPos.background =
                ContextCompat.getDrawable(context, R.drawable.bg_wrong_answer)
        }
        else {
            holder.itemView.txtQuestionPos.background =
                ContextCompat.getDrawable(context, R.drawable.question_pos)
        }

        holder.itemView.setOnClickListener {
            // Update the selected item position when an item is clicked
            setSelectedItem(position)
            (context as ExamQuizsActivity).onImageClick(position)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    // Method to set the selected item and notify the adapter about the change
    fun setSelectedItem(position: Int) {
        selectedItem = position
        notifyDataSetChanged()
    }
}
