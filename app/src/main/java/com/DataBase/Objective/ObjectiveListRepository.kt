package com.DataBase.Objective

import androidx.lifecycle.LiveData
import com.DataBase.AppDBDao

class ObjectiveListRepository(private val appDBDao: AppDBDao) {
    val allObjectivesWithCategory: LiveData<List<ObjectiveWithCategory>> = appDBDao.readAllObjectivesWithCategories()

    suspend fun addObjective(objective: Objective){
        appDBDao.addObjective(objective)
    }

    suspend fun updateObjective(objective: Objective){
        appDBDao.updateObjective(objective)
    }

    suspend fun deleteObjective(objective: Objective){
        appDBDao.deleteObjective(objective)
    }
}