package com.DataBase.Day

import androidx.lifecycle.LiveData
import com.DataBase.Activity.Activity
import com.DataBase.AppDBDao
import com.DataBase.Category.Category
import java.util.*

class AllDaysRepository (private val appDBDao: AppDBDao) {
    val days: LiveData<List<Day>>

    init {
        days = appDBDao.readAllDays()
    }
}