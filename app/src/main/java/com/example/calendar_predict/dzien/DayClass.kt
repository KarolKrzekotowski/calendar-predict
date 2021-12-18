package com.example.calendar_predict.dzien

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.DataBase.Day.DayViewModel
import com.DataBase.Day.DayViewModelFactory
import com.example.calendar_predict.DayAddedActivitiyFragment
import com.example.calendar_predict.MyPagerAdapter
import com.example.calendar_predict.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.day2_page.*
import java.time.LocalDateTime
import java.util.*

import java.util.Calendar

class DayClass : Fragment() {

    private lateinit var dayViewModel: DayViewModel

    var recyclerView: RecyclerView?=null
    var calendar = Calendar.getInstance()
    var day = calendar.get(Calendar.DAY_OF_MONTH)
    var month = calendar.get(Calendar.MONTH)
    var year = calendar.get(Calendar.YEAR)
    private lateinit var adapter: RecyclerView.Adapter<RecyclerDayAdapter.ViewHolder>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.day2_page, container, false)
        instance = this

        adapter = RecyclerDayAdapter()
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = adapter

//        var day_id = dayViewModel.dayWithActivities.value?.day?.id

        calendar[Calendar.HOUR_OF_DAY] = 0
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0
        val dzionek = Calendar.getInstance()
        val godzina =dzionek[Calendar.HOUR_OF_DAY]
        val minuta = dzionek[Calendar.MINUTE]

//        dayViewModel = DayViewModelFactory(calendar.time)
        val factory = DayViewModelFactory(requireActivity().application, calendar.time)
        dayViewModel = ViewModelProvider(this, factory).get(DayViewModel::class.java)

//        var activity = Activity(0,2,1,May 04 09:51:52 CDT 2009,13 )
        dayViewModel.dayWithActivities.observe(viewLifecycleOwner, Observer { data ->
            if (data != null) {
                (adapter as RecyclerDayAdapter).setData(data.activityWithCategory.sortedBy { it.activity.hour_from })
            }
        })




        val datka:TextView = view.findViewById(R.id.dzien)

        datka.setText(displayCorrectTime(godzina,minuta))

        val edycja:Button = view.findViewById(R.id.kurczak)
        edycja.setOnClickListener{

            val intent = Intent(getActivity(),EditDay::class.java)
            intent.putExtra("day",day.toString())
            //intent.putExtra("month",(month+1).toString())
            intent.putExtra("month",(month).toString())
            intent.putExtra("year",year.toString())
            intent.putExtra("edycja","1")
            startActivity(intent)
        }



        return view
    }
    fun displayCorrectTime(hour : Int,minute : Int): String{

        var minuteString = minute.toString()
        var hourString = hour.toString()

        if(minute<10){
            minuteString = "0$minute"
        }
        when {
            hour<10 -> hourString = "0$hour"
            hour>23 -> {
                hourString = "00"
            }
        }
        return "$hourString:$minuteString"
    }


    companion object
    {
        lateinit var instance: DayClass
        fun getViewmodel(calendar: Calendar): DayViewModel
        {
            instance.calendar = calendar
            val factory = DayViewModelFactory(instance.requireActivity().application, instance.calendar.time)
            instance.dayViewModel = ViewModelProvider(instance, factory).get(DayViewModel::class.java)

            Log.i("calendar", "moja data " + instance.calendar.time)
            DayAddedActivitiyFragment.setModel(instance.dayViewModel)
            return instance.dayViewModel
        }
        fun getCalendar(): Calendar
        {
            return instance.calendar
        }

    }


}