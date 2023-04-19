package com.sscenglishquiz.model

class QuestionWiseModel(
    val Test_Title: String?=null,
    val Description:String?=null,
    val Questions :ArrayList<QuestionData>? = null
    ) {
    constructor() : this(null, null, null)
}