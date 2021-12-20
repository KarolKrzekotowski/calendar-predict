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

class GoogleCalendarUpdateViewModel(application: Application): AndroidViewModel(application) {

    private val googleCalendarUpdateRepository: GoogleCalendarUpdateRepository

    init {
        val appDBDao = AppDataBase.getDatabase(
            application
        ).appDBDao()

        googleCalendarUpdateRepository = GoogleCalendarUpdateRepository(appDBDao)
    }

    fun getDaysList(dateFrom: Date, dateTo: Date): List<DayWithActivities>{
            return googleCalendarUpdateRepository.getDaysList(dateFrom, dateTo)
    }
}