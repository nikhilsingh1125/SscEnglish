package com.sscenglishpractice.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import com.sscenglishpractice.QuizActivity
import com.sscenglishpractice.R
import com.sscenglishquiz.model.QuestionData
import kotlinx.android.synthetic.main.row_question_list.view.*
import java.util.*
import kotlin.collections.ArrayList

class ViewPagerAdapter(val context: Context, val arrayList: ArrayList<QuestionData>) :
    PagerAdapter() {
    // on below line we are creating a method
    // as get count to return the size of the list.

    private var optionASelected = false
    private var optionBSelected = false
    private var optionCSelected = false
    private var optionDSelected = false
    var correctAnswerCount = 0
    var incorrectAnswerCount = 0
    var answeredCorrectly = false

    override fun getCount(): Int {
        return arrayList?.size!!
    }

    // on below line we are returning the object
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as RelativeLayout
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

        val model = arrayList?.get(position)

        itemView.cvA.setOnClickListener {
            selectOptionA(itemView, model)
        }

        itemView.cvB.setOnClickListener {
            selectOptionB(itemView, model)
        }

        itemView.cvC.setOnClickListener {
            selectOptionC(itemView, model)
        }

        itemView.cvD.setOnClickListener {
            selectOptionD(itemView, model)
        }




        itemView.btnNext.setOnClickListener {
            (context as QuizActivity).clickOnBtnNext(position)
          //  itemView.btnNext.setBackgroundColor(ContextCompat.getColor(context, R.color.green))
        }

        itemView.btnSubmit.setOnClickListener {
            (context as QuizActivity).clickOnSubmitNext(position,correctAnswerCount,incorrectAnswerCount,arrayList.size)
            //  itemView.btnNext.setBackgroundColor(ContextCompat.getColor(context, R.color.green))
        }

        // on the below line we are adding this
        // item view to the container.
        Objects.requireNonNull(container).addView(itemView)

        // on below line we are simply
        // returning our item view.
        return itemView
    }

    private fun selectOptionA(itemView: View, model: QuestionData?) {
        optionASelected = true
        optionBSelected = false
        optionCSelected = false
        optionDSelected = false
        if (model != null) {
            updateOptionSelectedUI(itemView, model)
        }
    }

    private fun selectOptionB(itemView: View, model: QuestionData?) {
        optionASelected = false
        optionBSelected = true
        optionCSelected = false
        optionDSelected = false
        if (model != null) {
            updateOptionSelectedUI(itemView, model)
        }
    }

    private fun selectOptionC(itemView: View, model: QuestionData?) {
        optionASelected = false
        optionBSelected = false
        optionCSelected = true
        optionDSelected = false
        if (model != null) {
            updateOptionSelectedUI(itemView, model)
        }
    }

    private fun selectOptionD(itemView: View, model: QuestionData?) {
        optionASelected = false
        optionBSelected = false
        optionCSelected = false
        optionDSelected = true
        updateOptionSelectedUI(itemView, model)
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }

    private fun displayQuestion(index: Int, itemView: View) {
        val questionData = arrayList?.get(index)

        if (questionData != null) {
            // Update the UI to display the question and answer options

            itemView.txtQuestionCount.text = "Q.${questionData.question_count}"

            itemView.quizQuestion.text = "${questionData.question}"
            itemView.txtAnswerA.text = questionData.option_A
            itemView.txtAnswerB.text = questionData.option_B
            itemView.txtAnswerC.text = questionData.option_C
            itemView.txtAnswerD.text = questionData.option_D

            // Reset the answer option views to their default state
            itemView.cvA.setBackgroundColor(Color.WHITE)
            itemView.cvB.setBackgroundColor(Color.WHITE)
            itemView.cvC.setBackgroundColor(Color.WHITE)
            itemView.cvD.setBackgroundColor(Color.WHITE)
            itemView.cvA.isEnabled = true
            itemView.cvB.isEnabled = true
            itemView.cvC.isEnabled = true
            itemView.cvD.isEnabled = true

            val sharedPreferences = context.getSharedPreferences("quiz", Context.MODE_PRIVATE)
            val correct = sharedPreferences.getInt("correctAnswerCount", 0)
            val incorrect = sharedPreferences.getInt("incorrectAnswerCount", 0)

        }

    }

    private fun updateOptionSelectedUI(itemView: View, model: QuestionData?) {
        itemView.cvA.setBackgroundColor(Color.WHITE)
        itemView.cvB.setBackgroundColor(Color.WHITE)
        itemView.cvC.setBackgroundColor(Color.WHITE)
        itemView.cvD.setBackgroundColor(Color.WHITE)

        if (optionASelected) {
            itemView.cvA.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_answer))
        }
        if (optionBSelected) {
            itemView.cvB.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_answer))
        }
        if (optionCSelected) {
            itemView.cvC.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_answer))
        }
        if (optionDSelected) {
            itemView.cvD.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_answer))
        }

        val selectedAnswer = if (optionASelected) {
            model?.option_A
        } else if (optionBSelected) {
            model?.option_B
        } else if (optionCSelected) {
            model?.option_C
        } else if (optionDSelected) {
            model?.option_D
        } else {
            null // No option is selected
        }

        if (selectedAnswer != null) {
            if (selectedAnswer == model?.answer) {
                // If the question hasn't been answered correctly before, increment count and set flag to true
                correctAnswerCount++

                // Set background color of selected option to green
                if (model != null) {
                    when (selectedAnswer) {
                        model.option_A -> {
                            itemView.cvA.setBackground(
                                ContextCompat.getDrawable(
                                    context,
                                    R.drawable.bg_selected_answer
                                )
                            )
                            itemView.imgA.visibility = View.VISIBLE
                        }
                        model.option_B -> {
                            itemView.cvB.setBackground(
                                ContextCompat.getDrawable(
                                    context,
                                    R.drawable.bg_selected_answer
                                )
                            )
                            itemView.imgB.visibility = View.VISIBLE
                        }
                        model.option_C -> {
                            itemView.cvC.setBackground(
                                ContextCompat.getDrawable(
                                    context,
                                    R.drawable.bg_selected_answer
                                )
                            )
                            itemView.imgC.visibility = View.VISIBLE
                        }
                        model.option_D -> {
                            itemView.cvD.setBackground(
                                ContextCompat.getDrawable(
                                    context,
                                    R.drawable.bg_selected_answer
                                )
                            )
                            itemView.imgD.visibility = View.VISIBLE
                        }
                    }
                }
            }

            else {
                // Set background color of selected option to red
                if (model != null) {

                    incorrectAnswerCount++
                    when (selectedAnswer) {
                        model.option_A -> itemView.cvA.setBackground(
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.bg_wrong_answer
                            )
                        )
                        model.option_B -> itemView.cvB.setBackground(
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.bg_wrong_answer
                            )
                        )
                        model.option_C -> itemView.cvC.setBackground(
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.bg_wrong_answer
                            )
                        )
                        model.option_D -> itemView.cvD.setBackground(
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.bg_wrong_answer
                            )
                        )
                    }
                }
                // Set background color of correct option to green
                if (model != null) {
                    when (model.answer) {
                        model.option_A -> {
                            itemView.cvA.setBackground(
                                ContextCompat.getDrawable(
                                    context,
                                    R.drawable.bg_selected_answer
                                )
                            )
                            itemView.imgA.visibility = View.VISIBLE
                        }

                        model.option_B -> {
                            itemView.cvB.setBackground(
                                ContextCompat.getDrawable(
                                    context,
                                    R.drawable.bg_selected_answer
                                )
                            )
                            itemView.imgB.visibility = View.VISIBLE
                        }
                        model.option_C -> {
                            itemView.cvC.setBackground(
                                ContextCompat.getDrawable(
                                    context,
                                    R.drawable.bg_selected_answer
                                )
                            )
                            itemView.imgC.visibility = View.VISIBLE
                        }
                        model.option_D -> {
                            itemView.cvD.setBackground(
                                ContextCompat.getDrawable(
                                    context,
                                    R.drawable.bg_selected_answer
                                )
                            )
                            itemView.imgD.visibility = View.VISIBLE
                        }
                    }
                }
            }

            val sharedPreferences = context.getSharedPreferences("quiz", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putInt("correctAnswerCount", correctAnswerCount)
            editor.putInt("incorrectAnswerCount", incorrectAnswerCount)
            editor.apply()
            // Disable the button
            itemView.cvA.isEnabled = false
            itemView.cvB.isEnabled = false
            itemView.cvC.isEnabled = false
            itemView.cvD.isEnabled = false
            // Show the updated count in a toast message

        }






        }



    }

