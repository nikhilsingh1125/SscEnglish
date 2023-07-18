package com.sscenglishpractice.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.sscenglishquiz.model.QuestionData

class QuizViewModel: ViewModel() {

    private val database = FirebaseDatabase.getInstance().getReference("SSC_MTS_2020/SYNONYMS_2020")
    private val _questions = MutableLiveData<List<QuestionData>>()
    val questions: LiveData<List<QuestionData>> = _questions

    fun loadQuiz() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Process the retrieved data
                val description = dataSnapshot.child("Description").getValue(String::class.java)
                val questions = dataSnapshot.child("Questions")
                    .getValue(object : GenericTypeIndicator<List<QuestionData>>() {})
                _questions.value = questions
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors here
            }
        })
    }
}