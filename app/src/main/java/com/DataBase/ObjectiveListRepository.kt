package com.DataBase

import androidx.lifecycle.LiveData
import com.DataBase.Objective.Objective
import com.DataBase.Objective.ObjectiveWithCategory

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