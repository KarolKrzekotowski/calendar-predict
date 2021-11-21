package com.DataBase.Day

import androidx.lifecycle.LiveData
import com.DataBase.Activity.Activity
import com.DataBase.AppDBDao
import com.DataBase.Category.Category
import java.util.*

class DayUpdateRepository(private val appDBDao: AppDBDao, date: Date) {
    val daysWithActivities: LiveData<DayWithActivities>
    val categories: List<Category>
    var day: Day?

    init {
        daysWithActivities = appDBDao.readDayWithActivities(date)
        categories = appDBDao.readAllCategories()

        day = appDBDao.readDay(date)

        if(day == null){
            var newDay = Day(0, date, 0)

            appDBDao.addDay(newDay)

            day = appDBDao.readDay(date)
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