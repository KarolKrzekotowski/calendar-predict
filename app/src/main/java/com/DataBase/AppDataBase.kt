package com.DataBase


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import com.DataBase.Activity.Activity
import com.DataBase.Category.Category
import com.DataBase.Day.Day
import com.DataBase.Objective.Objective
import com.DataBase.Rating.Rating

@Database(entities = [Activity::class, Category::class, Day::class, Objective::class, Rating::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {

    abstract fun appDBDao(): AppDBDao

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "calendar_predict_database"
                )
                    .createFromAsset("database/calendar_predict_database.db")
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}