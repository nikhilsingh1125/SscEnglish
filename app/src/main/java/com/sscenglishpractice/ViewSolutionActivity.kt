package com.sscenglishpractice

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.sscenglishpractice.adapter.ViewSolutionAdapter
import com.sscenglishpractice.model.QuestionRes
import com.sscenglishpractice.model.ResultShowData
import kotlinx.android.synthetic.main.activity_quiz.loader
import kotlinx.android.synthetic.main.activity_view_solution.idViewPagerSol
import kotlinx.android.synthetic.main.custom_toolbar.action_bar_Title
import kotlinx.android.synthetic.main.custom_toolbar.action_bar_back
import kotlinx.android.synthetic.main.custom_toolbar.action_bar_share
import kotlinx.android.synthetic.main.custom_toolbar.btnSubmit

class ViewSolutionActivity : AppCompatActivity() {

    val TAG = "ViewSolutionActivity"
    var type = ""
    var title = ""
    lateinit var db : FirebaseFirestore
    var categoryTest =""
    lateinit var questions: ArrayList<ResultShowData>
    private lateinit var arrResultDetailsData: ArrayList<ResultShowData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_solution)

        loader.setAnimation(R.raw.loader)
        loader.visibility = View.VISIBLE
        loader.playAnimation()

        db = FirebaseFirestore.getInstance()

        arrResultDetailsData = arrayListOf()


        action_bar_share.visibility = View.GONE
        btnSubmit.visibility = View.GONE
        action_bar_back.visibility = View.VISIBLE
        action_bar_back.setOnClickListener {
            onBackPressed()
        }

        action_bar_Title.text = "Result View"

        val intent = intent
        val category = intent.getStringExtra("categoryType")
        val typeCat = intent.getStringExtra("TypeCat")
        Log.d("QuizResultsPractice", "categoryType: $category")
        Log.d("QuizResultsPractice", "typeCat: $typeCat")


        getDataSolutions(typeCat,category)
    }

    private fun getDataSolutions(typeCat: String?, category: String?) {

        val databaseReference = FirebaseDatabase.getInstance().getReference("QuizResultsPractice/$typeCat")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Handle data changes
                if (snapshot.exists()) {
                    loader.visibility = View.GONE
                    val questionsSnapshot = snapshot.child("questions")
                    val questionsList = questionsSnapshot.getValue(object : GenericTypeIndicator<List<QuestionRes>>() {}) as ArrayList<QuestionRes>

                    // After retrieving the questions list, set up your ViewPager
                    val adapter = ViewSolutionAdapter(this@ViewSolutionActivity, questionsList, categoryTest)
                    idViewPagerSol.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                Log.e(TAG, "Failed to retrieve quiz result: ${error.message}", error.toException())
            }
        })








        /*   val firestore = FirebaseFirestore.getInstance()
           val collectionRef = firestore.collection("your_collection_name")

   // Assuming you have the category and subcollection names
           if (categoryType != null) {
               categoryTest = categoryType
           }
           val subcollection = "your_subcollection_name"

           if (categoryTest != null) {

               collectionRef.document(categoryTest).collection(subcollection).get()
                   .addOnSuccessListener { querySnapshot ->
                       loader.visibility = View.GONE
                       // Data retrieval successful
                       val resultDataList = ArrayList<ResultShowData>()

                       for (documentSnapshot in querySnapshot.documents) {
                           val resultShowData = documentSnapshot.toObject(ResultShowData::class.java)
                           if (resultShowData != null) {
                               resultDataList.add(resultShowData)
                           }
                       }

                       Log.d("SelectedUI", "resultDataList ===>: $resultDataList")
                       val adapter = ViewSolutionAdapter(this@ViewSolutionActivity, resultDataList,categoryTest)
                       idViewPagerSol.adapter = adapter


                       val arraySolutions = resultDataList
                       Log.d("firestore", "arraySolutions: $arraySolutions")
                       for (resultShowData in resultDataList) {
                           val questionCount = resultShowData.question_count
                           val question = resultShowData.question
                           val answer = resultShowData.answer
                           val options_A = resultShowData.option_A
                           val options_B = resultShowData.option_B
                           val options_C = resultShowData.option_C
                           val options_D = resultShowData.option_D
                           val solution = resultShowData.Solutions
                           val selectedAnswer = resultShowData.selectedAnswer
                           val optionsSelected = resultShowData.optionsSelected

                           // Access other properties as needed
                           val testCategory = resultShowData.testCategory

                           Log.d(TAG, "Question Count: $questionCount")
                           Log.d(TAG, "Question: $question")
                           Log.d(TAG, "Answer: $answer")
                           Log.d(TAG, "Option A: $options_A")
                           Log.d(TAG, "Option B: $options_B")
                           Log.d(TAG, "Option C: $options_C")
                           Log.d(TAG, "Option D: $options_D")
                           Log.d(TAG, "Solution: $solution")
                           Log.d(TAG, "Selected Answer: $selectedAnswer")
                           Log.d(TAG, "Options Selected: $optionsSelected")
                           Log.d(TAG, "Test Category: $testCategory")

                           val resultString = resultShowData.toString()
                           Log.d(TAG, "ResultShowData: $resultString")
                           // Perform any other operations or log additional properties
                       }

                       val resultStringList = ArrayList<String>()

                       for (resultShowData in resultDataList) {
                           val resultString = resultShowData.toString()
                           resultStringList.add(resultString)
                       }

                       Log.d("SelectedUI", "array====>: $resultStringList")

   // Print the resultStringList or use it as needed
                       for (resultString in resultStringList) {
                           Log.d(TAG, "ResultShowData: $resultString")
                       }


                       // Process the resultDataList or update your UI with the retrieved data
                   }
                   .addOnFailureListener { e ->
                       // Handle any errors
                       Log.e("SelectedUI", "Error retrieving data for category: $categoryTest - ${e.message}", e)
                   }
           }*/
    }


    fun clickOnBtnNext() {
        idViewPagerSol.setCurrentItem(idViewPagerSol.currentItem + 1, true)
    }

    /*override fun onBackPressed() {

    *//*    val pDialog = PrettyDialog(this)
        pDialog
            .setTitle(getString(R.string.app_name))
            .setMessage("Are you sure you don't want continue?")
            .setIconTint(R.color.black_light)
            .addButton(
                "OK",  // button text
                R.color.white,  // button text color
                R.color.black_light
            )  // button background color
            {
                deleteCollection()
                pDialog.dismiss()
                startActivity(Intent(this@ViewSolutionActivity,HomeActivity::class.java))
//                deleteCollection()
            }
            .addButton(
                "Cancel",  // button text
                R.color.white,  // button text color
                R.color.black_light
            )  // button background color
            { pDialog.dismiss() }
            .show()*//*
    }*/

 /*   private fun deleteCollection() {
        val firestore = FirebaseFirestore.getInstance()
        val collectionRef = firestore.collection("your_collection_name")

// Delete all documents within the subcollection
        collectionRef.document(categoryTest).collection("your_subcollection_name")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val batch = firestore.batch()

                for (documentSnapshot in querySnapshot.documents) {
                    batch.delete(documentSnapshot.reference)
                }

                // Commit the batch delete operation
                batch.commit()
                    .addOnSuccessListener {
                        // Subcollection documents deleted successfully
                        // Now delete the subcollection itself

                        collectionRef.document(categoryTest).collection("your_subcollection_name")
                            .get()
                            .addOnSuccessListener { subcollectionSnapshot ->
                                val subcollectionBatch = firestore.batch()

                                for (documentSnapshot in subcollectionSnapshot.documents) {
                                    subcollectionBatch.delete(documentSnapshot.reference)
                                }

                                // Commit the subcollection batch delete operation
                                subcollectionBatch.commit()
                                    .addOnSuccessListener {
                                        startActivity(Intent(this@ViewSolutionActivity,HomeActivity::class.java))
                                        // Subcollection deleted successfully
                                        Log.d("SelectedUI", "Subcollection deleted successfully")
                                    }
                                    .addOnFailureListener { e ->
                                        // Handle any errors during subcollection deletion
                                        Log.e(
                                            "SelectedUI",
                                            "Error deleting subcollection: ${e.message}",
                                            e
                                        )
                                    }
                            }
                            .addOnFailureListener { e ->
                                // Handle any errors during subcollection retrieval
                                Log.e(
                                    "SelectedUI",
                                    "Error retrieving subcollection: ${e.message}",
                                    e
                                )
                            }
                    }
                    .addOnFailureListener { e ->
                        // Handle any errors during subcollection document deletion
                        Log.e(
                            "SelectedUI",
                            "Error deleting subcollection documents: ${e.message}",
                            e
                        )
                    }
            }
            .addOnFailureListener { e ->
                // Handle any errors during subcollection document retrieval
                Log.e(
                    "SelectedUI",
                    "Error retrieving subcollection documents: ${e.message}",
                    e
                )
            }

    }*/

    override fun onDestroy() {
        super.onDestroy()
//        deleteCollection()
    }

    fun clickOnBtnPrev() {
        idViewPagerSol.setCurrentItem(idViewPagerSol.currentItem - 1, true)
    }
}