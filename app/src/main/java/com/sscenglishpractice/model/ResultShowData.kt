package com.sscenglishquiz.model

class ResultShowData(
    var question_count: String?=null,
    var question: String?=null,
    var answer: String?=null,
    var option_A: String?=null,
    var option_B: String?=null,
    var option_C: String?=null,
    var option_D: String?=null,
    var Solutions : String?=null,
    var selectedAnswer : String?=null,
    var isGivenAnswer : Boolean = false,
    var optionsSelected : String? =null,
    var testCategory : String? =null,
    var selectedOptions : String? =null
) {
    override fun toString(): String {
        return "ResultShowData(question_count=$question_count, question=$question, answer=$answer, option_A=$option_A, option_B=$option_B, option_C=$option_C, option_D=$option_D, Solutions=$Solutions, selectedAnswer=$selectedAnswer, isGivenAnswer=$isGivenAnswer, optionsSelected=$optionsSelected, testCategory=$testCategory,selectedOptions=$selectedOptions)"
    }
    constructor() : this(null, null, null, null, null, null, null, null, null,false,null,null)
    }
//    constructor() : this(null, null, null, null, null, null,null) }
