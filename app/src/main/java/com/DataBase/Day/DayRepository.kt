package com.DataBase.Day

import androidx.lifecycle.LiveData
import com.DataBase.Activity.Activity
import com.DataBase.AppDBDao
import java.util.*


class DayRepository(private val appDBDao: AppDBDao, date: Date) {
    val daysWithActivities: LiveData<DayWithActivities>

    init {
        daysWithActivities = appDBDao.readDayWithActivities(date)

        if(appDBDao.readDay(date) == null){
            var newDay = Day(0, date, 0)

            appDBDao.addDay(newDay)
        }
    }

    suspend fun addActivity(activity: Activity){
        appDBDao.addActivity(activity)
    }

    suspend fun updateActivity(activity: Activity){
        appDBDao.updateActivity(activity)
    }

    suspend fun deleteActivity(activity: Activity){
        appDBDao.deleteActivity(activity)
    }
}