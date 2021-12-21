package com.example.calendar_predict.prediction

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.DataBase.AppDBDao
import com.DataBase.AppDataBase
import com.DataBase.Objective.Objective
import com.example.calendar_predict.GoalKind
import java.util.*

class AgregationViewModel(application: Application): AndroidViewModel(application) {

    var appDBDao: AppDBDao = AppDataBase.getDatabase(
        application
    ).appDBDao()




    fun prepareAgregate(): MutableMap<Int, Int> {

        val calendar1 = Calendar.getInstance()
        val objectiveList = appDBDao.getAllObjective(calendar1.time)

        val Categories = mutableSetOf<Int>()
        for (list in objectiveList) {
            Categories.add(list.category_id)
        }
        val categoryAmount = Categories.size
        var result = mutableMapOf<Int, Int>()
        for (list in objectiveList) {
            Categories.add(list.category_id)
        }
        var classIndexes = mutableMapOf<Int, Int>()
        var category = 0
        for (list in objectiveList) {
            category = list.category_id
            if (!classIndexes.containsKey(category)) {
                result[category] = countTime(list)
            }else {
                result[category] = result.getValue(category) + countTime(list)
            }
        }
        return result
    }

    fun countTime(list: Objective): Int {
        if (list.kind ==  GoalKind.DAY.string) {
            return list.targetAmount
        }else if (list.kind == GoalKind.WEEK.string) {
            return list.targetAmount/7
        }else {
            return list.targetAmount/30
        }
    }

}