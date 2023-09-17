package com.sscenglishpractice

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.sscenglishpractice.adapter.QuestionAdapter
import com.sscenglishpractice.model.ListCategoryData
import kotlinx.android.synthetic.main.activity_home_category.adView
import kotlinx.android.synthetic.main.activity_main.recyclerView
import kotlinx.android.synthetic.main.custom_toolbar.action_bar_back
import kotlinx.android.synthetic.main.custom_toolbar.action_bar_share
import kotlinx.android.synthetic.main.custom_toolbar.btnSubmit

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

        MobileAds.initialize(this)

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

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
            array.add(ListCategoryData("Spelling Error", ""))
        }
        else if (type == "CHCL") {
            array.add(ListCategoryData("Synonyms", ""))
            array.add(ListCategoryData("Antonyms", ""))
            array.add(ListCategoryData("Oneword", ""))
            array.add(ListCategoryData("Idioms and pharses", ""))
            array.add(ListCategoryData("Spelling Error", ""))
        } else if (type == "CPO") {
            array.add(ListCategoryData("Synonyms", ""))
            array.add(ListCategoryData("Antonyms", ""))
            array.add(ListCategoryData("Oneword", ""))
            array.add(ListCategoryData("Idioms and pharses", ""))
            array.add(ListCategoryData("Spelling Error", ""))
        }
        else if (type == "Stenographer") {
            array.add(ListCategoryData("Synonyms", ""))
            array.add(ListCategoryData("Antonyms", ""))
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
            array.add(ListCategoryData("Spelling Error", ""))
        } else if (type == "CHCL_2021") {
            array.add(ListCategoryData("Synonyms", ""))
            array.add(ListCategoryData("Antonyms", ""))
            array.add(ListCategoryData("Oneword", ""))
            array.add(ListCategoryData("Idioms and pharses", ""))
            array.add(ListCategoryData("Spelling Error", ""))
        } else if (type == "MTS_2021") {
            array.add(ListCategoryData("Synonyms", ""))
            array.add(ListCategoryData("Antonyms", ""))
            array.add(ListCategoryData("Oneword", ""))
            array.add(ListCategoryData("Idioms and pharses", ""))
            array.add(ListCategoryData("Spelling Error", ""))
        }
        else if (type == "CGL_2020") {
            array.add(ListCategoryData("Synonyms", ""))
            array.add(ListCategoryData("Antonyms", ""))
            array.add(ListCategoryData("Oneword", ""))
            array.add(ListCategoryData("Idioms and pharses", ""))
            array.add(ListCategoryData("Spelling Error", ""))
        }
        else if (type == "MTS_2020") {
            array.add(ListCategoryData("Synonyms", ""))
            array.add(ListCategoryData("Antonyms", ""))
            array.add(ListCategoryData("Oneword", ""))
            array.add(ListCategoryData("Idioms and pharses", ""))
            array.add(ListCategoryData("Spelling Error", ""))
        }

        else if (type == "CHSL_2020") {
            array.add(ListCategoryData("Synonyms", ""))
            array.add(ListCategoryData("Antonyms", ""))
            array.add(ListCategoryData("Oneword", ""))
            array.add(ListCategoryData("Idioms and pharses", ""))
            array.add(ListCategoryData("Spelling Error", ""))
        }
        else if (type == "CPO_2020") {
            array.add(ListCategoryData("Synonyms", ""))
            array.add(ListCategoryData("Antonyms", ""))
            array.add(ListCategoryData("Oneword", ""))
            array.add(ListCategoryData("Idioms and pharses", ""))
            array.add(ListCategoryData("Spelling Error", ""))
        }

        else if (type == "CGL_2019") {
            array.add(ListCategoryData("Synonyms", ""))
            array.add(ListCategoryData("Antonyms", ""))
            array.add(ListCategoryData("Oneword", ""))
            array.add(ListCategoryData("Idioms and pharses", ""))
            array.add(ListCategoryData("Spelling Error", ""))
        }
        else if (type == "MTS_2019") {
            array.add(ListCategoryData("Synonyms", ""))
            array.add(ListCategoryData("Antonyms", ""))
            array.add(ListCategoryData("Oneword", ""))
            array.add(ListCategoryData("Idioms and pharses", ""))
            array.add(ListCategoryData("Spelling Error", ""))
        }
        else if (type == "CPO_2019") {
            array.add(ListCategoryData("Synonyms", ""))
            array.add(ListCategoryData("Antonyms", ""))
            array.add(ListCategoryData("Oneword", ""))
            array.add(ListCategoryData("Idioms and pharses", ""))
            array.add(ListCategoryData("Spelling Error", ""))
        }
        else if (type == "CHSL_2019") {
            array.add(ListCategoryData("Synonyms", ""))
            array.add(ListCategoryData("Antonyms", ""))
            array.add(ListCategoryData("Oneword", ""))
            array.add(ListCategoryData("Idioms and pharses", ""))
            array.add(ListCategoryData("Spelling Error", ""))
        }
        else if (type == "CGL_2023") {
            array.add(ListCategoryData("Synonyms", ""))
            array.add(ListCategoryData("Antonyms", ""))
            array.add(ListCategoryData("Oneword", ""))
            array.add(ListCategoryData("Idioms and pharses", ""))
            array.add(ListCategoryData("Spelling Error", ""))
        }
        else if (type == "PHASE_2023") {
            array.add(ListCategoryData("Synonyms", ""))
            array.add(ListCategoryData("Antonyms", ""))
            array.add(ListCategoryData("Oneword", ""))
            array.add(ListCategoryData("Idioms and pharses", ""))
            array.add(ListCategoryData("Spelling Error", ""))
        }
        else if (type == "CHSL_2023") {
            array.add(ListCategoryData("Synonyms", ""))
            array.add(ListCategoryData("Antonyms", ""))
            array.add(ListCategoryData("Oneword", ""))
            array.add(ListCategoryData("Idioms and pharses", ""))
            array.add(ListCategoryData("Spelling Error", ""))
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
            else if (position == 4) {
                intent.putExtra("TYPE", "4")
                intent.putExtra("Title", "Spelling Error 2022")
                intent.putExtra("Category", "CGL Spelling Error 2022")
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
            else if (position == 4) {
                intent.putExtra("TYPE", "CHCL_5")
                intent.putExtra("Title", "Spelling Error 2022")
                intent.putExtra("Category", "CHCL Spelling Error 2022")
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
            else if (position == 4) {
                intent.putExtra("TYPE", "CPO_5")
                intent.putExtra("Title", "Spelling Error 2022")
                intent.putExtra("Category", "CPO Spelling Error 2022")
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
            else if (position == 4) {
                intent.putExtra("TYPE", "MTS_5")
                intent.putExtra("Title", "Spelling Error 2022")
                intent.putExtra("Category", "MTS Spelling Error 2022")
            }
        }
        else if (type == "Stenographer") {
            if (position == 0) {
                intent.putExtra("TYPE", "Stenographer_1")
                intent.putExtra("Title", "SYNONYMS 2022")
                intent.putExtra("Category", "Stenographer SYNONYMS 2022")
            } else if (position == 1) {
                intent.putExtra("TYPE", "Stenographer_2")
                intent.putExtra("Title", "ANTONYMS 2022")
                intent.putExtra("Category", "Stenographer ANTONYMS 2022")
            }  else if (position == 2) {
                intent.putExtra("TYPE", "Stenographer_3")
                intent.putExtra("Title", "IDIOMS 2022")
                intent.putExtra("Category", "Stenographer IDIOMS 2022")
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
            else if (position == 4) {
                intent.putExtra("TYPE", "CGL_2021_5")
                intent.putExtra("Title", "Spelling Error 2021")
                intent.putExtra("Category", "CGL Spelling Error 2021")
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
            else if (position == 4) {
                intent.putExtra("TYPE", "CHCL_2021_5")
                intent.putExtra("Title", "Spelling Error 2021")
                intent.putExtra("Category", "CHCL Spelling Error 2021")
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
            else if (position == 4) {
                intent.putExtra("TYPE", "MTS_2021_5")
                intent.putExtra("Title", "Spelling Error 2021")
                intent.putExtra("Category", "MTS Spelling Error 2021")
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
            else if (position == 4) {
                intent.putExtra("TYPE", "CGL_2020_5")
                intent.putExtra("Title", "Spelling Error 2022")
                intent.putExtra("Category", "CGL Spelling Error 2020")
            }
        }
        else if (type == "MTS_2020") {
            if (position == 0) {
                intent.putExtra("TYPE", "MTS_2020_1")
                intent.putExtra("Title", "SYNONYMS 2020")
                intent.putExtra("Category", "MTS SYNONYMS 2020")
            } else if (position == 1) {
                intent.putExtra("TYPE", "MTS_2020_2")
                intent.putExtra("Title", "ANTONYMS 2020")
                intent.putExtra("Category", "MTS ANTONYMS 2020")
            } else if (position == 2) {
                intent.putExtra("TYPE", "MTS_2020_3")
                intent.putExtra("Title", "ONEWORD 2020")
                intent.putExtra("Category", "MTS ONEWORD 2020")
            } else if (position == 3) {
                intent.putExtra("TYPE", "MTS_2020_4")
                intent.putExtra("Title", "IDIOMS 2020")
                intent.putExtra("Category", "MTS IDIOMS 2020")
            }
            else if (position == 4) {
                intent.putExtra("TYPE", "MTS_2020_5")
                intent.putExtra("Title", "Spelling Error 2022")
                intent.putExtra("Category", "MTS Spelling Error 2020")
            }
        }
        else if (type == "CHSL_2020") {
            if (position == 0) {
                intent.putExtra("TYPE", "CHSL_2020_1")
                intent.putExtra("Title", "SYNONYMS 2020")
                intent.putExtra("Category", "CGL SYNONYMS 2020")
            } else if (position == 1) {
                intent.putExtra("TYPE", "CHSL_2020_2")
                intent.putExtra("Title", "ANTONYMS 2020")
                intent.putExtra("Category", "CGL ANTONYMS 2020")
            } else if (position == 2) {
                intent.putExtra("TYPE", "CHSL_2020_3")
                intent.putExtra("Title", "ONEWORD 2020")
                intent.putExtra("Category", "CGL ONEWORD 2020")
            } else if (position == 3) {
                intent.putExtra("TYPE", "CHSL_2020_4")
                intent.putExtra("Title", "IDIOMS 2020")
                intent.putExtra("Category", "CGL IDIOMS 2020")
            }
            else if (position == 4) {
                intent.putExtra("TYPE", "CHSL_2020_5")
                intent.putExtra("Title", "Spelling Error 2022")
                intent.putExtra("Category", "CHSL Spelling Error 2020")
            }
        }
        else if (type == "CPO_2020") {
            if (position == 0) {
                intent.putExtra("TYPE", "CPO_2020_1")
                intent.putExtra("Title", "SYNONYMS 2020")
                intent.putExtra("Category", "CPO SYNONYMS 2020")
            } else if (position == 1) {
                intent.putExtra("TYPE", "CPO_2020_2")
                intent.putExtra("Title", "ANTONYMS 2020")
                intent.putExtra("Category", "CPO ANTONYMS 2020")
            } else if (position == 2) {
                intent.putExtra("TYPE", "CPO_2020_3")
                intent.putExtra("Title", "ONEWORD 2020")
                intent.putExtra("Category", "CPO ONEWORD 2020")
            } else if (position == 3) {
                intent.putExtra("TYPE", "CPO_2020_4")
                intent.putExtra("Title", "IDIOMS 2020")
                intent.putExtra("Category", "CPO IDIOMS 2020")
            }
            else if (position == 4) {
                intent.putExtra("TYPE", "CPO_2020_5")
                intent.putExtra("Title", "Spelling Error 2022")
                intent.putExtra("Category", "CPO Spelling Error 2020")
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
            else if (position == 4) {
                intent.putExtra("TYPE", "CGL_2019_5")
                intent.putExtra("Title", "Spelling Error 2022")
                intent.putExtra("Category", "CGL Spelling Error 2019")
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
            else if (position == 4) {
                intent.putExtra("TYPE", "MTS_2019_5")
                intent.putExtra("Title", "Spelling Error 2022")
                intent.putExtra("Category", "MTS Spelling Error 2019")
            }
        }
        else if (type == "CPO_2019") {
            if (position == 0) {
                intent.putExtra("TYPE", "CPO_2019_1")
                intent.putExtra("Title", "SYNONYMS 2019")
                intent.putExtra("Category", "CPO SYNONYMS 2019")
            } else if (position == 1) {
                intent.putExtra("TYPE", "CPO_2019_2")
                intent.putExtra("Title", "ANTONYMS 2019")
                intent.putExtra("Category", "CPO ANTONYMS 2019")
            } else if (position == 2) {
                intent.putExtra("TYPE", "CPO_2019_3")
                intent.putExtra("Title", "ONEWORD 2019")
                intent.putExtra("Category", "CPO ONEWORD 2019")
            } else if (position == 3) {
                intent.putExtra("TYPE", "CPO_2019_4")
                intent.putExtra("Title", "IDIOMS 2019")
                intent.putExtra("Category", "CPO IDIOMS 2019")
            }
            else if (position == 4) {
                intent.putExtra("TYPE", "CPO_2019_5")
                intent.putExtra("Title", "Spelling Error 2022")
                intent.putExtra("Category", "CPO Spelling Error 2019")
            }
        }

        else if (type == "CHSL_2019") {
            if (position == 0) {
                intent.putExtra("TYPE", "CHSL_2019_1")
                intent.putExtra("Title", "SYNONYMS 2019")
                intent.putExtra("Category", "CHSL SYNONYMS 2019")
            } else if (position == 1) {
                intent.putExtra("TYPE", "CHSL_2019_2")
                intent.putExtra("Title", "ANTONYMS 2019")
                intent.putExtra("Category", "CHSL ANTONYMS 2019")
            } else if (position == 2) {
                intent.putExtra("TYPE", "CHSL_2019_3")
                intent.putExtra("Title", "ONEWORD 2019")
                intent.putExtra("Category", "CHSL ONEWORD 2019")
            } else if (position == 3) {
                intent.putExtra("TYPE", "CHSL_2019_4")
                intent.putExtra("Title", "IDIOMS 2019")
                intent.putExtra("Category", "CHSL IDIOMS 2019")
            }
            else if (position == 4) {
                intent.putExtra("TYPE", "CHSL_2019_5")
                intent.putExtra("Title", "Spelling Error 2022")
                intent.putExtra("Category", "CHSL Spelling Error 2019")
            }
        }
        else if (type == "CGL_2023") {
            if (position == 0) {
                intent.putExtra("TYPE", "CGL_2023_1")
                intent.putExtra("Title", "SYNONYMS 2023")
                intent.putExtra("Category", "CGL SYNONYMS 2023")
            } else if (position == 1) {
                intent.putExtra("TYPE", "CGL_2023_2")
                intent.putExtra("Title", "ANTONYMS 2023")
                intent.putExtra("Category", "CGL ANTONYMS 2023")
            } else if (position == 2) {
                intent.putExtra("TYPE", "CGL_2023_3")
                intent.putExtra("Title", "ONEWORD 2023")
                intent.putExtra("Category", "CGL ONEWORD 2023")
            } else if (position == 3) {
                intent.putExtra("TYPE", "CGL_2023_4")
                intent.putExtra("Title", "IDIOMS 2023")
                intent.putExtra("Category", "CGL IDIOMS 2023")
            }
            else if (position == 4) {
                intent.putExtra("TYPE", "CGL_2023_5")
                intent.putExtra("Title", "Spelling Error 2023")
                intent.putExtra("Category", "CGL Spelling Error 2023")
            }
        }
        else if (type == "PHASE_2023") {
            if (position == 0) {
                intent.putExtra("TYPE", "PHASE_2023_1")
                intent.putExtra("Title", "SYNONYMS 2023")
                intent.putExtra("Category", "PHASE SYNONYMS 2023")
            } else if (position == 1) {
                intent.putExtra("TYPE", "PHASE_2023_2")
                intent.putExtra("Title", "ANTONYMS 2023")
                intent.putExtra("Category", "PHASE ANTONYMS 2023")
            } else if (position == 2) {
                intent.putExtra("TYPE", "PHASE_2023_3")
                intent.putExtra("Title", "ONEWORD 2023")
                intent.putExtra("Category", "PHASE ONEWORD 2023")
            } else if (position == 3) {
                intent.putExtra("TYPE", "PHASE_2023_4")
                intent.putExtra("Title", "IDIOMS 2023")
                intent.putExtra("Category", "PHASE IDIOMS 2023")
            }
            else if (position == 4) {
                intent.putExtra("TYPE", "PHASE_2023_5")
                intent.putExtra("Title", "Spelling Error 2023")
                intent.putExtra("Category", "PHASE Spelling Error 2023")
            }
        }
        else if (type == "CHSL_2023") {
            if (position == 0) {
                intent.putExtra("TYPE", "CHSL_2023_1")
                intent.putExtra("Title", "SYNONYMS 2023")
                intent.putExtra("Category", "CHSL SYNONYMS 2023")
            } else if (position == 1) {
                intent.putExtra("TYPE", "CHSL_2023_2")
                intent.putExtra("Title", "ANTONYMS 2023")
                intent.putExtra("Category", "CHSL ANTONYMS 2023")
            } else if (position == 2) {
                intent.putExtra("TYPE", "CHSL_2023_3")
                intent.putExtra("Title", "ONEWORD 2023")
                intent.putExtra("Category", "CHSL ONEWORD 2023")
            } else if (position == 3) {
                intent.putExtra("TYPE", "CHSL_2023_4")
                intent.putExtra("Title", "IDIOMS 2023")
                intent.putExtra("Category", "CHSL IDIOMS 2023")
            }

            else if (position == 4) {
                intent.putExtra("TYPE", "CHSL_2023_5")
                intent.putExtra("Title", "Spelling Error 2023")
                intent.putExtra("Category", "CHSL Spelling Error 2023")
            }

        }

            startActivity(intent)
    }

}