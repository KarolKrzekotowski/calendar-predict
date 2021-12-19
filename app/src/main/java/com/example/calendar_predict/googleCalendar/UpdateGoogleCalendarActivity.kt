package com.example.calendar_predict.googleCalendar
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle

import android.util.Log
import android.view.View
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import com.example.calendar_predict.R
import java.util.*
import android.provider.CalendarContract
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.DataBase.Day.DayWithActivities
import com.DataBase.Day.GoogleCalendarUpdateViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class UpdateGoogleCalendarActivity : AppCompatActivity(){

    lateinit var dataPickerDateFrom : DatePicker
    lateinit var dataPickerDateTo : DatePicker
    private val PERMISSION_REQUEST_CODE = 200
    private val PERMISSION_REQUEST_CODE_2 = 201
    lateinit var googleCalendarUpdateViewModel: GoogleCalendarUpdateViewModel

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_google_calendar)
        dataPickerDateFrom= findViewById<DatePicker>(R.id.datePickerDateFrom)
        dataPickerDateTo = findViewById<DatePicker>(R.id.datePickerDateTo)

        googleCalendarUpdateViewModel = ViewModelProvider(this).get(GoogleCalendarUpdateViewModel::class.java)


    }

    fun updateGoogleCalendar(view: View) {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_CALENDAR), PERMISSION_REQUEST_CODE)
        } else if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_CALENDAR), PERMISSION_REQUEST_CODE_2)
        }
        else {
            val dateFrom = java.util.Calendar.getInstance()
            dateFrom[java.util.Calendar.YEAR] = dataPickerDateFrom.year
            dateFrom[java.util.Calendar.MONTH] = dataPickerDateFrom.month
            dateFrom[java.util.Calendar.DAY_OF_MONTH] = dataPickerDateFrom.dayOfMonth
            dateFrom[java.util.Calendar.HOUR_OF_DAY] = 0
            dateFrom[java.util.Calendar.MINUTE] = 0
            dateFrom[java.util.Calendar.SECOND] = 0
            dateFrom[java.util.Calendar.MILLISECOND] = 0


            val dateTo = java.util.Calendar.getInstance()
            dateTo[java.util.Calendar.YEAR] = dataPickerDateTo.year
            dateTo[java.util.Calendar.MONTH] = dataPickerDateTo.month
            dateTo[java.util.Calendar.DAY_OF_MONTH] = dataPickerDateTo.dayOfMonth
            dateTo[java.util.Calendar.HOUR_OF_DAY] = 0
            dateTo[java.util.Calendar.MINUTE] = 0
            dateTo[java.util.Calendar.SECOND] = 0
            dateTo[java.util.Calendar.MILLISECOND] = 0


            //Log.d(null, dateFrom.time.toString() + " "+ dateTo.time.toString())
            val listToUpdate = googleCalendarUpdateViewModel.getDaysList(dateFrom.time , dateTo.time)



            lifecycleScope.launch(Dispatchers.IO) {

                val calId = getCalendarId(applicationContext)
                for (dayWithActivities in listToUpdate) {

                    var calendarDay = Calendar.getInstance();
                    calendarDay.time = dayWithActivities.day.date

                    for (activityWithCategory in dayWithActivities.activityWithCategory) {


                            var calendarFrom = Calendar.getInstance();
                            calendarFrom.time = activityWithCategory.activity.hour_from

                            var calendarTo = Calendar.getInstance();
                            calendarTo.time = activityWithCategory.activity.hour_to


                            Log.d(null, activityWithCategory.activity.name + " "+ calendarDay.get(Calendar.YEAR) + " "+ calendarDay.get(Calendar.MONTH)+ " " +calendarDay.get(Calendar.DAY_OF_MONTH))
                        val startMillis: Long = Calendar.getInstance().run {
                            set(calendarDay.get(Calendar.YEAR), calendarDay.get(Calendar.MONTH), calendarDay.get(Calendar.DAY_OF_MONTH), calendarFrom.get(Calendar.HOUR), calendarFrom.get(Calendar.MINUTE))
                            timeInMillis
                        }
                        val endMillis: Long = Calendar.getInstance().run {
                            set(calendarDay.get(Calendar.YEAR), calendarDay.get(Calendar.MONTH), calendarDay.get(Calendar.DAY_OF_MONTH), calendarTo.get(Calendar.HOUR), calendarTo.get(Calendar.MINUTE))
                            timeInMillis
                        }


                        val values = ContentValues().apply {
                            put(CalendarContract.Events.DTSTART, startMillis)
                            put(CalendarContract.Events.DTEND, endMillis)
                            put(CalendarContract.Events.TITLE, activityWithCategory.category.name)
                            put(CalendarContract.Events.DESCRIPTION, activityWithCategory.activity.name)
                            put(CalendarContract.Events.CALENDAR_ID, calId)
                            put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().id)
                        }
                        contentResolver.insert(CalendarContract.Events.CONTENT_URI, values)
                    }
                }
            }
            finish()
        }
    }
}

private fun getCalendarId(context: Context) : Long? {
    val projection = arrayOf(CalendarContract.Calendars._ID, CalendarContract.Calendars.CALENDAR_DISPLAY_NAME)

    var calCursor = context.contentResolver.query(
        CalendarContract.Calendars.CONTENT_URI,
        projection,
        CalendarContract.Calendars.VISIBLE + " = 1 AND " + CalendarContract.Calendars.IS_PRIMARY + "=1",
        null,
        CalendarContract.Calendars._ID + " ASC"
    )

    if (calCursor != null && calCursor.count <= 0) {
        calCursor = context.contentResolver.query(
            CalendarContract.Calendars.CONTENT_URI,
            projection,
            CalendarContract.Calendars.VISIBLE + " = 1",
            null,
            CalendarContract.Calendars._ID + " ASC"
        )
    }

    if (calCursor != null) {
        if (calCursor.moveToFirst()) {
            val calName: String
            val calID: String
            val nameCol = calCursor.getColumnIndex(projection[1])
            val idCol = calCursor.getColumnIndex(projection[0])

            calName = calCursor.getString(nameCol)
            calID = calCursor.getString(idCol)

            Log.d(null, "Calendar name = $calName Calendar ID = $calID")

            calCursor.close()
            return calID.toLong()
        }
    }
    return null
}


