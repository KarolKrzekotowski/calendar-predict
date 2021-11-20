package com.example.calendar_predict.dzien

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import com.example.calendar_predict.R
import kotlinx.android.synthetic.main.calendar_page.*

class Calendar :Fragment() {
    var datawyznaczona:String?=null

    var year=""
    var month = 0
    var dayOfMonth =""
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

            val intent = Intent(getActivity(),EditDay::class.java)
            intent.putExtra("day",dayOfMonth)
            intent.putExtra("month",(month+1).toString())
            intent.putExtra("year",year)
            startActivity(intent)


        }




        return view
    }





}