package com.DataBase.Day

import androidx.lifecycle.LiveData
import com.DataBase.AppDBDao
import java.util.*

class DayListRepository(private val appDBDao: AppDBDao) {
    val allDaysWithActivities: LiveData<List<DayWithActivities>> = appDBDao.readAllDaysWithActivities()

    suspend fun addDay(day: Day){
        appDBDao.addDay(day)
    }

    suspend fun updateDay(day: Day){
        appDBDao.updateDay(day)
    }

    suspend fun deleteDay(day: Day){
        appDBDao.deleteDay(day)
    }
}