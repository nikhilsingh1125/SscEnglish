package com.sscenglishpractice.adapter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
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
import com.google.firebase.firestore.FirebaseFirestore
import com.sscenglishpractice.QuizActivity
import com.sscenglishpractice.R
import com.sscenglishpractice.utils.AppDatabase
import com.sscenglishquiz.model.DbResultShowData
import com.sscenglishquiz.model.QuestionData
import com.sscenglishpractice.model.ResultShowData
import kotlinx.android.synthetic.main.custom_toolbar.view.btnSubmit
import kotlinx.android.synthetic.main.row_question_list.view.*
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.collections.ArrayList

class ViewPagerAdapter(
    val context: Context,
    val arrayList: ArrayList<QuestionData>,
    val title: String,
    val category: String
) :
    PagerAdapter() {
    // on below line we are creating a method
    // as get count to return the size of the list.

    var selectedAnswer = ""
    private var optionASelected = false
    private var optionBSelected = false
    private var optionCSelected = false
    private var optionDSelected = false
    var correctAnswerCount = 0
    var questionCount = 0
    var incorrectAnswerCount = 0
    var bookmarkQuestion = false
    var givenAnswerCount = 0
    val resultDataList = mutableListOf<ResultShowData>()
    var skipedAnswer = 0

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

        itemView.txtQuizTitle.text = title



        Handler(Looper.getMainLooper()).postDelayed({
            itemView.txtTotalQuestions.text = "${model.question_count}/${arrayList.size}"
        }, 100)


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

            skipedAnswer = arrayList.size - givenAnswerCount
            Log.e("instantiateItem", "skipedAnswer: $skipedAnswer")
            (context as QuizActivity).clickOnSubmitNext(
                position,
                correctAnswerCount,
                incorrectAnswerCount,
                arrayList.size,
                givenAnswerCount,
                skipedAnswer,
                title, category,model,arrayList
            )
            val resultDataList = mutableListOf<DbResultShowData>()
            var serialNumber = 1
// Assume you have a list of questions called 'arrayList'
            for ((index, model) in arrayList.withIndex()) {
                if (model.isGivenAnswer) {
                    val userAnswer = when {
                        optionASelected -> model.option_A
                        optionBSelected -> model.option_B
                        optionCSelected -> model.option_C
                        optionDSelected -> model.option_D
                        else -> null
                    }

                    val resultShow = DbResultShowData()
                    resultShow.question_count = serialNumber.toString()
                    resultShow.question = model.question.toString()
                    resultShow.answer = model.answer.toString()
                    resultShow.option_A = model.option_A.toString()
                    resultShow.option_B = model.option_B.toString()
                    resultShow.option_C = model.option_C.toString()
                    resultShow.option_D = model.option_D.toString()
                    resultShow.Solutions = model.Solutions.toString()
                    resultShow.selectedAnswer = selectedAnswer
                    resultShow.isGivenAnswer = true
                    resultShow.optionsSelected = model.optionsSelected.toString()
                    resultShow.testCategory = category
                    resultShow.selectedOptions = model.selectedOptionsAnswer.toString()

                    resultDataList.add(resultShow)
                    val db = AppDatabase.getInstance(context)

                    val executor: ExecutorService = Executors.newSingleThreadExecutor()
                    executor.execute {
                        db.resultShowDataDao().insert(resultShow)
                    }
                    serialNumber++
                }


            }

            Log.e("instantiateItem", "resultDataList==>: $resultDataList", )
            val firestore = FirebaseFirestore.getInstance()
            val collectionRef = firestore.collection("your_collection_name")

// Assuming you have a list of ResultShowData objects called 'resultDataList'
            for (question in resultDataList) {
                val questionId = question.question_count ?: ""
                val category = category // Replace with the actual category name

                val documentRef =
                    collectionRef.document(category).collection("your_subcollection_name")
                        .document(questionId)
                documentRef.set(question)
                    .addOnSuccessListener {
                        // Data saved successfully for the current question
                        Log.d("SelectedUI", "Data saved successfully for question: $questionId")
                    }
                    .addOnFailureListener { e ->
                        // Handle any errors
                        Log.e(
                            "SelectedUI",
                            "Error saving data for question: $questionId - ${e.message}",
                            e
                        )
                    }
            }
        }

    /*    itemView.imgBookmarkUnfill.setOnClickListener {
            if (!model.isBookmark) {
                itemView.imgBookmarkFill.visibility = View.VISIBLE
                itemView.imgBookmarkUnfill.visibility = View.GONE // Hide the unfill bookmark icon
                model.isBookmark = true
            }
        }

        itemView.imgBookmarkFill.setOnClickListener {
            if (model.isBookmark) {
                itemView.imgBookmarkFill.visibility = View.GONE // Hide the fill bookmark icon
                itemView.imgBookmarkUnfill.visibility = View.VISIBLE
                model.isBookmark = false
            }
        }*/


        // on the below line we are adding this
        // item view to the container.
        Objects.requireNonNull(container).addView(itemView)

        // on below line we are simply
        // returning our item view.
        return itemView
    }


    private fun selectOptionA(itemView: View, model: QuestionData?) {

        model?.optionsSelected = "A"
        optionASelected = true
        optionBSelected = false
        optionCSelected = false
        optionDSelected = false

        if (model != null) {
            updateOptionSelectedUI(itemView, model)

            if (optionASelected){
                    model.selectedOptionsAnswer = model.option_A
                }
            }
    }

    private fun selectOptionB(itemView: View, model: QuestionData?) {
        model?.optionsSelected = "B"
        optionASelected = false
        optionBSelected = true
        optionCSelected = false
        optionDSelected = false
        if (model != null) {
            updateOptionSelectedUI(itemView, model)
            if (optionBSelected){
                model.selectedOptionsAnswer = model.option_B
            }
        }
    }

    private fun selectOptionC(itemView: View, model: QuestionData?) {
        model?.optionsSelected = "C"
        optionASelected = false
        optionBSelected = false
        optionCSelected = true
        optionDSelected = false
        if (model != null) {
            updateOptionSelectedUI(itemView, model)

            if (optionCSelected){
                model.selectedOptionsAnswer = model.option_C
            }
        }
    }

    private fun selectOptionD(itemView: View, model: QuestionData?) {
        model?.optionsSelected = "D"
        optionASelected = false
        optionBSelected = false
        optionCSelected = false
        optionDSelected = true
        updateOptionSelectedUI(itemView, model)
        if (optionDSelected){
            if (model != null) {
                model.selectedOptionsAnswer = model.option_D
            }
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }

    private fun displayQuestion(index: Int, itemView: View) {
        val questionData = arrayList?.get(index)

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

    private fun updateOptionSelectedUI(itemView: View, model: QuestionData?) {


        itemView.llSolutions.visibility = View.VISIBLE
        itemView.cvA.background = ContextCompat.getDrawable(context, R.drawable.bg_answer_quiz)
        itemView.cvB.background = ContextCompat.getDrawable(context, R.drawable.bg_answer_quiz)
        itemView.cvC.background = ContextCompat.getDrawable(context, R.drawable.bg_answer_quiz)
        itemView.cvD.background = ContextCompat.getDrawable(context, R.drawable.bg_answer_quiz)

        if (optionASelected) {
            itemView.cvA.background = ContextCompat.getDrawable(context, R.drawable.bg_answer)
        }
        if (optionBSelected) {
            itemView.cvB.background = ContextCompat.getDrawable(context, R.drawable.bg_answer)
        }
        if (optionCSelected) {
            itemView.cvC.background = ContextCompat.getDrawable(context, R.drawable.bg_answer)
        }
        if (optionDSelected) {
            itemView.cvD.background = ContextCompat.getDrawable(context, R.drawable.bg_answer)
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

            givenAnswerCount++

            if (model != null) {
                model.isGivenAnswer = true


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
                                itemView.solutionTextView.text = "Solution will be available soon"
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
            }

            questionCount++
            Log.e("updateOptionSelectedUI", "questionCount: $questionCount")
            if (selectedAnswer == model?.answer) {
                Log.e("OptionSelected", "selectedAnswer: $selectedAnswer , answer: ${model.answer}")
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

            } else {
                // Set background color of selected option to red
                if (model != null) {

                    incorrectAnswerCount++
                    when (selectedAnswer) {
                        model.option_A -> {
                            itemView.cvA.setBackground(
                                ContextCompat.getDrawable(
                                    context,
                                    R.drawable.bg_wrong_answer
                                )
                            )
                            shakeButton(itemView.cvA)
                        }

                        model.option_B -> {
                            itemView.cvB.setBackground(
                                ContextCompat.getDrawable(
                                    context,
                                    R.drawable.bg_wrong_answer
                                )
                            )
                            shakeButton(itemView.cvB)
                        }

                        model.option_C -> {
                            itemView.cvC.setBackground(
                                ContextCompat.getDrawable(
                                    context,
                                    R.drawable.bg_wrong_answer
                                )
                            )
                            shakeButton(itemView.cvC)
                        }

                        model.option_D -> {
                            itemView.cvD.setBackground(
                                ContextCompat.getDrawable(
                                    context,
                                    R.drawable.bg_wrong_answer
                                )
                            )
                            shakeButton(itemView.cvD)
                        }
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

    private fun shakeButton(view: View) {
        val animatorSet = AnimatorSet()

        // Define the shaking animation for the button
        val animation1 = ObjectAnimator.ofFloat(view, "translationX", -10f)
        val animation2 = ObjectAnimator.ofFloat(view, "translationX", 10f)

        // Configure the animation properties
        animation1.duration = 100
        animation1.repeatCount = 5
        animation1.repeatMode = ObjectAnimator.REVERSE

        animation2.duration = 100
        animation2.repeatCount = 5
        animation2.repeatMode = ObjectAnimator.REVERSE

        // Play the animations sequentially
        animatorSet.playSequentially(animation1, animation2)

        // Start the animation
        animatorSet.start()
    }



}

