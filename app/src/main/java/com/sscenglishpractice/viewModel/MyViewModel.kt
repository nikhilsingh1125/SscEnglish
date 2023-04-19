package com.sscenglishpractice.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.sscenglishpractice.repository.QuizRepository
import com.sscenglishquiz.model.QuestionData
import com.sscenglishquiz.model.QuestionWiseModel

/*
class MyViewModel(val repository: QuizRepository) : ViewModel() {


    val _data = MutableLiveData<List<QuestionData>>()
    val data: LiveData<List<QuestionWiseModel>> = _data

    constructor() : this(QuizRepository())

    fun fetchData() {


        try {
            repository.getData().addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val questionWiseModel = snapshot.getValue(QuestionWiseModel::class.java)
                        _data.value = questionWiseModel.Questions

                    } else {
                        // handle the case when there is no data
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // handle error
                }
            })
        } catch (e: Exception) {
            Log.e("MyViewModel", "fetchData: $e")

        }


    }
}*/
