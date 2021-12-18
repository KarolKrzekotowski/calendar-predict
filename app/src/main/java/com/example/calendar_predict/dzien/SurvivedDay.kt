package com.example.calendar_predict.dzien

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.DataBase.Day.DayViewModel
import com.DataBase.Day.DayViewModelFactory
import com.example.calendar_predict.databinding.SurvivedDayBinding
import kotlinx.android.synthetic.main.edit_day.*
import kotlinx.android.synthetic.main.edit_day.EdycjaDniaS
import kotlinx.android.synthetic.main.survived_day.*
import java.util.Calendar

var day:String?=null
var month:String?=null
var year:String?=null
var together:String?=null


class SurvivedDay : AppCompatActivity() {
    private lateinit var adapter: SurvivedDayAdapter
    lateinit var  dayViewModel: DayViewModel
    private lateinit var binding: SurvivedDayBinding
    val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SurvivedDayBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        init()

    }
    private fun init(){

        val datereceived =intent.extras
        year = datereceived?.getString("year")
        day = datereceived?.getString("day")
        month = datereceived?.getString("month")
        together =(day.toString()+" "+(month!!.toInt()+1).toString()+ "  "+ year.toString())
        EdycjaDniaS.text = together

        val rvSurvived = dayEditRecyclerS
        adapter = SurvivedDayAdapter()
        rvSurvived.adapter = adapter
        rvSurvived.layoutManager = LinearLayoutManager(this)


        calendar[Calendar.YEAR] = year!!.toInt()
        calendar[Calendar.MONTH] = month!!.toInt()
        calendar[Calendar.DAY_OF_MONTH] = day!!.toInt()
        calendar[Calendar.HOUR_OF_DAY] = 0
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0


        val factory = DayViewModelFactory(application, calendar.time)
        dayViewModel = ViewModelProvider(this, factory).get(DayViewModel::class.java)

        dayViewModel.dayWithActivities.observe(this,{it->
            //Log.e("1234456780", it.activityWithCategory)
            if (it != null) {
                adapter.setData(it.activityWithCategory.sortedBy { it.activity.hour_from })
            }
        })





    }
    fun Exit(view: View){
        finish()
    }
}