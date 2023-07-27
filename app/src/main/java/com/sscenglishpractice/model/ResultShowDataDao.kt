package com.sscenglishpractice.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sscenglishquiz.model.DbResultShowData

@Dao
interface ResultShowDataDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(resultShowData: DbResultShowData)

    @Query("SELECT * FROM result_show_data_table WHERE testCategory = :category")
    fun getShowsByCategory(category: String): List<DbResultShowData>

    @Query("DELETE FROM result_show_data_table WHERE testCategory = :category")
    fun deleteShowsByCategory(category: String)
}