package com.sscenglishpractice.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class QuizRepository {
    val db = Firebase.database.reference

    fun getData(): DatabaseReference {
        return db.child("Quizes")
    }
}