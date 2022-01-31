package com.example.calendar_predict.dzien

import android.app.Activity.RESULT_OK
import android.app.TimePickerDialog
import android.app.appsearch.AppSearchResult.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsSpinner
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.DataBase.Activity.Activity
import com.DataBase.Activity.ActivityWithCategory
import com.DataBase.Day.DayUpdateViewModel
import com.DataBase.Day.DayUpdateViewModelFactory
import com.DataBase.Day.DayViewModel
import com.DataBase.Day.DayViewModelFactory
import com.example.calendar_predict.GoalsCategorySpinner
import com.example.calendar_predict.GoalsCategorySpinnerAdapter
import com.example.calendar_predict.MainActivity


import com.example.calendar_predict.R
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.add_activity.*
import java.lang.Thread.sleep
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
    var aktywnosc : ActivityWithCategory?= null
    var godzina1=0
    var godzina2 = 0
    var minuta1 = 0
    var minuta2 = 0
    var calendarFrom2 =Calendar.getInstance()
    var calendarTo2 = Calendar.getInstance()
    var editMode = false
    var editId =0
    var dateConflict = false
    lateinit var dayViewModel:DayViewModel
    private var DayEditActivitiesList = emptyList<ActivityWithCategory>()
    var FriendCalendarFrom = Calendar.getInstance()
    var FriendCalendarTo = Calendar.getInstance()
    var FriendFrom :String ?= null
    var FriendTo :String ?= null
    var FriendActivity :String?= null
    var calendar = Calendar.getInstance()
    var Friend = false
    var InviteTime: String ?= null
    var FriendName : String ?=null
    ///////////////////////////////////////////////////////
    private var spinnerList: MutableList<GoalsCategorySpinner> = mutableListOf()

    lateinit var dayUpdateViewModel: DayUpdateViewModel
    lateinit var  spinner: Spinner
    val interval =15
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_activity)




        val datereceived =intent.extras
        if (datereceived!=null) {

            day = datereceived?.getString("day")
            month = datereceived?.getString("month")
            aktywnosc = datereceived?.getParcelable("activity")
            FriendFrom = datereceived?.getString("calendar1")
            FriendTo = datereceived?.getString("calendar2")
            FriendActivity = datereceived?.getString("nameofactivity")
            together = (day+" "+(month?.toInt()?.plus(1)).toString())
            InviteTime = datereceived?.getString("sentTime")
            FriendName = datereceived?.getString("FriendName")
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

//                mark.setText(aktywnosc!!.activity.category_id.toString())
                calendardate.text =
                        (calendarFrom2[Calendar.DAY_OF_MONTH].toString() + "  " + calendarFrom2[Calendar.MONTH].toString())
                hour = godzina1
                hour2 = godzina2
                minute = minuta1
                minute2 = minuta2
                calendar.time = calendarFrom2.time
            }

            else if (FriendFrom != null && FriendTo != null){
                Friend =true

                calendarFrom2.timeInMillis = FriendFrom!!.toLong()
                godzina1 = calendarFrom2[Calendar.HOUR_OF_DAY]
                minuta1 = calendarFrom2[Calendar.MINUTE]
                day =calendarFrom2[Calendar.DAY_OF_MONTH].toString()
                month = calendarFrom2[Calendar.MONTH].toString()

                calendarTo2.timeInMillis = FriendTo!!.toLong()
                godzina2 = calendarTo2[Calendar.HOUR_OF_DAY]
                minuta2 = calendarTo2[Calendar.MINUTE]

                start.setText(displayCorrectTime(godzina1, minuta1))
                zakonczenie.setText((displayCorrectTime(godzina2, minuta2)))

                nameofactivity.setText(FriendActivity)

                calendardate.text =
                    (calendarFrom2[Calendar.DAY_OF_MONTH].toString() + "  " + calendarFrom2[Calendar.MONTH].toString())
                hour = godzina1
                hour2 = godzina2
                minute = minuta1
                minute2 = minuta2
                calendar.time = calendarFrom2.time

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

            val factory = DayUpdateViewModelFactory(application, calendar.time)
            dayUpdateViewModel = ViewModelProvider(this, factory).get(DayUpdateViewModel::class.java)

            val factory1 = DayViewModelFactory(application,calendar.time)
            dayViewModel = ViewModelProvider(this,factory1).get(DayViewModel::class.java)

            dayViewModel.dayWithActivities.observe(this,{it ->
                DayEditActivitiesList = it.activityWithCategory
            })

            ////////////////////////////////
            val categories = dayUpdateViewModel.allCategories
            for (category in categories) {
                spinnerList.add(GoalsCategorySpinner(category))
            }
            spinner= findViewById(R.id.spinner2)
            spinner.adapter = GoalsCategorySpinnerAdapter(this, spinnerList)

        }





        pickTimeButton.setOnClickListener{


                var     timepicker = TimePickerDialog(this,{view, mHour, mMinute ->
                hour = mHour
                minute = (mMinute/interval) * interval
                    if (minute ==60) {
                        minute = 0
                    }
                start.setText(displayCorrectTime(hour,minute))
            },hour,minute,true)

            timepicker.show()
        }

        pickTimeButton2.setOnClickListener{

            val timepicker2 = TimePickerDialog(this,{view, kHour, kMinute ->
                hour2 = kHour

                minute2 = (kMinute/interval) * interval
                if (minute2 ==60) {
                    minute2 = 0
                }
                zakonczenie.setText(displayCorrectTime(hour2,minute2))
            },hour2,minute2,true)
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

    fun addActivity(view: View) {
        name = nameofactivity.text.toString()
        hour_from = start.text.toString()
        hour_to = zakonczenie.text.toString()
//        category = mark.text.toString()
        category = 1.toString()

        if (hour > hour2 || (hour == hour2 && minute > minute2)) {
            Toast.makeText(this, "Błędne godziny ", Toast.LENGTH_SHORT).show()

        } else if (name == "") {
            Toast.makeText(this, "Brak nazwy ", Toast.LENGTH_SHORT).show()
        } else if (category == "") {
            Toast.makeText(this, "Brak kategorii ", Toast.LENGTH_SHORT).show()
        } else {


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

                var activity1 = Activity(
                    0,
                    dayUpdateViewModel.day.id,
                    dayUpdateViewModel.allCategories[spinner.selectedItemPosition].id,
                    calendarFrom.time,
                    calendarTo.time,
                    name!!
                )
                for (list in DayEditActivitiesList) {
                    if ((activity1.hour_from < list.activity.hour_from && activity1.hour_to <= list.activity.hour_from) ||
                        (activity1.hour_from > list.activity.hour_from && activity1.hour_from >= list.activity.hour_to)
                    ) {
                        continue
                    } else {
                        dateConflict = true
                        break
                    }
                }

                if (Friend == true) {
                    if (dateConflict) {
                        Toast.makeText(
                            this,
                            "Zmień plan i ponów dodanie aktywności2",
                            Toast.LENGTH_SHORT
                        ).show()
                        val myintent = Intent()
                        val success = false
                        myintent.putExtra("success", success)
                        setResult(RESULT_OK, myintent)
                        finish()

                    } else {



                        dayUpdateViewModel.updateActivity(activity1)

                        val myRef = MainActivity.getMyRef()
                        val firedatabase = Firebase.database("https://calendar-predict-default-rtdb.europe-west1.firebasedatabase.app/")
                        val friendRef  = firedatabase.getReference("users")
                        val friendEmail = FriendName.toString()
                        val fireFriendEmail = friendEmail.replace('.',' ')
//                        myRef.child("invite").child(InviteTime!!).removeValue()

                        friendRef.child(fireFriendEmail).child("Positive").child(InviteTime.toString()).child("name").setValue(name)
                        friendRef.child(fireFriendEmail).child("Positive").child(InviteTime.toString()).child("Friend").setValue(FriendName)
                        friendRef.child(fireFriendEmail).child("Positive").child(InviteTime.toString()).child("Sent").setValue(InviteTime.toString())

                        val myintent = Intent()
                        val success = true
                        myintent.putExtra("success", success)
                        setResult(RESULT_OK, myintent)

                        finish()

                    }
                }
                    if (dateConflict) {
                        Toast.makeText(
                            this,
                            "Zmień plan i ponów dodanie aktywności1",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {

                        dayUpdateViewModel.addActivity(activity1)
                        finish()
                    }


                } else {
                    var counterOfConflict = 0
                    var activity2 = Activity(
                        editId,
                        dayUpdateViewModel.day.id,
                        dayUpdateViewModel.allCategories[spinner.selectedItemPosition].id,
                        calendarFrom.time,
                        calendarTo.time,
                        name!!
                    )

                    for (list in DayEditActivitiesList) {


                        if ((activity2.hour_from < list.activity.hour_from && activity2.hour_to <= list.activity.hour_from) ||
                            (activity2.hour_from > list.activity.hour_from && activity2.hour_from >= list.activity.hour_to)
                        ) {
                            continue

                        } else if (activity2.id != list.activity.id) {
                            dateConflict = true

                        } else {
                            counterOfConflict++

                        }
                    }


                    if (dateConflict) {
                        Toast.makeText(
                            this,
                            "Zmień plan i ponów dodanie aktywności2",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        dayUpdateViewModel.updateActivity(activity2)
                        finish()
                    }


                }


            }
        }
    }
