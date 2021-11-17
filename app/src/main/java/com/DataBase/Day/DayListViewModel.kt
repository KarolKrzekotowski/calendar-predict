package com.DataBase.Day

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.DataBase.AppDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DayListViewModel(application: Application): AndroidViewModel(application) {
    val allDaysWithActivities: LiveData<List<DayWithActivities>>
    private val dayListRepository: DayListRepository

    init {
        val appDBDao = AppDataBase.getDatabase(
            application
        ).appDBDao()

        dayListRepository = DayListRepository(appDBDao)
        allDaysWithActivities = dayListRepository.allDaysWithActivities
    }

    fun addDay(day: Day){
        viewModelScope.launch(Dispatchers.IO){
            dayListRepository.addDay(day)
        }
    }

    fun updateDay(day: Day){
        viewModelScope.launch(Dispatchers.IO){
            dayListRepository.updateDay(day)
        }
    }

    fun deleteDay(day: Day){
        viewModelScope.launch(Dispatchers.IO){
            dayListRepository.deleteDay(day)
        }
    }
}