package com.example.calendar_predict.statistics

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Transformations.map
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.DataBase.AppDataBase
import com.example.calendar_predict.GoalAdapter
import com.example.calendar_predict.MygoalsDayRecyclerViewAdapter
import com.example.calendar_predict.R
import java.sql.Date
import kotlin.collections.ArrayList
import kotlin.random.Random
import com.example.calendar_predict.prediction.Tools

class PredictionClass : Fragment() {
    private lateinit var adapter: GoalAdapter
    private var weights : DoubleArray
    private var bias = 0.0
    val tool = Tools()

    lateinit var appDBDao: AppDataBase

    init {
        appDBDao = AppDataBase.getDatabase(
            requireContext()
        ).appDBDao()
        val random = Random(1)
        val weights = DoubleArray(1)
        for (x in 0 until 1) {
            weights[x] = random.nextFloat().toDouble()
        }
        this.weights = weights
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.daily_goal_list, container, false)

        val rvTask = view.findViewById<RecyclerView>(R.id.dailylist)
        val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        decoration.setDrawable(ColorDrawable(Color.WHITE))
        rvTask.addItemDecoration(decoration)

        val adapter = MygoalsDayRecyclerViewAdapter()
        rvTask.adapter = adapter

        rvTask.layoutManager = LinearLayoutManager(requireContext())

        //TODO: nie zaciągać przeterminowanych celów
        viewModel.allObjectiveWithCategory.observe(viewLifecycleOwner, {
            adapter.setData(it)
        })
        return view
    }

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
        var classIndexes = mutableMapOf<Int, Int>()
        var mainCounter = 0
        var counter = 0
        var spam: Pair<Int, Int>
        var category: Int
        var time: Int
        var tmp = mutableListOf<Int>()
        // sorry ale wciaz trzeba prepare data
        for (list in dataX) {
            while (!list.isEmpty()){
                spam = list.drop(1)[0]
                category = spam.first
                time = spam.second
                if (!classIndexes.containsKey(category)) {
                    classIndexes[category] = counter
                    tmp.add(time)
                    counter += 1
                }else {
                    tmp[classIndexes.getValue(category)] += time
                }

            }
            listOfList[mainCounter] = tmp.map { it.toDouble() }.toDoubleArray()
            tmp.clear()
            mainCounter += 1
        }
        val newDataY = dataY.map { it.toDouble() }.toDoubleArray()
        fit(listOfList.toTypedArray(), newDataY, 5, 32)
        val result = mutableMapOf<Int, Int>()
        for (idx in classIndexes.keys) {
            result[idx] = weights[classIndexes.getValue(idx)].toInt()
        }
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
        val weights = DoubleArray(amountCategory)
        for (x in 0 until amountCategory) {
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