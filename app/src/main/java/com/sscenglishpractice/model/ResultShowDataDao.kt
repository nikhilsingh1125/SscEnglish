package com.sscenglishpractice.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sscenglishquiz.model.DbResultShowData

@Dao
interface ResultShowDataDao {
    @Insert
    fun insert(resultShowData: DbResultShowData)

    @Query("SELECT * FROM result_show_data_table")
    fun getAllResultShowData(): List<DbResultShowData>
}