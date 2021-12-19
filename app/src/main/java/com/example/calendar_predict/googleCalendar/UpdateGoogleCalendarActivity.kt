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
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_CALENDAR), PERMISSION_REQUEST_CODE)
        }
        else {
            val dateFrom = java.util.Calendar.getInstance()
            dateFrom[java.util.Calendar.YEAR] = dataPickerDateFrom.dayOfMonth
            dateFrom[java.util.Calendar.MONTH] = dataPickerDateFrom.month
            dateFrom[java.util.Calendar.DAY_OF_MONTH] = dataPickerDateFrom.year
            dateFrom[java.util.Calendar.HOUR_OF_DAY] = 0
            dateFrom[java.util.Calendar.MINUTE] = 0
            dateFrom[java.util.Calendar.SECOND] = 0
            dateFrom[java.util.Calendar.MILLISECOND] = 0


            val dateTo = java.util.Calendar.getInstance()
            dateFrom[java.util.Calendar.YEAR] = dataPickerDateTo.dayOfMonth
            dateFrom[java.util.Calendar.MONTH] = dataPickerDateTo.month
            dateFrom[java.util.Calendar.DAY_OF_MONTH] = dataPickerDateTo.year
            dateFrom[java.util.Calendar.HOUR_OF_DAY] = 0
            dateFrom[java.util.Calendar.MINUTE] = 0
            dateFrom[java.util.Calendar.SECOND] = 0
            dateFrom[java.util.Calendar.MILLISECOND] = 0

            val listToUpdate = googleCalendarUpdateViewModel.getDaysList(dateFrom.time , dateTo.time)



            lifecycleScope.launch(Dispatchers.IO) {
                val calId = getCalendarId(applicationContext)
                for (dayWithActivities in listToUpdate) {
                    for (activityWithCategory in dayWithActivities.activityWithCategory) {
                        val startMillis: Long = Calendar.getInstance().run {
                            set(dayWithActivities.day.date.year, dayWithActivities.day.date.month, dayWithActivities.day.date.day, activityWithCategory.activity.hour_from.hours, activityWithCategory.activity.hour_from.minutes)
                            timeInMillis
                        }
                        val endMillis: Long = Calendar.getInstance().run {
                            set(dayWithActivities.day.date.year, dayWithActivities.day.date.month, dayWithActivities.day.date.day, activityWithCategory.activity.hour_to.hours, activityWithCategory.activity.hour_to.minutes)
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


