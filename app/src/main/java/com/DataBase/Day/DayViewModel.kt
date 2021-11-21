package com.DataBase.Day

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.DataBase.Activity.Activity
import com.DataBase.AppDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class DayViewModel(application: Application, date: Date): AndroidViewModel(application)  {
    var dayWithActivities: LiveData<DayWithActivities>
    private val dayRepository: DayRepository

    init {
        val appDBDao = AppDataBase.getDatabase(
            application
        ).appDBDao()

        dayRepository = DayRepository(appDBDao, date)

        dayWithActivities = dayRepository.daysWithActivities
        //Log.e("12345676890", dayRepository.daysWithActivities.value.toString())
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

class DayViewModelFactory(private val mApplication: Application, private val date: Date) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DayViewModel(mApplication, date) as T
    }
}
