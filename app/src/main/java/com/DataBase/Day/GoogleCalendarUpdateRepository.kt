package com.DataBase.Day

import androidx.lifecycle.LiveData
import com.DataBase.Activity.Activity
import com.DataBase.AppDBDao
import java.util.*

class GoogleCalendarUpdateRepository(private val appDBDao: AppDBDao) {
    fun getDaysList(dateFrom: Date, dateTo: Date) : List<DayWithActivities>{
        return appDBDao.readDaysWithActivities(dateFrom, dateTo)
    }
}