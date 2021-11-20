package com.example.calendar_predict.dzien

import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

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
            //Todo przygotuj ładunek do bazy i zapisz


            finish()
        }
    }
}