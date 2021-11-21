package com.DataBase.Rating

import androidx.lifecycle.LiveData
import com.DataBase.Activity.Activity
import com.DataBase.AppDBDao
import com.DataBase.Day.Day
import com.DataBase.Day.DayWithActivities
import java.util.*

class RatingRepository(private val appDBDao: AppDBDao) {

    init {
    }

    suspend fun updateDay(day: Day){
        appDBDao.addDay(day)
    }

    suspend fun addRating(rating: Rating){
        appDBDao.addRating(rating)
    }

}