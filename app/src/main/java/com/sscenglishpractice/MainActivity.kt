package com.sscenglishpractice

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sscenglishpractice.adapter.QuestionAdapter
import com.sscenglishpractice.model.ListCategoryData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_toolbar.*

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    var type = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnSubmit.visibility = View.GONE
        val intent = intent
        type = intent.getStringExtra("TYPE").toString()
        val array = ArrayList<ListCategoryData>()

        action_bar_back.visibility = View.VISIBLE
        action_bar_share.visibility = View.GONE

        action_bar_back.setOnClickListener {
            onBackPressed()
        }

        if (type == "CGL") {
            array.add(ListCategoryData("Synonyms", ""))
            array.add(ListCategoryData("Antonyms", ""))
            array.add(ListCategoryData("Oneword", ""))
            array.add(ListCategoryData("Idioms and pharses", ""))
        }
        else if (type == "CHCL") {
            array.add(ListCategoryData("Synonyms", ""))
            array.add(ListCategoryData("Antonyms", ""))
            array.add(ListCategoryData("Oneword", ""))
            array.add(ListCategoryData("Idioms and pharses", ""))
        } else if (type == "CPO") {
            array.add(ListCategoryData("Synonyms", ""))
            array.add(ListCategoryData("Antonyms", ""))
            array.add(ListCategoryData("Oneword", ""))
            array.add(ListCategoryData("Idioms and pharses", ""))
        }
        else if (type == "MTS") {
            array.add(ListCategoryData("Synonyms", ""))
            array.add(ListCategoryData("Antonyms", ""))
            array.add(ListCategoryData("Oneword", ""))
            array.add(ListCategoryData("Idioms and pharses", ""))
        }

        else if (type == "CGL_2021") {
            array.add(ListCategoryData("Synonyms", ""))
            array.add(ListCategoryData("Antonyms", ""))
            array.add(ListCategoryData("Oneword", ""))
            array.add(ListCategoryData("Idioms and pharses", ""))
        } else if (type == "CHCL_2021") {
            array.add(ListCategoryData("Synonyms", ""))
            array.add(ListCategoryData("Antonyms", ""))
            array.add(ListCategoryData("Oneword", ""))
            array.add(ListCategoryData("Idioms and pharses", ""))
        } else if (type == "MTS_2021") {
            array.add(ListCategoryData("Synonyms", ""))
            array.add(ListCategoryData("Antonyms", ""))
            array.add(ListCategoryData("Oneword", ""))
            array.add(ListCategoryData("Idioms and pharses", ""))
        } else if (type == "CGL_2020") {
            array.add(ListCategoryData("Synonyms", ""))
            array.add(ListCategoryData("Antonyms", ""))
            array.add(ListCategoryData("Oneword", ""))
            array.add(ListCategoryData("Idioms and pharses", ""))
        } else if (type == "CGL_2019") {
            array.add(ListCategoryData("Synonyms", ""))
            array.add(ListCategoryData("Antonyms", ""))
            array.add(ListCategoryData("Oneword", ""))
            array.add(ListCategoryData("Idioms and pharses", ""))
        }
        else if (type == "MTS_2019") {
            array.add(ListCategoryData("Synonyms", ""))
            array.add(ListCategoryData("Antonyms", ""))
            array.add(ListCategoryData("Oneword", ""))
            array.add(ListCategoryData("Idioms and pharses", ""))
        }


        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        val rvAdapter = QuestionAdapter(this@MainActivity, array)
        recyclerView.adapter = rvAdapter

    }

    fun goToQuizActi(model: ListCategoryData, position: Int) {

        val intent = Intent(this, QuizActivity::class.java)
        if (type == "CGL") {
            if (position == 0) {
                intent.putExtra("TYPE", "0")
                intent.putExtra("Title", "SYNONYMS 2022")
                intent.putExtra("Category", "CGL SYNONYMS 2022")
            } else if (position == 1) {
                intent.putExtra("TYPE", "1")
                intent.putExtra("Title", "ANTONYMS 2022")
                intent.putExtra("Category", "CGL ANTONYMS 2022")
            } else if (position == 2) {
                intent.putExtra("TYPE", "2")
                intent.putExtra("Title", "ONEWORD 2022")
                intent.putExtra("Category", "CGL ONEWORD 2022")
            } else if (position == 3) {
                intent.putExtra("TYPE", "3")
                intent.putExtra("Title", "Idioms 2022")
                intent.putExtra("Category", "CGL IDIOMS 2022")
            }
        }
        else if (type == "CHCL") {
            if (position == 0) {
                intent.putExtra("TYPE", "CHCL_1")
                intent.putExtra("Title", "SYNONYMS 2022")
                intent.putExtra("Category", "CHCL SYNONYMS 2022")
            } else if (position == 1) {
                intent.putExtra("TYPE", "CHCL_2")
                intent.putExtra("Title", "ANTONYMS 2022")
                intent.putExtra("Category", "CHCL ANTONYMS 2022")
            } else if (position == 2) {
                intent.putExtra("TYPE", "CHCL_3")
                intent.putExtra("Title", "ONEWORD 2022")
                intent.putExtra("Category", "CHCL ONEWORD 2022")
            } else if (position == 3) {
                intent.putExtra("TYPE", "CHCL_4")
                intent.putExtra("Title", "IDIOMS 2022")
                intent.putExtra("Category", "CHCL IDIOMS 2022")
            }
        }
        else if (type == "CPO") {
            if (position == 0) {
                intent.putExtra("TYPE", "CPO_1")
                intent.putExtra("Title", "SYNONYMS 2022")
                intent.putExtra("Category", "CPO SYNONYMS 2022")
            } else if (position == 1) {
                intent.putExtra("TYPE", "CPO_2")
                intent.putExtra("Title", "ANTONYMS 2022")
                intent.putExtra("Category", "CPO ANTONYMS 2022")
            } else if (position == 2) {
                intent.putExtra("TYPE", "CPO_3")
                intent.putExtra("Title", "ONEWORD 2022")
                intent.putExtra("Category", "CPO ONEWORD 2022")
            } else if (position == 3) {
                intent.putExtra("TYPE", "CPO_4")
                intent.putExtra("Title", "IDIOMS 2022")
                intent.putExtra("Category", "CPO IDIOMS 2022")
            }
        }
        else if (type == "MTS") {
            if (position == 0) {
                intent.putExtra("TYPE", "MTS_1")
                intent.putExtra("Title", "SYNONYMS 2022")
                intent.putExtra("Category", "MTS SYNONYMS 2022")
            } else if (position == 1) {
                intent.putExtra("TYPE", "MTS_2")
                intent.putExtra("Title", "ANTONYMS 2022")
                intent.putExtra("Category", "MTS ANTONYMS 2022")
            } else if (position == 2) {
                intent.putExtra("TYPE", "MTS_3")
                intent.putExtra("Title", "ONEWORD 2022")
                intent.putExtra("Category", "MTS ONEWORD 2022")
            } else if (position == 3) {
                intent.putExtra("TYPE", "MTS_4")
                intent.putExtra("Title", "IDIOMS 2022")
                intent.putExtra("Category", "MTS IDIOMS 2022")
            }
        }

        else if (type == "CGL_2021") {
            if (position == 0) {
                intent.putExtra("TYPE", "CGL_2021_1")
                intent.putExtra("Title", "SYNONYMS 2021")
                intent.putExtra("Category", "CGL SYNONYMS 2021")
            } else if (position == 1) {
                intent.putExtra("TYPE", "CGL_2021_2")
                intent.putExtra("Title", "ANTONYMS 2021")
                intent.putExtra("Category", "CGL ANTONYMS 2021")
            } else if (position == 2) {
                intent.putExtra("TYPE", "CGL_2021_3")
                intent.putExtra("Title", "ONEWORD 2021")
                intent.putExtra("Category", "CGL ONEWORD 2021")
            } else if (position == 3) {
                intent.putExtra("TYPE", "CGL_2021_4")
                intent.putExtra("Title", "IDIOMS 2021")
                intent.putExtra("Category", "CGL IDIOMS 2021")
            }
        }
        else if (type == "CHCL_2021") {
            if (position == 0) {
                intent.putExtra("TYPE", "CHCL_2021_1")
                intent.putExtra("Title", "SYNONYMS 2021")
                intent.putExtra("Category", "CHCL SYNONYMS 2021")
            } else if (position == 1) {
                intent.putExtra("TYPE", "CHCL_2021_2")
                intent.putExtra("Title", "ANTONYMS 2021")
                intent.putExtra("Category", "CHCL ANTONYMS 2021")
            } else if (position == 2) {
                intent.putExtra("TYPE", "CHCL_2021_3")
                intent.putExtra("Title", "ONEWORD 2021")
                intent.putExtra("Category", "CHCL ONEWORD 2021")
            } else if (position == 3) {
                intent.putExtra("TYPE", "CHCL_2021_4")
                intent.putExtra("Title", "IDIOMS 2021")
                intent.putExtra("Category", "CHCL IDIOMS 2021")
            }
        }
        else if (type == "MTS_2021") {
            if (position == 0) {
                intent.putExtra("TYPE", "MTS_2021_1")
                intent.putExtra("Title", "SYNONYMS 2021")
                intent.putExtra("Category", "MTS SYNONYMS 2021")
            } else if (position == 1) {
                intent.putExtra("TYPE", "MTS_2021_2")
                intent.putExtra("Title", "ANTONYMS 2021")
                intent.putExtra("Category", "MTS ANTONYMS 2021")
            } else if (position == 2) {
                intent.putExtra("TYPE", "MTS_2021_3")
                intent.putExtra("Title", "ONEWORD 2021")
                intent.putExtra("Category", "MTS ONEWORD 2021")
            } else if (position == 3) {
                intent.putExtra("TYPE", "MTS_2021_4")
                intent.putExtra("Title", "IDIOMS 2021")
                intent.putExtra("Category", "MTS IDIOMS 2021")
            }
        }

        else if (type == "CGL_2020") {
            if (position == 0) {
                intent.putExtra("TYPE", "CGL_2020_1")
                intent.putExtra("Title", "SYNONYMS 2020")
                intent.putExtra("Category", "CGL SYNONYMS 2020")
            } else if (position == 1) {
                intent.putExtra("TYPE", "CGL_2020_2")
                intent.putExtra("Title", "ANTONYMS 2020")
                intent.putExtra("Category", "CGL ANTONYMS 2020")
            } else if (position == 2) {
                intent.putExtra("TYPE", "CGL_2020_3")
                intent.putExtra("Title", "ONEWORD 2020")
                intent.putExtra("Category", "CGL ONEWORD 2020")
            } else if (position == 3) {
                intent.putExtra("TYPE", "CGL_2020_4")
                intent.putExtra("Title", "IDIOMS 2020")
                intent.putExtra("Category", "CGL IDIOMS 2020")
            }
        }

        else if (type == "CGL_2019") {
            if (position == 0) {
                intent.putExtra("TYPE", "CGL_2019_1")
                intent.putExtra("Title", "SYNONYMS 2019")
                intent.putExtra("Category", "CGL SYNONYMS 2019")
            } else if (position == 1) {
                intent.putExtra("TYPE", "CGL_2019_2")
                intent.putExtra("Title", "ANTONYMS 2019")
                intent.putExtra("Category", "CGL ANTONYMS 2019")
            } else if (position == 2) {
                intent.putExtra("TYPE", "CGL_2019_3")
                intent.putExtra("Title", "ONEWORD 2019")
                intent.putExtra("Category", "CGL ONEWORD 2019")
            } else if (position == 3) {
                intent.putExtra("TYPE", "CGL_2019_4")
                intent.putExtra("Title", "IDIOMS 2019")
                intent.putExtra("Category", "CGL IDIOMS 2019")
            }
        }

        else if (type == "MTS_2019") {
            if (position == 0) {
                intent.putExtra("TYPE", "MTS_2019_1")
                intent.putExtra("Title", "SYNONYMS 2019")
                intent.putExtra("Category", "MTS SYNONYMS 2019")
            } else if (position == 1) {
                intent.putExtra("TYPE", "MTS_2019_2")
                intent.putExtra("Title", "ANTONYMS 2019")
                intent.putExtra("Category", "MTS ANTONYMS 2019")
            } else if (position == 2) {
                intent.putExtra("TYPE", "MTS_2019_3")
                intent.putExtra("Title", "ONEWORD 2019")
                intent.putExtra("Category", "MTS ONEWORD 2019")
            } else if (position == 3) {
                intent.putExtra("TYPE", "MTS_2019_4")
                intent.putExtra("Title", "IDIOMS 2019")
                intent.putExtra("Category", "MTS IDIOMS 2019")
            }
        }



        startActivity(intent)
    }

}