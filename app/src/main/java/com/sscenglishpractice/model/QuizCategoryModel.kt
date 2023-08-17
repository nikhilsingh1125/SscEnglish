package com.sscenglishpractice.model
import android.os.Parcel
import android.os.Parcelable


data class QuizCategoryModel(
    val Description: String = "",
    val Questions: List<Question> = emptyList(),
    val Test_Title: String = "",
    val Time_taken: String = ""
)

data class Question(
    val Passage: String? = "",
    val Solutions: String? = "",
    val Type_of_question: String? = "",
    val answer: String? = "",
    val option_A: String? = "",
    val option_B: String? = "",
    val option_C: String? = "",
    val option_D: String? = "",
    val question: String? = "",
    val question_count: String? = "",
    var isSelectedAnswer: Boolean = false,
    var isGivenAnswer: Boolean = false,
    var isVisited: Boolean = false,
    var isWrongAnswer: Boolean = false,
    var userSelectAnswer: String? = null,
    var correctSelectAnswer: Int =0,
    var incorrectSelectAnswer: Int =0,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Passage)
        parcel.writeString(Solutions)
        parcel.writeString(Type_of_question)
        parcel.writeString(answer)
        parcel.writeString(option_A)
        parcel.writeString(option_B)
        parcel.writeString(option_C)
        parcel.writeString(option_D)
        parcel.writeString(question)
        parcel.writeString(question_count)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Question> {
        override fun createFromParcel(parcel: Parcel): Question {
            return Question(parcel)
        }

        override fun newArray(size: Int): Array<Question?> {
            return arrayOfNulls(size)
        }
    }
}