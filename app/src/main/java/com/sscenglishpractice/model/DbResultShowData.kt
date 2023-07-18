package com.sscenglishquiz.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "result_show_data_table")
data class DbResultShowData(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var question_count: String = "",
    var question: String = "",
    var answer: String = "",
    var option_A: String = "",
    var option_B: String = "",
    var option_C: String = "",
    var option_D: String = "",
    var Solutions: String = "",
    var selectedAnswer: String = "",
    var isGivenAnswer: Boolean = false,
    var optionsSelected: String = "",
    var testCategory : String ="",
    var selectedOptions: String = ""
)
