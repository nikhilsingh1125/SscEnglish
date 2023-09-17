package com.sscenglishpractice.model

import com.sscenglishquiz.model.QuestionData

data class BookmarkedModel(
    val name: String, val questions: ArrayList<QuestionData>)

