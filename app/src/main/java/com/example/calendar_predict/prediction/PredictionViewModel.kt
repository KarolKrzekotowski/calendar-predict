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
import com.example.calendar_predict.MygoalsDayRecyclerViewAdapter
import kotlin.collections.ArrayList
import com.example.calendar_predict.prediction.Tools
import kotlinx.android.synthetic.main.statistics_page.*
import java.util.*

class PredictionViewModel(application: Application): AndroidViewModel(application) {

    private lateinit var weights : DoubleArray
    private var bias = 0.0
    val tool = Tools()
    var appDBDao: AppDBDao = AppDataBase.getDatabase(
        application
    ).appDBDao()



    fun forwardPropogate(x: DoubleArray): Double {
        return tool.dot(this.weights, x) + bias
    }

    private fun optimizeParameters( gradients : ArrayList<Array<Any>> , learningRate : Double ) {
        val weightGradientsList = ArrayList<DoubleArray>()
        for( gradient in gradients ) {
            weightGradientsList.add( gradient[0] as DoubleArray )
        }
        val weightGradients = tool.multidimMean( weightGradientsList.toTypedArray()  ).toDoubleArray()

        val biasGradientsList = ArrayList<Double>()
        for( gradient in gradients ) {
            biasGradientsList.add( gradient[1] as Double )
        }
        val biasGradients = ( biasGradientsList.toTypedArray() ).average()
        this.weights = tool.subtract( this.weights , tool.multiplyScalar( weightGradients , learningRate ) )
        this.bias = this.bias - ( biasGradients * learningRate )
    }

    fun fit (x : Array<DoubleArray> , y: DoubleArray , epochs : Int , batchSize: Int ) {
        val batches = tool.batch( x , y , batchSize )
        for ( e in 0 until epochs ) {
            for ( batch in batches ) {
                val gradients = ArrayList<Array<Any>>()
                for ( pair in batch ) {
                    val predictions = forwardPropogate( pair.first )
                    gradients.add( tool.calculateGradients( pair.first , predictions , pair.second ) )
                }
                optimizeParameters( gradients , 0.001 )
            }
        }
    }

    fun Predict(dataY: IntArray, dataX: ArrayList<MutableSet<Pair<Int, Int>>>, amountCategory: Int): MutableMap<Int, Int> {
        val listOfList = MutableList(dataX.size) { DoubleArray(amountCategory) }
        val classIndexes = mutableMapOf<Int, Int>()
        var mainCounter = 0
        var counter = 1
        var spam: Pair<Int, Int>
        var category: Int
        var time: Int
        var tmp = mutableListOf<Int>()
        var sumActivityTime = 0
        tmp.add(0)
        for (list in dataX) {
            while (!list.isEmpty()){
                spam = list.drop(1)[0]
                category = spam.first
                time = spam.second
                if (!classIndexes.containsKey(category)) {
                    classIndexes[category] = counter
                    tmp.add(time)
                    sumActivityTime += time
                    counter += 1
                }else {
                    tmp[classIndexes.getValue(category)] += time
                }
            }
            tmp[0] = sumActivityTime
            sumActivityTime = 0
            listOfList[mainCounter] = tmp.map { it.toDouble() }.toDoubleArray()
            tmp = tmp.map { 0 } as MutableList<Int>
            mainCounter += 1
        }

        val newDataY = dataY.map { it.toDouble() }.toDoubleArray()
        fit(listOfList.toTypedArray(), newDataY, 5, 32)
        val result = mutableMapOf<Int, Int>()
        for (idx in classIndexes.keys) {
            result[idx] = weights[classIndexes.getValue(idx)].toInt()
        }
        result[0] = weights[0].toInt()
        return result
    }


    fun preparePredict(): MutableMap<Int, Int> {


        val activityList = appDBDao.getAllActivity()
        val ratingList = appDBDao.getAllRatings()
        var counter = 0

        for (listA in activityList) {
            for (listR in ratingList) {
                if (listA.day_id == listR.day_id) {
                    counter += 1
                }
            }
        }
        // 0,      1,           2,         3,       4
        // day_id, category_id, hour_from, hour_to, rate
        val data = MutableList(counter) { listOf<Any>() }
        for (listA in activityList) {
            for (listR in ratingList) {
                if (listA.day_id == listR.day_id) {
                    data.add(
                        listOf(
                            listA.day_id,
                            listA.category_id,
                            listA.hour_from,
                            listA.hour_to,
                            listR.rate
                        )
                    )
                }
            }
        }

        val Categories = mutableSetOf<Int>()
        for (list in data) {
            Categories.add(list[4] as Int)
        }
        val amountCategory = Categories.size
        val random = Random(1)
        val weights = DoubleArray(amountCategory+1)
        for (x in 0 until amountCategory+1) {
            weights[x] = random.nextFloat().toDouble()
        }
        this.weights = weights

        val doneDays: MutableSet<Int> = mutableSetOf()
        val dataY = IntArray(amountCategory)
        // Para(kategoria, czas)
        val dataX = ArrayList<MutableSet<Pair<Int, Int>>>()
        counter = 0
        var time = 0
        for (list in data) {
            if (!doneDays.contains(list[0])) {
                doneDays.add(list[0] as Int)
                dataY[counter] = list[4] as Int
                time = tool.countTime(list[2] as Date, list[3] as Date)
                dataX[counter].add(Pair(list[1], time) as Pair<Int, Int>)
                counter += 1
            }
        }
        return Predict(dataY, dataX, amountCategory)
    }

}