package com.sscenglishquiz.model

class QuestionData(
    val question_count: String?=null,
    val question: String?=null,
    val answer: String?=null,
    val option_A: String?=null,
    val option_B: String?=null,
    val option_C: String?=null,
    val option_D: String?=null
) {
    override fun toString(): String {
        return "Question $question_count: $question\n" +
                "A. $option_A\n" +
                "B. $option_B\n" +
                "C. $option_C\n" +
                "D. $option_D\n" +
                "Answer: $answer"
    }
//    constructor() : this(null, null, null, null, null, null,null)
}
