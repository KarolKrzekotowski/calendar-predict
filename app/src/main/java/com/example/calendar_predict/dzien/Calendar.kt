package com.example.calendar_predict.dzien

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.DataBase.Day.DayUpdateViewModel
import com.DataBase.Day.DayUpdateViewModelFactory
import com.example.calendar_predict.R
import kotlinx.android.synthetic.main.calendar_page.*
import java.util.Calendar

class Calendar :Fragment() {
    var datawyznaczona:String?=null

    var year=""
    var month = 0
    var dayOfMonth =""
    private lateinit var dayUpdateViewModel: DayUpdateViewModel
    var calendar = Calendar.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.calendar_page, container,false)



        var daychanangecalendar:CalendarView =view.findViewById(R.id.calendarViewpage)
        daychanangecalendar.setOnDateChangeListener{ view, myear, mmonth, mdayOfMonth ->
            year = myear.toString()
            month = mmonth
            dayOfMonth = mdayOfMonth.toString()
            Log.i("halo",mdayOfMonth.toString())
            calendar[Calendar.DAY_OF_MONTH] = dayOfMonth.toInt()
            calendar[Calendar.MONTH] = month.toInt()
            calendar[Calendar.YEAR] = year.toInt()
            calendar[Calendar.HOUR_OF_DAY] = 0
            calendar[Calendar.MINUTE] = 0
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0

            val factory = DayUpdateViewModelFactory(requireActivity().application, calendar.time)
            dayUpdateViewModel =
                ViewModelProvider(this, factory).get(DayUpdateViewModel::class.java)
            Log.i("evaluacja", dayOfMonth.toString()+month)
            Log.i("evaluacja", dayUpdateViewModel.day.evaluated.toString())
            if (dayUpdateViewModel.day.evaluated==1){
                Toast.makeText(context,"Dzień został już oceniony, brak możliwości edycji",Toast.LENGTH_SHORT).show()
            }
            else {

                val intent = Intent(getActivity(), EditDay::class.java)
                intent.putExtra("day", dayOfMonth.toString())
                //intent.putExtra("month",(month+1).toString())
                intent.putExtra("month", month.toString())
                intent.putExtra("year", year.toString())
                Log.i("dacisko",(dayOfMonth.toString() + month).toString())
                startActivity(intent)
            }


        }




        return view
    }





}