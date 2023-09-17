package com.sscenglishquiz.model

import android.os.Parcel
import android.os.Parcelable

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
    var isSelectedAnswer : Boolean = false,
    var isSkipAnswer : Boolean = false,
    var isWrongAnswer : Boolean = false,
    var questionIdentifier : String? =null,
    val Type_of_question: String? = "",
) :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readString()
    ) {
    }

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

    fun generateIdentifier(): String {
        val content = "$question$option_A$option_B$option_C$option_D"
        return content.hashCode().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(question_count)
        parcel.writeString(question)
        parcel.writeString(answer)
        parcel.writeString(option_A)
        parcel.writeString(option_B)
        parcel.writeString(option_C)
        parcel.writeString(option_D)
        parcel.writeString(Solutions)
        parcel.writeByte(if (isGivenAnswer) 1 else 0)
        parcel.writeString(optionsSelected)
        parcel.writeString(selectedOptionsAnswer)
        parcel.writeString(testCategory)
        parcel.writeByte(if (isBookmark) 1 else 0)
        parcel.writeByte(if (isSelectedAnswer) 1 else 0)
        parcel.writeByte(if (isSkipAnswer) 1 else 0)
        parcel.writeByte(if (isWrongAnswer) 1 else 0)
        parcel.writeString(questionIdentifier)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<QuestionData> {
        override fun createFromParcel(parcel: Parcel): QuestionData {
            return QuestionData(parcel)
        }

        override fun newArray(size: Int): Array<QuestionData?> {
            return arrayOfNulls(size)
        }
    }
}
