package com.sscenglishpractice.adapter

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import com.sscenglishpractice.HomeActivity
import com.sscenglishpractice.R
import com.sscenglishpractice.ViewSolutionActivity
import com.sscenglishquiz.model.ResultShowData
import kotlinx.android.synthetic.main.row_question_list.view.*
import java.util.*
import kotlin.collections.ArrayList

class ViewSolutionAdapter(
    val context: Context,
    val arrayList: ArrayList<ResultShowData>,
    val categoryType: String?,
) :
    PagerAdapter() {
    override fun getCount(): Int {
        return arrayList.size
    }

    // on below line we are returning the object
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as LinearLayout
    }

    // on below line we are initializing
    // our item and inflating our layout file
    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        // on below line we are initializing
        // our layout inflater.
        val mLayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        // on below line we are inflating our custom
        // layout file which we have created.
        val itemView: View = mLayoutInflater.inflate(R.layout.row_question_list, container, false)

        displayQuestion(position, itemView)

        val model = arrayList.get(position)

        itemView.txtQuizTitle.text = categoryType

        itemView.btnSubmit.visibility = View.GONE


        Handler(Looper.getMainLooper()).postDelayed({
            itemView.txtTotalQuestions.text = "${position+1}/${arrayList.size}"
        }, 100)


        resultSelectedUI(itemView, model)


        itemView.btnNext.setOnClickListener {
            (context as ViewSolutionActivity).clickOnBtnNext(position,arrayList)
            //  itemView.btnNext.setBackgroundColor(ContextCompat.getColor(context, R.color.green))
        }

        if (position == arrayList.size) {
            itemView.btnSubmit.visibility = View.VISIBLE
            itemView.btnSubmit.setOnClickListener {
                context.startActivity(Intent(context,HomeActivity::class.java))
            }
        }


        // on the below line we are adding this
        // item view to the container.
        Objects.requireNonNull(container).addView(itemView)

        // on below line we are simply
        // returning our item view.
        return itemView
    }


    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }

    private fun displayQuestion(index: Int, itemView: View) {
        val questionData = arrayList.get(index)

        if (questionData != null) {
            // Update the UI to display the question and answer options


            itemView.quizQuestion.text = "${questionData.question}"
            itemView.txtAnswerA.text = questionData.option_A
            itemView.txtAnswerB.text = questionData.option_B
            itemView.txtAnswerC.text = questionData.option_C
            itemView.txtAnswerD.text = questionData.option_D

            // Reset the answer option views to their default state
            itemView.cvA.background = ContextCompat.getDrawable(context, R.drawable.bg_answer_quiz)
            itemView.cvB.background = ContextCompat.getDrawable(context, R.drawable.bg_answer_quiz)
            itemView.cvC.background = ContextCompat.getDrawable(context, R.drawable.bg_answer_quiz)
            itemView.cvD.background = ContextCompat.getDrawable(context, R.drawable.bg_answer_quiz)

            itemView.cvA.isEnabled = true
            itemView.cvB.isEnabled = true
            itemView.cvC.isEnabled = true
            itemView.cvD.isEnabled = true

            val sharedPreferences = context.getSharedPreferences("quiz", Context.MODE_PRIVATE)
            val correct = sharedPreferences.getInt("correctAnswerCount", 0)
            val incorrect = sharedPreferences.getInt("incorrectAnswerCount", 0)

        }

    }


    private fun resultSelectedUI(itemView: View, model: ResultShowData?) {
        val selectedAnswer = model?.selectedAnswer
        val correctAnswer = model?.answer

        // Reset the background color of all answer options
        itemView.llSolutions.visibility = View.VISIBLE
        itemView.cvA.background = ContextCompat.getDrawable(context, R.drawable.bg_answer_quiz)
        itemView.cvB.background = ContextCompat.getDrawable(context, R.drawable.bg_answer_quiz)
        itemView.cvC.background = ContextCompat.getDrawable(context, R.drawable.bg_answer_quiz)
        itemView.cvD.background = ContextCompat.getDrawable(context, R.drawable.bg_answer_quiz)

        // Change the background color of the selected answer option if given answer is true and matches the correct answer
        if (model != null) {

            Log.e("updateOptionSelectedUI", " Solutions: ${model.Solutions}")
            if (model.Solutions != null) {
                var isSolutionVisible = false

                // Set initial visibility of buttons
                itemView.seeSolutionButton.visibility = View.VISIBLE
                itemView.hideSolutionButton.visibility = View.GONE

                itemView.seeSolutionButton.setOnClickListener {
                    if (!isSolutionVisible) {
                        isSolutionVisible = true
                        // Show the solution
                        if (model.Solutions == "") {
                            itemView.solutionTextView.visibility = View.VISIBLE
                            itemView.solutionTextView.text = "Coming soon"
                            itemView.seeSolutionButton.visibility = View.GONE
                            itemView.hideSolutionButton.visibility = View.VISIBLE
                        } else {
                            itemView.solutionTextView.text = model.Solutions
                            itemView.solutionTextView.visibility = View.VISIBLE
                            itemView.seeSolutionButton.visibility = View.GONE
                            itemView.hideSolutionButton.visibility = View.VISIBLE
                        }

                    }
                }

                itemView.hideSolutionButton.setOnClickListener {
                    if (isSolutionVisible) {
                        isSolutionVisible = false
                        // Hide the solution
                        itemView.solutionTextView.visibility = View.GONE
                        itemView.seeSolutionButton.visibility = View.VISIBLE
                        itemView.hideSolutionButton.visibility = View.GONE
                    }
                }
            } else {
                itemView.seeSolutionButton.text = "UpComing Solutions"
                itemView.seeSolutionButton.visibility = View.VISIBLE
            }

            Log.e("resultSelectedUI", "answer ==>${model.answer} selectedAnswer==> ${model.selectedAnswer}")
            Log.e("resultSelectedUI", "answer ==>${model.answer} selectedOptions==> ${model.selectedOptions}")
            when (model.optionsSelected) {

                "A" -> {
                    if (model.answer == model.selectedOptions) {

                        itemView.cvA.background = ContextCompat.getDrawable(
                            context,
                            R.drawable.bg_selected_answer
                        )
                    } else {
                        itemView.cvA.background = ContextCompat.getDrawable(
                            context,
                            R.drawable.bg_wrong_answer
                        )
                        itemView.wrongA.visibility = View.VISIBLE
                        itemView.txtA.visibility = View.GONE

                        Log.e("resultSelectedUI", "answer ==>${model.answer} option_A==> ${model.option_A}")
                        if (model.answer == model.option_A){
                            itemView.cvA.background = ContextCompat.getDrawable(
                                context,
                                R.drawable.bg_selected_answer
                            )
                        }
                    }
                }

                "B" -> {
                    if (model.answer == model.selectedOptions) {
                        itemView.cvB.background = ContextCompat.getDrawable(
                            context,
                            R.drawable.bg_selected_answer
                        )
                    } else {
                        itemView.cvB.background = ContextCompat.getDrawable(
                            context,
                            R.drawable.bg_wrong_answer
                        )
                        itemView.wrongB.visibility = View.VISIBLE
                        itemView.txtB.visibility = View.GONE
                        Log.e("resultSelectedUI", "answer ==>${model.answer} option_B==> ${model.option_B}")
                        if (model.answer == model.option_B){
                            itemView.cvA.background = ContextCompat.getDrawable(
                                context,
                                R.drawable.bg_selected_answer
                            )
                        }
                    }
                }

                "C" -> {
                    if (model.answer == model.selectedOptions) {
                        itemView.cvC.background = ContextCompat.getDrawable(
                            context,
                            R.drawable.bg_selected_answer
                        )
                    } else {
                        itemView.cvC.background = ContextCompat.getDrawable(
                            context,
                            R.drawable.bg_wrong_answer
                        )
                        itemView.wrongC.visibility = View.VISIBLE
                        itemView.txtC.visibility = View.GONE

                        Log.e("resultSelectedUI", "answer ==>${model.answer} option_C==> ${model.option_C}")
                        if (model.answer ==model.option_C){
                            itemView.cvC.background = ContextCompat.getDrawable(
                                context,
                                R.drawable.bg_selected_answer
                            )
                        }
                    }
                }

                "D" -> {
                    if (model.answer == model.selectedOptions) {
                        itemView.cvD.background = ContextCompat.getDrawable(
                            context,
                            R.drawable.bg_selected_answer
                        )
                    } else {
                        itemView.cvD.background = ContextCompat.getDrawable(
                            context,
                            R.drawable.bg_wrong_answer
                        )
                        itemView.wrongD.visibility = View.VISIBLE
                        itemView.txtD.visibility = View.GONE
                        Log.e("resultSelectedUI", "answer ==>${model.answer} option_D==> ${model.option_D}")
                        if (model.answer == model.option_D){
                            itemView.cvD.background = ContextCompat.getDrawable(
                                context,
                                R.drawable.bg_selected_answer
                            )
                        }
                    }
                }
            }
        }
    }


}

