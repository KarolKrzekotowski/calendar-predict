package com.DataBase.Day

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.DataBase.Activity.Activity
import com.DataBase.AppDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class DayViewModel(application: Application): AndroidViewModel(application)  {
    var dayWithActivities: LiveData<DayWithActivities>
    private val dayRepository: DayRepository

    init {
        val appDBDao = AppDataBase.getDatabase(
            application
        ).appDBDao()

        dayRepository = DayRepository(appDBDao)

        dayWithActivities = dayRepository.daysWithActivities

        if(dayWithActivities.value == null){

            val calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = 0
            calendar[Calendar.MINUTE] = 0
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0

            viewModelScope.launch(Dispatchers.IO){
                var newDay = Day(0, calendar.time, 0)

                dayRepository.addDay(newDay)
            }
        }
    }

    fun addActivity(activity: Activity){
        viewModelScope.launch(Dispatchers.IO){
            dayRepository.addActivity(activity)
        }
    }

    fun updateActivity(activity: Activity){
        viewModelScope.launch(Dispatchers.IO){
            dayRepository.updateActivity(activity)
        }
    }

    fun deleteActivity(activity: Activity){
        viewModelScope.launch(Dispatchers.IO){
            dayRepository.deleteActivity(activity)
        }
    }
}