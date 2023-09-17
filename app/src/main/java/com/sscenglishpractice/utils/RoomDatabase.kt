package com.sscenglishpractice.utils

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sscenglishpractice.model.ResultShowDataDao
import com.sscenglishquiz.model.DbResultShowData

@Database(entities = [DbResultShowData::class], version =2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun resultShowDataDao(): ResultShowDataDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "app_database"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}

