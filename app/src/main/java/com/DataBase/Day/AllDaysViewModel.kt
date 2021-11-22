package com.DataBase.Day

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.DataBase.Activity.Activity
import com.DataBase.AppDataBase
import com.DataBase.Category.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class AllDaysViewModel(application: Application): AndroidViewModel(application)  {
    var days: LiveData<List<Day>>
    private val allDaysRepository: AllDaysRepository

    init {
        val appDBDao = AppDataBase.getDatabase(
            application
        ).appDBDao()

        allDaysRepository = AllDaysRepository(appDBDao)
        days = allDaysRepository.days

        //Log.e("12345676890", dayRepository.daysWithActivities.value.toString())
    }
}