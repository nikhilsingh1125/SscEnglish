package com.sscenglishpractice.model


data class ResultShowData(
    val Questions: List<QuestionRes> = emptyList()
)

data class QuestionRes(
    val answer: String = "",
    val bookmark: Boolean = false,
    val givenAnswer: Boolean = false,
    val option_A: String = "",
    val option_B: String = "",
    val option_C: String = "",
    val option_D: String = "",
    val optionsSelected: String = "",
    val question: String = "",
    val question_count: String = "",
    val selectedAnswer: Boolean = false,
    val selectedOptionsAnswer: String = "",
    val skipAnswer: Boolean = false,
    val solutions: String = "",
    val wrongAnswer: Boolean = false
)
