package com.example.calendar_predict.dzien

import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.DataBase.Activity.Activity
import com.DataBase.Activity.ActivityWithCategory
import com.DataBase.Day.DayUpdateViewModel
import com.DataBase.Day.DayUpdateViewModelFactory
import com.example.calendar_predict.GoalsCategorySpinner
import com.example.calendar_predict.GoalsCategorySpinnerAdapter


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
    var aktywnosc : ActivityWithCategory?= null
    var godzina1=0
    var godzina2 = 0
    var minuta1 = 0
    var minuta2 = 0
    var calendarFrom2 =Calendar.getInstance()
    var calendarTo2 = Calendar.getInstance()
    var editMode = false
    var editId =0

    private var spinnerList: MutableList<GoalsCategorySpinner> = mutableListOf()

    var calendar = Calendar.getInstance()

    lateinit var dayUpdateViewModel: DayUpdateViewModel
    lateinit var spinner: Spinner


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_activity)
        val factory = DayUpdateViewModelFactory(application, calendar.time)
        dayUpdateViewModel = ViewModelProvider(this, factory).get(DayUpdateViewModel::class.java)

        val categories = dayUpdateViewModel.allCategories
        for (category in categories) {
            spinnerList.add(GoalsCategorySpinner(category))
        }
        spinner = findViewById(R.id.spinner2)
        spinner.adapter = GoalsCategorySpinnerAdapter(this, spinnerList)

        val datereceived =intent.extras
        if (datereceived!=null) {

            day = datereceived?.getString("day")
            month = datereceived?.getString("month")
            aktywnosc = datereceived?.getParcelable("activity")
            together = (day+" "+month)
            if(aktywnosc!=null) {

                editMode = true

                calendarFrom2.time = aktywnosc!!.activity.hour_from
                godzina1 = calendarFrom2[Calendar.HOUR_OF_DAY]
                minuta1 = calendarFrom2[Calendar.MINUTE]
                day =calendarFrom2[Calendar.DAY_OF_MONTH].toString()
                month = calendarFrom2[Calendar.MONTH].toString()
                editId = aktywnosc!!.activity.id

                calendarTo2.time = aktywnosc!!.activity.hour_to
                godzina2 = calendarTo2[Calendar.HOUR_OF_DAY]
                minuta2 = calendarTo2[Calendar.MINUTE]
                start.setText(displayCorrectTime(godzina1, minuta1))
                zakonczenie.setText((displayCorrectTime(godzina2, minuta2)))

                nameofactivity.setText(aktywnosc!!.activity.name)

                spinner.setSelection(aktywnosc!!.activity.category_id)
                calendardate.text =
                    (calendarFrom2[Calendar.DAY_OF_MONTH].toString() + "  " + calendarFrom2[Calendar.MONTH].toString())
                hour = godzina1
                hour2 = godzina2
                minute = minuta1
                minute2 = minuta2
            }
            else{
                calendardate.setText(together)
                calendar[Calendar.MONTH] =month!!.toInt()
                calendar[Calendar.DAY_OF_MONTH] = day!!.toInt()

            }
            calendar[Calendar.HOUR_OF_DAY] = 0
            calendar[Calendar.MINUTE] = 0
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0
            Log.i("tutaj","jestem")

        }





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
        Log.i("od",hour.toString())
        if (hour>hour2 || (hour==hour2 && minute> minute2)){
            Toast.makeText(this, "Błędne godziny ", Toast.LENGTH_SHORT).show()

        }
        else if(name ==""){
            Toast.makeText(this, "Brak nazwy ", Toast.LENGTH_SHORT).show()
        }
        else {


                val calendarFrom = Calendar.getInstance()
                //calendarFrom[Calendar.YEAR] = year!!.toInt()
                calendarFrom[Calendar.MONTH] = month!!.toInt()
                calendarFrom[Calendar.DAY_OF_MONTH] = day!!.toInt()
                calendarFrom[Calendar.HOUR_OF_DAY] = hour.toInt()
                calendarFrom[Calendar.MINUTE] = minute.toInt()
                calendarFrom[Calendar.SECOND] = 0
                calendarFrom[Calendar.MILLISECOND] = 0

                val calendarTo = Calendar.getInstance()
                //calendarTo[Calendar.YEAR] = year!!.toInt()
                calendarTo[Calendar.MONTH] = month!!.toInt()
                calendarTo[Calendar.DAY_OF_MONTH] = day!!.toInt()
                calendarTo[Calendar.HOUR_OF_DAY] = hour2
                calendarTo[Calendar.MINUTE] = minute2.toInt()
                calendarTo[Calendar.SECOND] = 0
                calendarTo[Calendar.MILLISECOND] = 0
                if (editMode == false) {

                var activity = Activity(
                    0,
                    dayUpdateViewModel.day.id,
                    spinner.selectedItemPosition,
                    calendarFrom.time,
                    calendarTo.time,
                    name!!
                )
                dayUpdateViewModel.addActivity(activity)


                finish()
                }
                else{
                   var activity = Activity(editId,dayUpdateViewModel.day.id,spinner.selectedItemPosition,calendarFrom.time,calendarTo.time,name!!)
                    dayUpdateViewModel.addActivity(activity)
                    finish()
                }




        }
    }
}