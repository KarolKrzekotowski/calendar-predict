package com.DataBase.Day

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.DataBase.Activity.Activity
import com.DataBase.AppDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DayViewModel(application: Application): AndroidViewModel(application)  {
    val dayWithActivities: LiveData<DayWithActivities>
    private val dayRepository: DayRepository

    init {
        val appDBDao = AppDataBase.getDatabase(
            application
        ).appDBDao()

        dayRepository = DayRepository(appDBDao)
        dayWithActivities = dayRepository.daysWithActivities
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