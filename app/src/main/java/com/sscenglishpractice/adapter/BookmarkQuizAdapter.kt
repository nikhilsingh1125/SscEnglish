package com.sscenglishpractice.adapter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import com.google.firebase.database.FirebaseDatabase
import com.sscenglishpractice.BookmarkQuizActivity
import com.sscenglishpractice.R
import com.sscenglishquiz.model.QuestionData
import com.sscenglishpractice.model.ResultShowData
import kotlinx.android.synthetic.main.custom_toolbar.view.btnSubmit
import kotlinx.android.synthetic.main.row_question_list.view.*
import java.util.*
import kotlin.collections.ArrayList

class BookmarkQuizAdapter(
    val context: Context,
    val arrayList: ArrayList<QuestionData>,
    val title: String,
    val category: String,
    val type: String
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
    val bookmarkStates: MutableMap<String, Boolean> = mutableMapOf()
    private val sharedPrefs: SharedPreferences = context.getSharedPreferences(
        "bookmark_prefs",
        Context.MODE_PRIVATE
    )

    private val selectedAnswers =
        HashMap<Int, String?>() // Store selected answers for each question position

    private val selectedOptionsAnswers =
        HashMap<Int, String?>()

    override fun getCount(): Int {
        return arrayList.size
    }

    // on below line we are returning the object
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as LinearLayout
    }

    // on below line we are initializing
    // our item and inflating our layout file
    @SuppressLint("HardwareIds")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        // on below line we are initializing
        // our layout inflater.
        val mLayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        // on below line we are inflating our custom
        // layout file which we have created.
        val itemView: View = mLayoutInflater.inflate(R.layout.row_question_list, container, false)

        displayQuestion(position, itemView)


        val model = arrayList[position]

        itemView.txtQuizTitle.text = title

        Handler(Looper.getMainLooper()).postDelayed({
            itemView.txtTotalQuestions.text =  "${position + 1}/${arrayList.size}"
        }, 100)

        val selectedOption = selectedAnswers[position]
        val options = selectedOptionsAnswers[position]

        if (model.Type_of_question.isNullOrBlank()){
            itemView.txtTypeQuestion.visibility = View.GONE
        }
        else{
            itemView.txtTypeQuestion.visibility = View.VISIBLE
            itemView.txtTypeQuestion.text = model.Type_of_question
        }

        if (selectedOption != null) {
            updateOptionSelectedUI(itemView, model, selectedOption, options)
        }
        handleClicks(itemView, model, position)

        model.isSkipAnswer = !(model.isSelectedAnswer && model.isGivenAnswer)


//        val questionIdentifier = model.generateIdentifier()

        itemView.btnSubmit.visibility = View.GONE


        if (model.isBookmark) {
            itemView.imgBookmarkFill.visibility = View.VISIBLE
            itemView.imgBookmarkUnfill.visibility = View.GONE
        }
        Log.e("BookmarkQuizAdapter", "instantiateItem: ${model.questionIdentifier}")
        Log.e("BookmarkQuizAdapter", "isBookmark: ${model.isBookmark}")
        Log.e("BookmarkQuizAdapter", "model ==>: ${model}")

       /* itemView.imgBookmarkUnfill.setOnClickListener {
            if (!model.isBookmark) {
                itemView.imgBookmarkFill.visibility = View.VISIBLE
                itemView.imgBookmarkUnfill.visibility = View.GONE

                saveBookmarkedQuestions(model)
            }
        }*/
        val deviceId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        Log.e("BookMarkActivity", "deviceId ==> : $deviceId")
        itemView.imgBookmarkFill.setOnClickListener {
            if (model.isBookmark) {
                itemView.imgBookmarkFill.visibility = View.GONE
                itemView.imgBookmarkUnfill.visibility = View.VISIBLE
                model.isBookmark = false
                removeBookmarkForQuestion(model, title,deviceId)
            }
        }


        Log.e("GenerateIdentifier", " isSkipAnswer :${model.isBookmark} ")

        // on the below line we are adding this
        // item view to the container.
        Objects.requireNonNull(container).addView(itemView)

        // on below line we are simply
        // returning our item view.
        return itemView
    }

    private fun handleClicks(itemView: View, model: QuestionData, position: Int) {
        itemView.cvA.setOnClickListener {
            selectOption(itemView, model, position, "A", model.option_A)
        }

        itemView.cvB.setOnClickListener {
            selectOption(itemView, model, position, "B", model.option_B)
        }

        itemView.cvC.setOnClickListener {
            selectOption(itemView, model, position, "C", model.option_C)
        }

        itemView.cvD.setOnClickListener {
            selectOption(itemView, model, position, "D", model.option_D)
        }

        itemView.btnNext.setOnClickListener {
            (context as BookmarkQuizActivity).clickOnBtnNextBook()
            //  itemView.btnNext.setBackgroundColor(ContextCompat.getColor(context, R.color.green))
        }

        itemView.btnPrevQuiz.setOnClickListener {
            (context as BookmarkQuizActivity).onPrevClickBook()
        }


    }


    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }

    private fun selectOption(
        itemView: View,
        model: QuestionData,
        position: Int,
        selectedOption: String,
        options: String?
    ) {
        model.isSelectedAnswer = true
        model.isGivenAnswer = true
        Log.e("updateOptionSelectedUI", "isGivenAnswer: ${model.isGivenAnswer}")

        selectedAnswers[position] = selectedOption
        selectedOptionsAnswers[position] = options
        updateOptionSelectedUI(itemView, model, selectedOption, options)
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

    private fun updateOptionSelectedUI(
        itemView: View,
        model: QuestionData?,
        selectedOption: String,
        options: String?
    ) {
        if (model != null) {
            model.selectedOptionsAnswer = options
            model.optionsSelected = selectedOption
        }
        Log.e("OptionSelected", "selectedOption ==>: $selectedOption")
        Log.e("OptionSelected", "options==>: $options")

        itemView.llSolutions.visibility = View.VISIBLE
        itemView.cvA.background = ContextCompat.getDrawable(context, R.drawable.bg_answer_quiz)
        itemView.cvB.background = ContextCompat.getDrawable(context, R.drawable.bg_answer_quiz)
        itemView.cvC.background = ContextCompat.getDrawable(context, R.drawable.bg_answer_quiz)
        itemView.cvD.background = ContextCompat.getDrawable(context, R.drawable.bg_answer_quiz)

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

        if (options != null) {

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
            if (options == model?.answer) {
                Log.e("OptionSelected", "selectedAnswer: $selectedAnswer , answer: ${model.answer}")
                // If the question hasn't been answered correctly before, increment count and set flag to true
                correctAnswerCount++
                // Set background color of selected option to green
                when (options) {
                    model.option_A -> {
                        itemView.cvA.background = ContextCompat.getDrawable(
                            context,
                            R.drawable.bg_selected_answer
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

            } else {
                // Set background color of selected option to red
                if (model != null) {

                    incorrectAnswerCount++
                    when (options) {
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


    fun removeBookmarkForQuestion(
        question: QuestionData,
        category: String,
        deviceId: String,
    ) {
        val firebaseDatabase = FirebaseDatabase.getInstance()
        val databaseReference = firebaseDatabase.reference.child("bookmarked_questions").child(deviceId)

        val questionIdentifier = question.questionIdentifier

        Log.e("RemoveBookmark", "questionIdentifier ==> :$questionIdentifier ")

        // Remove the question using the unique identifier
        if (questionIdentifier != null) {
            databaseReference.child("categories").child(category).child(questionIdentifier)
                .removeValue()
                .addOnSuccessListener {
                    Toast.makeText(context, "Remove Bookmark", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->

                }
        }
    }


}

