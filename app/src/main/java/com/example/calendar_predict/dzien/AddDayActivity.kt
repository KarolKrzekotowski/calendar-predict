package com.example.calendar_predict.dzien

import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.DataBase.Activity.Activity
import com.DataBase.Day.DayUpdateViewModel
import com.DataBase.Day.DayUpdateViewModelFactory


import com.example.calendar_predict.R
import kotlinx.android.synthetic.main.add_activity.*
import java.util.Calendar



class AddDayActivity: AppCompatActivity() {

    var hour = 0
    var minute = 0
    var hour2 = 0
    var minute2 = 0
    var day:String?=null
    var month:String?=null
    var together:String?=null
    var name:String?=""
    var hour_from = ""
    var hour_to = ""
    var category = ""

    var calendar = Calendar.getInstance()

    lateinit var dayUpdateViewModel: DayUpdateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_activity)



        val datereceived =intent.extras
        day = datereceived?.getString("day")
        month = datereceived?.getString("month")
        together =(day.toString()+" "+month.toString()).toString()
        calendardate.text = together
        Log.i("123456","$day")
        Log.i("123456","$month")





        pickTimeButton.setOnClickListener{

            val timepicker = TimePickerDialog(this,{view, mHour, mMinute ->
               hour = mHour
               minute = mMinute
               start.setText(displayCorrectTime(hour,minute))
            },minute,hour,true)
            timepicker.show()
        }

        pickTimeButton2.setOnClickListener{

            val timepicker2 = TimePickerDialog(this,{view, kHour, kMinute ->
                hour2 = kHour
                minute2 = kMinute
                zakonczenie.setText(displayCorrectTime(hour2,minute2))
            },minute2,hour2,true)
            timepicker2.show()
        }

        calendar[Calendar.HOUR_OF_DAY] = 0
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0

        val factory = DayUpdateViewModelFactory(application, calendar.time)
        dayUpdateViewModel = ViewModelProvider(this, factory).get(DayUpdateViewModel::class.java)
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

    fun addActivity(view: View){
        name = nameofactivity.text.toString()
        hour_from = start.text.toString()
        hour_to = zakonczenie.text.toString()
        category = mark.text.toString()
        if (hour>hour2 || (hour==hour2 && minute> minute2)  ){
            Toast.makeText(this, "Błędne godziny ", Toast.LENGTH_SHORT).show()

        }
        else if(name ==""){
            Toast.makeText(this, "Brak nazwy ", Toast.LENGTH_SHORT).show()
        }
        else if(category==""){
            Toast.makeText(this, "Brak kategorii ", Toast.LENGTH_SHORT).show()
        }
        else {

            val calendarFrom = Calendar.getInstance()
            //calendarFrom[Calendar.YEAR] = year!!.toInt()
            calendarFrom[Calendar.MONTH] = month!!.toInt()
            calendarFrom[Calendar.DAY_OF_MONTH] = day!!.toInt()
            calendarFrom[Calendar.HOUR_OF_DAY] = 0
            calendarFrom[Calendar.MINUTE] = 0
            calendarFrom[Calendar.SECOND] = 0
            calendarFrom[Calendar.MILLISECOND] = 0

            val calendarTo = Calendar.getInstance()
            //calendarTo[Calendar.YEAR] = year!!.toInt()
            calendarTo[Calendar.MONTH] = month!!.toInt()
            calendarTo[Calendar.DAY_OF_MONTH] = day!!.toInt()
            calendarTo[Calendar.HOUR_OF_DAY] = 0
            calendarTo[Calendar.MINUTE] = 0
            calendarTo[Calendar.SECOND] = 0
            calendarTo[Calendar.MILLISECOND] = 0


            var activity = Activity(0,  dayUpdateViewModel.day.id, 1, calendarFrom.time, calendarTo.time, name!!)
            dayUpdateViewModel.addActivity(activity)


            finish()
        }
    }
}