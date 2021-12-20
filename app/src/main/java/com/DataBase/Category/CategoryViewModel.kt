package com.DataBase.Category

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.DataBase.AppDataBase
import com.DataBase.Objective.Objective
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application): AndroidViewModel(application) {

    val allCategories: List<Category>
    private val categoryListRepository: CategoryListRepository

    init {
        val appDBDao = AppDataBase.getDatabase(
            application
        ).appDBDao()

        categoryListRepository = CategoryListRepository(appDBDao)
        allCategories = categoryListRepository.allCategories
    }

    fun addObjective(objective: Objective){
        viewModelScope.launch(Dispatchers.IO){
            categoryListRepository.addObjective(objective)
        }
    }

    fun updateObjective(objective: Objective){
        viewModelScope.launch(Dispatchers.IO){
            categoryListRepository.updateObjective(objective)
        }
    }

}