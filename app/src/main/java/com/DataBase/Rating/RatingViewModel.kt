package com.DataBase.Rating

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.DataBase.AppDataBase
import com.DataBase.Category.Category
import com.DataBase.Category.CategoryListRepository
import com.DataBase.Day.Day
import com.DataBase.Objective.Objective
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RatingViewModel(application: Application): AndroidViewModel(application) {
    private val ratingRepository: RatingRepository

    init {
        val appDBDao = AppDataBase.getDatabase(
            application
        ).appDBDao()

        ratingRepository = RatingRepository(appDBDao)
    }

    fun updateDay(day: Day){
        viewModelScope.launch(Dispatchers.IO){
            ratingRepository.updateDay(day)
        }
    }

    fun addRating(rating: Rating){
        viewModelScope.launch(Dispatchers.IO){
            ratingRepository.addRating(rating)
        }
    }

}