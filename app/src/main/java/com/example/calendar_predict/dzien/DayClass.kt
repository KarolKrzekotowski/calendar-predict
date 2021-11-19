package com.example.calendar_predict.dzien

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.DataBase.Day.DayViewModel
import com.DataBase.Day.DayViewModelFactory
import com.example.calendar_predict.R
import java.util.*


class DayClass : Fragment() {

    private lateinit var dayViewModel: DayViewModel

    var recyclerView: RecyclerView?=null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.day2_page, container, false)


        val adapter = RecyclerDayAdapter()
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = adapter

//        var day_id = dayViewModel.dayWithActivities.value?.day?.id

        val calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = 0
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0

//        dayViewModel = DayViewModelFactory(calendar.time)
        val factory = DayViewModelFactory(requireActivity().application, calendar.time)
        dayViewModel = ViewModelProvider(this, factory).get(DayViewModel::class.java)

//        var activity = Activity(0,2,1,May 04 09:51:52 CDT 2009,13 )
        dayViewModel.dayWithActivities.observe(viewLifecycleOwner, Observer { data ->

            adapter.setData(data.activityWithCategory)

        })


        return view
    }


    fun EndOfTheDay(view: View){

    }

    fun EditDay(view: View){

    }

}