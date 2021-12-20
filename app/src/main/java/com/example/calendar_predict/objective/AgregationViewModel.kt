package com.example.calendar_predict.prediction

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.DataBase.AppDBDao
import com.DataBase.AppDataBase
import com.DataBase.Objective.Objective
import com.example.calendar_predict.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Date
import kotlin.random.Random
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.AdapterView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.Transformations.map
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar_predict.GoalAdapter
import com.example.calendar_predict.GoalKind
import com.example.calendar_predict.MygoalsDayRecyclerViewAdapter
import kotlin.collections.ArrayList
import com.example.calendar_predict.prediction.Tools
import kotlinx.android.synthetic.main.statistics_page.*
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