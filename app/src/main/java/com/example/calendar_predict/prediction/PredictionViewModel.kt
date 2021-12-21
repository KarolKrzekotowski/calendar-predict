package com.example.calendar_predict.prediction

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.DataBase.AppDBDao
import com.DataBase.AppDataBase
import kotlin.random.Random
import android.util.Log
import kotlin.collections.ArrayList

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

    fun Predict(dataY: IntArray, dataX: ArrayList<MutableSet<Pair<Int, Int>>>, amountCategory: Int, categoryIndexes: MutableMap<Int, Int>): MutableMap<Int, Int> {
        val listOfList = MutableList(dataX.size) { DoubleArray(amountCategory-1) }
        var mainCounter = 0
        var spam: Pair<Int, Int>
        var category: Int
        var time: Int
        var tmp = mutableListOf<Int>()
        var sumActivityTime = 0
        for (x in 0..amountCategory) {
            tmp.add(0)
        }

        for (list in dataX) {
            for (el in list){
                spam = el
                category = spam.first
                time = spam.second
                tmp[categoryIndexes.getValue(category)] += time
                sumActivityTime += time
            }
            //1440 ilosc minut w dniu
            tmp[0] = (1440 - sumActivityTime)
            sumActivityTime = 0
            listOfList[mainCounter] = tmp.map { it.toDouble() / 1440.0 }.toDoubleArray()
            tmp = tmp.map { 0 } as MutableList<Int>
            mainCounter += 1
        }
        val mean = dataY.sum()/dataY.size
        val newDataY = dataY.map { ((it.toDouble() - mean) / 100.0) }.toDoubleArray()
        fit(listOfList.toTypedArray(), newDataY, 3000, 1)
        val result = mutableMapOf<Int, Int>()
        var plusWeights = weights.filter { it > 0 }.sum()
        for (idx in categoryIndexes.keys) {
            result[idx] = (weights[categoryIndexes.getValue(idx)] * 1000000).toInt()
        }
        for (idx in categoryIndexes.keys) {
            result[idx] = (result.getValue(idx).toDouble() / plusWeights / 1000000.0 * 1440).toInt()
            result[idx] = result.getValue(idx) - result.getValue(idx) % 15
            if (result.getValue(idx) < 0) {
                result.remove(idx)
            }
        }

        result.remove(0)
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
        var data = MutableList(counter) { listOf<Any>() }
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
        var egg = listOf<Any>()

        val Categories = mutableSetOf<Int>()
        counter = 1
        val categoryIndexes = mutableMapOf<Int, Int>()
        data.removeIf { it == egg }
        for (list in data) {
            if (!Categories.contains(list[1])) {
                categoryIndexes[list[1] as Int] = counter
                counter +=1
            }

            Categories.add(list[1] as Int)
        }
        val amountCategory = Categories.size + 1
        val random = Random(1)
        val weights = DoubleArray(amountCategory+1)
        for (x in 0 until amountCategory + 1) {
            weights[x] = random.nextFloat().toDouble()
        }
        this.weights = weights

        var doneDays: MutableSet<Int> = mutableSetOf()

        // Para(kategoria, czas)
        var counterSecond = 0
        for (list in data) {
            if (!doneDays.contains(list[0])) {

                counterSecond += 1
            }
        }
        var dataX = mutableListOf<MutableSet<Pair<Int, Int>>>()

        counter = 0
        var time = 0
        doneDays = mutableSetOf()
        for (list in data) {
            if (!doneDays.contains(list[0])) {
                doneDays.add(list[0] as Int)
                dataX.add(mutableSetOf())
                counter += 1
            }
        }
        val dataY = IntArray(counter)
        doneDays = mutableSetOf()
        counter = 0
        val classIndexes = mutableMapOf<Int, Int>()
        for (list in data) {
            if (!doneDays.contains(list[0])) {
                doneDays.add(list[0] as Int)
                dataY[counter] = list[4] as Int
                time = tool.countTime(list[2] as java.util.Date, list[3] as java.util.Date)
                Log.e("QQQQQQQQQQQQQQQQQQQ", "" + list)
                dataX[counter].add(Pair(list[1], time) as Pair<Int, Int>)
                classIndexes[list[0] as Int] = counter
                counter += 1
            } else {
                dataX[classIndexes.getValue(list[0] as Int)].add(Pair(list[1], tool.countTime(list[2] as java.util.Date, list[3] as java.util.Date)) as Pair<Int, Int>)
            }
        }
        for (el in dataY) {
            Log.e("GGGGGG", el.toString())
        }
        Log.e("ZZZZZZ", dataX.toString() + "" + dataY[0] )
        for (key in classIndexes.keys) {
            classIndexes[key] = classIndexes.getValue(key) + 1
        }
        return Predict(dataY, dataX as ArrayList<MutableSet<Pair<Int, Int>>>, amountCategory, categoryIndexes)
    }

}