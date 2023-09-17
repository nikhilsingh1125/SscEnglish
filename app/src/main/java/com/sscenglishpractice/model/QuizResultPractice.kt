package com.sscenglishpractice.model

import com.sscenglishquiz.model.QuestionData

data class QuizResultPractice(
    val questions: List<QuestionData> = emptyList()
)
