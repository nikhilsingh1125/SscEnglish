package com.sscenglishquiz.model

class QuestionData(
    var question_count: String?=null,
    var question: String?=null,
    var answer: String?=null,
    var option_A: String?=null,
    var option_B: String?=null,
    var option_C: String?=null,
    var option_D: String?=null,
    var Solutions : String?=null,
    var isGivenAnswer : Boolean = false,
    var optionsSelected : String? =null,
    var selectedOptionsAnswer : String? = null,
    var testCategory : String? =null,
    var isBookmark : Boolean = false,
) {
    override fun toString(): String {
        return "Question $question_count: $question\n" +
                "A. $option_A\n" +
                "B. $option_B\n" +
                "C. $option_C\n" +
                "D. $option_D\n" +
                "Answer: $answer" +
                "solutions :$Solutions"
    }
//    constructor() : this(null, null, null, null, null, null,null)
}
