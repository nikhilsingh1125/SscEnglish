package com.sscenglishpractice.model

import android.os.Parcel
import android.os.Parcelable

data class QuizQuestion(
    var Solutions: String? = null,
    var Type_of_question: String? = null,
    var answer: String? = null,
    var option_A: String? = null,
    var option_B: String? = null,
    var option_C: String? = null,
    var option_D: String? = null,
    var Passage: String? = null,
    var question: String? = null,
    var question_count: String? = null,
    var isSelectedAnswer: Boolean = false,
    var isGivenAnswer: Boolean = false,
    var isVisited: Boolean = false,
    var isWrongAnswer: Boolean = false,
    var userSelectAnswer: String? = null,
    var correctSelectAnswer: Int =0,
    var incorrectSelectAnswer: Int =0,
)
