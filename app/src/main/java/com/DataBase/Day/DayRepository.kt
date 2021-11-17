package com.DataBase.Day

import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import com.DataBase.Activity.Activity
import com.DataBase.AppDBDao
import java.util.*


class DayRepository(private val appDBDao: AppDBDao) {
    val daysWithActivities: LiveData<DayWithActivities>

    init {
        val calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = 0
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0

        daysWithActivities = appDBDao.readDayWithActivities(calendar.time)
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