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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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

        Log.e("instantiateItem", " Type_of_question===> : ${model.Type_of_question}", )
        itemView.txtQuizTitle.text = title

        Handler(Looper.getMainLooper()).postDelayed({
            itemView.txtTotalQuestions.text = "${model.question_count}/${arrayList.size}"
        }, 100)

        val selectedOption = selectedAnswers[position]
        val options = selectedOptionsAnswers[position]

        if (selectedOption != null) {
            updateOptionSelectedUI(itemView,model,selectedOption,options)
        }
        handleClicks(itemView, model, position)

        model.isSkipAnswer = !(model.isSelectedAnswer && model.isGivenAnswer)

        if (model.Type_of_question.isNullOrBlank()){
            itemView.txtTypeQuestion.visibility = View.GONE
        }
        else{
            itemView.txtTypeQuestion.visibility = View.VISIBLE
            itemView.txtTypeQuestion.text = model.Type_of_question
        }

//        val questionIdentifier = model.generateIdentifier()

        val questionIdentifier = model.generateIdentifier()
        val isBookmarked = sharedPrefs.getBoolean(questionIdentifier, false)

        Log.e("Answers", "questionIdentifier ==> :${model.questionIdentifier} ")
        Log.e("Answers", "isBookmarked ==> :${isBookmarked} ")
        Log.e("Answers", "model.isBookmark ==> :${model.isBookmark} ")
        Log.e("Answers", "model.questionIdentifier ==> :${model.questionIdentifier} ")
        // Update bookmark UI based on the loaded state
        if (model.isBookmark) {
            itemView.imgBookmarkFill.visibility = View.VISIBLE
            itemView.imgBookmarkUnfill.visibility = View.GONE
        } else {
            itemView.imgBookmarkFill.visibility = View.GONE
            itemView.imgBookmarkUnfill.visibility = View.VISIBLE
        }

        val deviceId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        Log.e("ViewPager", "deviceId ==> : $deviceId")
        itemView.imgBookmarkUnfill.setOnClickListener {
            if (!model.isBookmark) {
                itemView.imgBookmarkFill.visibility = View.VISIBLE
                itemView.imgBookmarkUnfill.visibility = View.GONE
                model.isBookmark = true

                // Update bookmark state in SharedPreferences
                sharedPrefs.edit().putBoolean(questionIdentifier, true).apply()

                saveBookmarkedQuestions(model,deviceId)
            }
        }

        itemView.imgBookmarkFill.setOnClickListener {
            if (model.isBookmark) {
                itemView.imgBookmarkFill.visibility = View.GONE
                itemView.imgBookmarkUnfill.visibility = View.VISIBLE
                model.isBookmark = false

                // Update bookmark state in SharedPreferences
                sharedPrefs.edit().putBoolean(questionIdentifier, false).apply()

                // Call the removeBookmarkedQuestionFromDatabase function with the question's type, category, and identifier
                removeBookmarkForQuestion(model,category,deviceId)
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
            selectOption(itemView, model, position, "A",model.option_A)
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
            (context as QuizActivity).clickOnBtnNext(position)
            //  itemView.btnNext.setBackgroundColor(ContextCompat.getColor(context, R.color.green))
        }

        itemView.btnPrevQuiz.setOnClickListener {
            (context as QuizActivity).onPrevClick(position)
        }

        itemView.btnSubmit.setOnClickListener {


            val givenAnswer = arrayList.filter { it.isGivenAnswer}
            val correctAnswer = givenAnswer.filter { it.selectedOptionsAnswer == it.answer }
            val wrongAnswer = givenAnswer.filter { it.selectedOptionsAnswer != it.answer }

// Extract question_count values from answered questions
            val answeredQuestionIds = givenAnswer.map { it.question_count }.toSet()

// Find skipped questions
            val skippedQuestions = arrayList.filter { it.question_count !in answeredQuestionIds }

            Log.d("Answers", "givenAnswer ==>${givenAnswer.size}")
            Log.d("Answers", "correctAns ==>${correctAnswer.size}")
            Log.d("Answers", "wrongAnswer ==>${wrongAnswer.size}")
            Log.d("Answers", "questions ==>${arrayList.size}")
            Log.d("Answers", "skippedQuestions ==>${skippedQuestions.size}")

            skipedAnswer = arrayList.size - givenAnswerCount
            Log.e("instantiateItem", "skipedAnswer: $skipedAnswer")
            (context as QuizActivity).clickOnSubmitNext(
                title, category,arrayList
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
        updateOptionSelectedUI(itemView, model, selectedOption,options)
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



    fun getBookmarkedQuestions(): List<QuestionData> {
        return arrayList.filter { it.isBookmark }
    }

    private fun saveBookmarkedQuestionsToDatabase(
        bookmarkedQuestions: List<QuestionData>,
        category: String,
        model: QuestionData,
        deviceId: String // Pass the deviceId as a parameter
    ) {
        val firebaseDatabase = FirebaseDatabase.getInstance()
        // Create a reference using the deviceId to make it unique per device
        val databaseReference = firebaseDatabase.reference.child("bookmarked_questions").child(deviceId)

        // Create a set to keep track of saved questions and answers
        val savedQuestionsAndAnswers = mutableSetOf<Pair<String, String>>()

        // Retrieve existing questions and answers if any
        databaseReference.child("categories").child(category)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (childSnapshot in dataSnapshot.children) {
                        val question = childSnapshot.child("question").getValue(String::class.java)
                        val answer = childSnapshot.child("answer").getValue(String::class.java)
                        if (question != null && answer != null) {
                            savedQuestionsAndAnswers.add(Pair(question, answer))
                        }
                    }

                    // Now, you can save new questions if they are not already in the set
                    for (question in bookmarkedQuestions) {
                        val questionText = question.question
                        val answerText = question.answer

                        // Check if the question and answer pair already exists
                        val pair = Pair(questionText, answerText)
                        if (!savedQuestionsAndAnswers.contains(pair)) {
                            // Create data to be saved in the database

                            // Create a unique identifier for each question
                            val questionIdentifier =
                                databaseReference.child("categories").child(category).push().key.toString()
                            model.questionIdentifier = questionIdentifier

                            val dataToSave = mapOf(
                                "question" to questionText,
                                "answer" to answerText,
                                "questionIdentifier" to model.questionIdentifier,
                                "option_A" to question.option_A,
                                "option_B" to question.option_B,
                                "option_C" to question.option_C,
                                "option_D" to question.option_D,
                                "Solutions" to question.Solutions,
                                "isGivenAnswer" to question.isGivenAnswer,
                                "optionsSelected" to question.optionsSelected,
                                "selectedOptionsAnswer" to question.selectedOptionsAnswer,
                                "testCategory" to question.testCategory,
                                "isBookmark" to question.isBookmark,
                                "isSelectedAnswer" to question.isSelectedAnswer,
                                "isSkipAnswer" to question.isSkipAnswer,
                                "isWrongAnswer" to question.isWrongAnswer,
                                "questionIdentifier" to question.questionIdentifier
                            )

                            databaseReference.child("categories").child(category).child(questionIdentifier)
                                .setValue(dataToSave)
                                .addOnSuccessListener {
                                    // Data saved successfully
                                }
                                .addOnFailureListener { e ->
                                    // Handle any errors
                                }
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle database error
                }
            })
    }





    fun saveBookmarkedQuestions(model: QuestionData, deviceId: String) {
        val bookmarkedQuestions = getBookmarkedQuestions()
        saveBookmarkedQuestionsToDatabase(bookmarkedQuestions,category,model,deviceId)
    }

    fun removeBookmarkForQuestion(question: QuestionData, category: String, deviceId: String) {
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

