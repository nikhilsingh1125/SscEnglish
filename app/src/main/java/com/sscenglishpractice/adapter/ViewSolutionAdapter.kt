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
import com.sscenglishpractice.model.ResultShowData
import kotlinx.android.synthetic.main.row_question_list.view.btnSubmit
import kotlinx.android.synthetic.main.row_solution_list.view.btnNextSol
import kotlinx.android.synthetic.main.row_solution_list.view.btnPrevResult
import kotlinx.android.synthetic.main.row_solution_list.view.cvASol
import kotlinx.android.synthetic.main.row_solution_list.view.cvBSol
import kotlinx.android.synthetic.main.row_solution_list.view.cvCSol
import kotlinx.android.synthetic.main.row_solution_list.view.cvDSol
import kotlinx.android.synthetic.main.row_solution_list.view.hideSolutionButtonSol
import kotlinx.android.synthetic.main.row_solution_list.view.llSolutionsSol
import kotlinx.android.synthetic.main.row_solution_list.view.quizQuestionSol
import kotlinx.android.synthetic.main.row_solution_list.view.seeSolutionButtonSol
import kotlinx.android.synthetic.main.row_solution_list.view.solutionTextViewSol
import kotlinx.android.synthetic.main.row_solution_list.view.txtASol
import kotlinx.android.synthetic.main.row_solution_list.view.txtAnswerASol
import kotlinx.android.synthetic.main.row_solution_list.view.txtAnswerBSol
import kotlinx.android.synthetic.main.row_solution_list.view.txtAnswerCSol
import kotlinx.android.synthetic.main.row_solution_list.view.txtAnswerDSol
import kotlinx.android.synthetic.main.row_solution_list.view.txtBSol
import kotlinx.android.synthetic.main.row_solution_list.view.txtCSol
import kotlinx.android.synthetic.main.row_solution_list.view.txtChosenAnswer
import kotlinx.android.synthetic.main.row_solution_list.view.txtDSol
import kotlinx.android.synthetic.main.row_solution_list.view.txtQuizTitleSol
import kotlinx.android.synthetic.main.row_solution_list.view.txtSelectedAnswer
import kotlinx.android.synthetic.main.row_solution_list.view.txtTotalQuestionsSol
import kotlinx.android.synthetic.main.row_solution_list.view.wrongASol
import kotlinx.android.synthetic.main.row_solution_list.view.wrongBSol
import kotlinx.android.synthetic.main.row_solution_list.view.wrongCSol
import kotlinx.android.synthetic.main.row_solution_list.view.wrongDSol
import java.util.Objects

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
        val itemView: View = mLayoutInflater.inflate(R.layout.row_solution_list, container, false)

        displayQuestion(position, itemView)

        val model = arrayList.get(position)

        itemView.txtQuizTitleSol.text = categoryType



        Handler(Looper.getMainLooper()).postDelayed({
            itemView.txtTotalQuestionsSol.text = "${position+1}/${arrayList.size}"
        }, 100)


        itemView.txtChosenAnswer.text =  "Selected Answer : "+model.selectedOptions
        itemView.txtSelectedAnswer.text = "Correct Answer : " +model.answer
        resultSelectedUI(itemView, model)


        itemView.btnNextSol.setOnClickListener {
            (context as ViewSolutionActivity).clickOnBtnNext()
        }
        itemView.btnPrevResult.setOnClickListener {
            (context as ViewSolutionActivity).clickOnBtnPrev()
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


            itemView.quizQuestionSol.text = "${questionData.question}"
            itemView.txtAnswerASol.text = questionData.option_A
            itemView.txtAnswerBSol.text = questionData.option_B
            itemView.txtAnswerCSol.text = questionData.option_C
            itemView.txtAnswerDSol.text = questionData.option_D

            // Reset the answer option views to their default state
            itemView.cvASol.background = ContextCompat.getDrawable(context, R.drawable.bg_answer_quiz)
            itemView.cvBSol.background = ContextCompat.getDrawable(context, R.drawable.bg_answer_quiz)
            itemView.cvCSol.background = ContextCompat.getDrawable(context, R.drawable.bg_answer_quiz)
            itemView.cvDSol.background = ContextCompat.getDrawable(context, R.drawable.bg_answer_quiz)

            itemView.cvASol.isEnabled = true
            itemView.cvBSol.isEnabled = true
            itemView.cvCSol.isEnabled = true
            itemView.cvDSol.isEnabled = true

            val sharedPreferences = context.getSharedPreferences("quiz", Context.MODE_PRIVATE)
            val correct = sharedPreferences.getInt("correctAnswerCount", 0)
            val incorrect = sharedPreferences.getInt("incorrectAnswerCount", 0)

        }

    }


    private fun resultSelectedUI(itemView: View, model: ResultShowData?) {
        val selectedAnswer = model?.selectedAnswer
        val correctAnswer = model?.answer

        // Reset the background color of all answer options
        itemView.llSolutionsSol.visibility = View.VISIBLE
        itemView.cvASol.background = ContextCompat.getDrawable(context, R.drawable.bg_answer_quiz)
        itemView.cvBSol.background = ContextCompat.getDrawable(context, R.drawable.bg_answer_quiz)
        itemView.cvCSol.background = ContextCompat.getDrawable(context, R.drawable.bg_answer_quiz)
        itemView.cvDSol.background = ContextCompat.getDrawable(context, R.drawable.bg_answer_quiz)

        // Change the background color of the selected answer option if given answer is true and matches the correct answer
        if (model != null) {

            Log.e("updateOptionSelectedUI", " Solutions: ${model.Solutions}")
            if (model.Solutions != null) {
                var isSolutionVisible = false

                // Set initial visibility of buttons
                itemView.seeSolutionButtonSol.visibility = View.VISIBLE
                itemView.hideSolutionButtonSol.visibility = View.GONE

                itemView.seeSolutionButtonSol.setOnClickListener {
                    if (!isSolutionVisible) {
                        isSolutionVisible = true
                        // Show the solution
                        if (model.Solutions == "") {
                            itemView.solutionTextViewSol.visibility = View.VISIBLE
                            itemView.solutionTextViewSol.text = "Solution will be available soon"
                            itemView.seeSolutionButtonSol.visibility = View.GONE
                            itemView.hideSolutionButtonSol.visibility = View.VISIBLE
                        } else {
                            itemView.solutionTextViewSol.text = model.Solutions
                            itemView.solutionTextViewSol.visibility = View.VISIBLE
                            itemView.seeSolutionButtonSol.visibility = View.GONE
                            itemView.hideSolutionButtonSol.visibility = View.VISIBLE
                        }

                    }
                }

                itemView.hideSolutionButtonSol.setOnClickListener {
                    if (isSolutionVisible) {
                        isSolutionVisible = false
                        // Hide the solution
                        itemView.solutionTextViewSol.visibility = View.GONE
                        itemView.seeSolutionButtonSol.visibility = View.VISIBLE
                        itemView.hideSolutionButtonSol.visibility = View.GONE
                    }
                }
            } else {
                itemView.seeSolutionButtonSol.text = "UpComing Solutions"
                itemView.seeSolutionButtonSol.visibility = View.VISIBLE
            }

            Log.e("resultSelectedUI", "answer ==>${model.answer} selectedAnswer==> ${model.selectedAnswer}")
            Log.e("resultSelectedUI", "answer ==>${model.answer} selectedOptions==> ${model.selectedOptions}")
            when (model.optionsSelected) {

                "A" -> {
                    if (model.answer == model.selectedOptions) {

                        itemView.cvASol.background = ContextCompat.getDrawable(
                            context,
                            R.drawable.bg_selected_answer
                        )
                    } else {
                        itemView.cvASol.background = ContextCompat.getDrawable(
                            context,
                            R.drawable.bg_wrong_answer
                        )
                        itemView.wrongASol.visibility = View.VISIBLE
                        itemView.txtASol.visibility = View.GONE

                        Log.e("resultSelectedUI", "answer ==>${model.answer} option_A==> ${model.option_A}")
                        if (model.answer == model.option_A){
                            itemView.cvASol.background = ContextCompat.getDrawable(
                                context,
                                R.drawable.bg_selected_answer
                            )
                        }
                    }
                }

                "B" -> {
                    if (model.answer == model.selectedOptions) {
                        itemView.cvBSol.background = ContextCompat.getDrawable(
                            context,
                            R.drawable.bg_selected_answer
                        )
                    } else {
                        itemView.cvBSol.background = ContextCompat.getDrawable(
                            context,
                            R.drawable.bg_wrong_answer
                        )
                        itemView.wrongBSol.visibility = View.VISIBLE
                        itemView.txtBSol.visibility = View.GONE
                        Log.e("resultSelectedUI", "answer ==>${model.answer} option_B==> ${model.option_B}")
                        if (model.answer == model.option_B){
                            itemView.cvASol.background = ContextCompat.getDrawable(
                                context,
                                R.drawable.bg_selected_answer
                            )
                        }
                    }
                }

                "C" -> {
                    if (model.answer == model.selectedOptions) {
                        itemView.cvCSol.background = ContextCompat.getDrawable(
                            context,
                            R.drawable.bg_selected_answer
                        )
                    } else {
                        itemView.cvCSol.background = ContextCompat.getDrawable(
                            context,
                            R.drawable.bg_wrong_answer
                        )
                        itemView.wrongCSol.visibility = View.VISIBLE
                        itemView.txtCSol.visibility = View.GONE

                        Log.e("resultSelectedUI", "answer ==>${model.answer} option_C==> ${model.option_C}")
                        if (model.answer ==model.option_C){
                            itemView.cvCSol.background = ContextCompat.getDrawable(
                                context,
                                R.drawable.bg_selected_answer
                            )
                        }
                    }
                }

                "D" -> {
                    if (model.answer == model.selectedOptions) {
                        itemView.cvDSol.background = ContextCompat.getDrawable(
                            context,
                            R.drawable.bg_selected_answer
                        )
                    } else {
                        itemView.cvDSol.background = ContextCompat.getDrawable(
                            context,
                            R.drawable.bg_wrong_answer
                        )
                        itemView.wrongDSol.visibility = View.VISIBLE
                        itemView.txtDSol.visibility = View.GONE
                        Log.e("resultSelectedUI", "answer ==>${model.answer} option_D==> ${model.option_D}")
                        if (model.answer == model.option_D){
                            itemView.cvDSol.background = ContextCompat.getDrawable(
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

