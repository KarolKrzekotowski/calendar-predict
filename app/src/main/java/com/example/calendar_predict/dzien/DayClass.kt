package com.example.calendar_predict.dzien

import android.content.Intent
import android.os.Bundle
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
import com.DataBase.Day.DayViewModel
import com.DataBase.Day.DayViewModelFactory
import com.example.calendar_predict.R
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
        val dzionek = Calendar.getInstance()
        val godzina =dzionek[Calendar.HOUR_OF_DAY]
        val minuta = dzionek[Calendar.MINUTE]

//        dayViewModel = DayViewModelFactory(calendar.time)
        val factory = DayViewModelFactory(requireActivity().application, calendar.time)
        dayViewModel = ViewModelProvider(this, factory).get(DayViewModel::class.java)

//        var activity = Activity(0,2,1,May 04 09:51:52 CDT 2009,13 )
        dayViewModel.dayWithActivities.observe(viewLifecycleOwner, Observer { data ->
            if (data != null) {
                adapter.setData(data.activityWithCategory.sortedBy { it.activity.hour_from })
            }
        })




        val datka:TextView = view.findViewById(R.id.dzien)

        datka.setText((godzina.toString() +" : "+ minuta.toString()))

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





}