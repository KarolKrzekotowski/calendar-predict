package com.DataBase.Day

import android.app.Application
import androidx.lifecycle.*
import com.DataBase.Activity.Activity
import com.DataBase.AppDataBase
import com.DataBase.Category.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class DayUpdateViewModel(application: Application, date: Date): AndroidViewModel(application)  {
    var dayWithActivities: LiveData<DayWithActivities>
    var allCategories: List<Category>
    private val dayUpdateRepository: DayUpdateRepository

    init {
        val appDBDao = AppDataBase.getDatabase(
            application
        ).appDBDao()

        dayUpdateRepository = DayUpdateRepository(appDBDao, date)

        dayWithActivities = dayUpdateRepository.daysWithActivities
        allCategories = dayUpdateRepository.categories
        //Log.e("12345676890", dayRepository.daysWithActivities.value.toString())
    }

    fun addActivity(activity: Activity){
        viewModelScope.launch(Dispatchers.IO){
            dayUpdateRepository.addActivity(activity)
        }
    }

    fun updateActivity(activity: Activity){
        viewModelScope.launch(Dispatchers.IO){
            dayUpdateRepository.updateActivity(activity)
        }
    }

    fun deleteActivity(activity: Activity){
        viewModelScope.launch(Dispatchers.IO){
            dayUpdateRepository.deleteActivity(activity)
        }
    }
}

class DayUpdateViewModelFactory(private val mApplication: Application, private val date: Date) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DayUpdateViewModel(mApplication, date) as T
    }
}