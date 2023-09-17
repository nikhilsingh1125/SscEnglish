package com.sscenglishpractice.model

import android.os.Parcel
import android.os.Parcelable

data class BookmarkQuestions(val question: String?, val answer: String?):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(question)
        parcel.writeString(answer)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BookmarkQuestions> {
        override fun createFromParcel(parcel: Parcel): BookmarkQuestions {
            return BookmarkQuestions(parcel)
        }

        override fun newArray(size: Int): Array<BookmarkQuestions?> {
            return arrayOfNulls(size)
        }
    }
}
