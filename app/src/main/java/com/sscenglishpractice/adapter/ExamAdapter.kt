package com.sscenglishpractice.adapter

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import com.sscenglishpractice.ExamQuizsActivity
import com.sscenglishpractice.R
import com.sscenglishpractice.model.Question
import com.sscenglishpractice.model.QuizQuestion
import kotlinx.android.synthetic.main.row_exam_quizes.view.btnNextExam
import kotlinx.android.synthetic.main.row_exam_quizes.view.btnPrev
import kotlinx.android.synthetic.main.row_exam_quizes.view.cvAExam
import kotlinx.android.synthetic.main.row_exam_quizes.view.cvBExam
import kotlinx.android.synthetic.main.row_exam_quizes.view.cvCExam
import kotlinx.android.synthetic.main.row_exam_quizes.view.cvDExam
import kotlinx.android.synthetic.main.row_exam_quizes.view.imgAExam
import kotlinx.android.synthetic.main.row_exam_quizes.view.imgBExam
import kotlinx.android.synthetic.main.row_exam_quizes.view.imgCExam
import kotlinx.android.synthetic.main.row_exam_quizes.view.imgDExam
import kotlinx.android.synthetic.main.row_exam_quizes.view.quizQuestionExam
import kotlinx.android.synthetic.main.row_exam_quizes.view.txtAnswerBExam
import kotlinx.android.synthetic.main.row_exam_quizes.view.txtAnswerCExam
import kotlinx.android.synthetic.main.row_exam_quizes.view.txtAnswerDExam
import kotlinx.android.synthetic.main.row_exam_quizes.view.txtAnswerExam
import kotlinx.android.synthetic.main.row_exam_quizes.view.txtPassage
import kotlinx.android.synthetic.main.row_exam_quizes.view.txtTotalQuestions
import kotlinx.android.synthetic.main.row_exam_quizes.view.txtTypeQuestion
import kotlinx.android.synthetic.main.row_exam_quizes.view.wrongAExam
import kotlinx.android.synthetic.main.row_exam_quizes.view.wrongBExam
import kotlinx.android.synthetic.main.row_exam_quizes.view.wrongCExam
import kotlinx.android.synthetic.main.row_exam_quizes.view.wrongDExam
import java.util.Objects

class ExamAdapter(
    val context: Context,
    val arrayList: ArrayList<Question>,
    val isResult: Boolean?
) : PagerAdapter() {

    var correctAns = 0
    var wrongAns = 0

    private val selectedAnswers =
        HashMap<Int, String?>() // Store selected answers for each question position

    private val selectedOptionsAnswers =
        HashMap<Int, String?>()

    override fun getCount(): Int {
        return arrayList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val mLayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView: View =
            mLayoutInflater.inflate(R.layout.row_exam_quizes, container, false)

        val model = arrayList[position]

        if (isResult == true){
            resultUi(model, itemView, position)
        }
        else{
            initUi(model, itemView, position)
            handleClicks(itemView, model, position)
        }


        Handler(Looper.getMainLooper()).postDelayed({
            itemView.txtTotalQuestions.text = "${position + 1}/${arrayList.size}"
        }, 100)

        itemView.btnPrev.setOnClickListener {
            (context as ExamQuizsActivity).onPrevClick(position)
        }
        itemView.btnNextExam.setOnClickListener {
            (context as ExamQuizsActivity).onNextClick(position)
        }

        Objects.requireNonNull(container).addView(itemView)

        return itemView
    }

    private fun resultUi(model: Question, itemView: View, position: Int) {

        itemView.cvAExam.isEnabled = false
        itemView.cvBExam.isEnabled = false
        itemView.cvCExam.isEnabled = false
        itemView.cvDExam.isEnabled = false

        if (model.Type_of_question == "") {
            itemView.txtTypeQuestion.visibility = View.GONE
        } else {
            itemView.txtTypeQuestion.visibility = View.VISIBLE
            itemView.txtTypeQuestion.text = model.Type_of_question
        }

        if (model.Passage == "") {
            itemView.txtPassage.visibility = View.GONE
        } else {
            itemView.txtPassage.visibility = View.VISIBLE
            itemView.txtPassage.text = model.Passage
        }


        itemView.quizQuestionExam.text = "${model.question}"
        itemView.txtAnswerExam.text = model.option_A
        itemView.txtAnswerBExam.text = model.option_B
        itemView.txtAnswerCExam.text = model.option_C
        itemView.txtAnswerDExam.text = model.option_D
        Log.e("selectedAnswer", "userSelectAnswer: ${model.userSelectAnswer} , answer: ${model.answer}")



        correctOrIncorrectAnswer(model,itemView)
    }

    private fun correctOrIncorrectAnswer(model: Question, itemView: View) {


        // Assuming these properties exist in the model class
        val isCorrectAnswer = (model.answer == model.userSelectAnswer)

        Log.e("Answers", "${model.question_count} question: ${model.answer}")
        Log.e("Answers", "${model.question_count} question: ${model.userSelectAnswer}")

        model.isWrongAnswer =  (model.userSelectAnswer != model.answer)
        if (isCorrectAnswer) {
            // The user selected the correct answer
            when (model.userSelectAnswer) {
                model.option_A -> {
                    // Show that option A is selected as the correct answer
                    itemView.cvAExam.background = ContextCompat.getDrawable(
                        context,
                        R.drawable.bg_selected_answer
                    )
                    itemView.imgAExam.visibility = View.VISIBLE
                }
                model.option_B -> {
                    // Show that option B is selected as the correct answer
                    itemView.cvBExam.background = ContextCompat.getDrawable(
                        context,
                        R.drawable.bg_selected_answer
                    )
                    itemView.imgBExam.visibility = View.VISIBLE
                }
                model.option_C -> {
                    itemView.cvCExam.background = ContextCompat.getDrawable(
                        context,
                        R.drawable.bg_selected_answer
                    )
                    itemView.imgCExam.visibility = View.VISIBLE
                }
                model.option_D -> {
                    itemView.cvDExam.background = ContextCompat.getDrawable(
                        context,
                        R.drawable.bg_selected_answer
                    )
                    itemView.imgDExam.visibility = View.VISIBLE
                }
                // Handle the other options in a similar way
                // ...
            }
        }
        else {
            // The user selected the wrong answer
            when {
                model.userSelectAnswer == model.option_A -> {
                    // Show that option A is selected as the wrong answer
                    itemView.cvAExam.background = ContextCompat.getDrawable(
                        context,
                        R.drawable.bg_wrong_answer
                    )
                    itemView.wrongAExam.visibility = View.VISIBLE
                }
                model.userSelectAnswer == model.option_B -> {
                    // Show that option B is selected as the wrong answer
                    itemView.cvBExam.background = ContextCompat.getDrawable(
                        context,
                        R.drawable.bg_wrong_answer
                    )
                    itemView.wrongBExam.visibility = View.VISIBLE
                }
                model.userSelectAnswer == model.option_C -> {
                    itemView.cvCExam.background = ContextCompat.getDrawable(
                        context,
                        R.drawable.bg_wrong_answer
                    )
                    itemView.wrongCExam.visibility = View.VISIBLE
                }
                model.userSelectAnswer == model.option_D -> {
                    itemView.cvDExam.background = ContextCompat.getDrawable(
                        context,
                        R.drawable.bg_wrong_answer
                    )
                    itemView.wrongDExam.visibility = View.VISIBLE
                }
                // Handle the other options in a similar way
                // ...
            }

            if (model.userSelectAnswer!="") {
                when (model.answer) {
                    model.option_A -> {
                        // Show that option A is the correct answer
                        itemView.cvAExam.background = ContextCompat.getDrawable(
                            context,
                            R.drawable.bg_selected_answer
                        )
                        itemView.imgAExam.visibility = View.VISIBLE
                    }

                    model.option_B -> {
                        // Show that option B is the correct answer
                        itemView.cvBExam.background = ContextCompat.getDrawable(
                            context,
                            R.drawable.bg_selected_answer
                        )
                        itemView.imgBExam.visibility = View.VISIBLE
                    }

                    model.option_C -> {
                        itemView.cvCExam.background = ContextCompat.getDrawable(
                            context,
                            R.drawable.bg_selected_answer
                        )
                        itemView.imgCExam.visibility = View.VISIBLE
                    }

                    model.option_D -> {
                        itemView.cvDExam.background = ContextCompat.getDrawable(
                            context,
                            R.drawable.bg_selected_answer
                        )
                        itemView.imgDExam.visibility = View.VISIBLE
                    }

                }
            }
        }




    }

    private fun handleClicks(itemView: View, model: Question, position: Int) {

        itemView.cvAExam.setOnClickListener {
            selectOption(itemView, model, position, "A",model.option_A)
        }
        itemView.cvBExam.setOnClickListener {
            selectOption(itemView, model, position, "B", model.option_B)
        }
        itemView.cvCExam.setOnClickListener {
            selectOption(itemView, model, position, "C", model.option_C)
        }
        itemView.cvDExam.setOnClickListener {
            selectOption(itemView, model, position, "D", model.option_D)
        }
    }

    private fun selectOption(
        itemView: View,
        model: Question,
        position: Int,
        selectedOption: String,
        options: String?
    ) {
        model.isSelectedAnswer = true
        model.isGivenAnswer = true
        Log.e("updateOptionSelectedUI", "isGivenAnswer: ${model.isGivenAnswer}")

        selectedAnswers[position] = selectedOption
        selectedOptionsAnswers[position] = options
        updateOptionSelectedUI(itemView, model, selectedOption,options)
    }

    private fun initUi(model: Question, itemView: View, position: Int) {

        if (model.Type_of_question == "") {
            itemView.txtTypeQuestion.visibility = View.GONE
        } else {
            itemView.txtTypeQuestion.visibility = View.VISIBLE
            itemView.txtTypeQuestion.text = model.Type_of_question
        }

        if (model.Passage == "") {
            itemView.txtPassage.visibility = View.GONE
        } else {
            itemView.txtPassage.visibility = View.VISIBLE
            itemView.txtPassage.text = model.Passage
        }


        itemView.quizQuestionExam.text = "${model.question}"
        itemView.txtAnswerExam.text = model.option_A
        itemView.txtAnswerBExam.text = model.option_B
        itemView.txtAnswerCExam.text = model.option_C
        itemView.txtAnswerDExam.text = model.option_D

        // Set the background for the selected answer (if any)
        val selectedOption = selectedAnswers[position]
        val options = selectedOptionsAnswers[position]
        selectedOption?.let { updateOptionSelectedUI(itemView, model, it, options) }
    }

    private fun updateOptionSelectedUI(
        itemView: View,
        model: Question,
        selectedOption: String,
        options: String?
    ) {

        Log.e("OptionSelected", "correctAns: $correctAns")
        Log.e("OptionSelected", "correctAns: $correctAns")

        if (options == model.answer) {
            correctAns++
            model.correctSelectAnswer = correctAns
            Log.e("SelectExam", "correctAns: $correctAns")
        }
        else {
            wrongAns++
            model.incorrectSelectAnswer = wrongAns
            Log.e("SelectExam", "wrongAns: $wrongAns")
        }

        itemView.cvAExam.background =
            if (selectedOption == "A") {
                model.userSelectAnswer  = model.option_A
                ContextCompat.getDrawable(
                    context,
                    R.drawable.bg_selected_answer
                )
            }
            else ContextCompat.getDrawable(context, R.drawable.bg_answer_quiz)
        itemView.cvBExam.background =
            if (selectedOption == "B") {
                model.userSelectAnswer  = model.option_B
                ContextCompat.getDrawable(
                    context,
                    R.drawable.bg_selected_answer
                )
            }
            else ContextCompat.getDrawable(context, R.drawable.bg_answer_quiz)
        itemView.cvCExam.background =
            if (selectedOption == "C") {
                model.userSelectAnswer  = model.option_C
                ContextCompat.getDrawable(
                    context,
                    R.drawable.bg_selected_answer
                )
            }
            else ContextCompat.getDrawable(context, R.drawable.bg_answer_quiz)
        itemView.cvDExam.background =
            if (selectedOption == "D") {
                model.userSelectAnswer  = model.option_D
                ContextCompat.getDrawable(
                    context,
                    R.drawable.bg_selected_answer
                )
            }
            else ContextCompat.getDrawable(context, R.drawable.bg_answer_quiz)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }


}
