package com.DataBase.Category

import androidx.lifecycle.LiveData
import com.DataBase.AppDBDao
import com.DataBase.Objective.Objective
import com.DataBase.Objective.ObjectiveWithCategory

class CategoryListRepository(private val appDBDao: AppDBDao) {
    val allCategories: LiveData<List<Category>> =
        appDBDao.readAllCategories()

    suspend fun addObjective(objective: Objective) {
        appDBDao.addObjective(objective)
    }

    suspend fun updateObjective(objective: Objective) {
        appDBDao.updateObjective(objective)
    }

//    suspend fun addCategory(category: Category) {
//        appDBDao.addCategory(category)
//    }
//
//    suspend fun updateCategory(category: Category) {
//        appDBDao.updateCategory(category)
//    }
//
//    suspend fun deleteCategory(category: Category) {
//        appDBDao.deleteCategory(category)
//    }
}