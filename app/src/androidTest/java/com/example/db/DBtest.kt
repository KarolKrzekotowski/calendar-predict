package com.example.db

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.DataBase.Activity.Activity
import com.DataBase.AppDataBase
import com.DataBase.Day.Day
import com.DataBase.Rating.Rating
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Test
import java.util.*


class DBtest {
    @Test
    @Throws(Exception::class)
     fun addActivity() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        val db = Room.inMemoryDatabaseBuilder(
            context, AppDataBase::class.java).build()
        val dbDao = db.appDBDao()
        val activity = Activity(id = 0, day_id = 1, category_id = 3, hour_from = Date(), hour_to = Date(), name = "test 1")

        dbDao.addActivityT(activity)


        val activities = dbDao.getAllActivity()

        assertThat(activities[0].day_id, equalTo(activity.day_id))
        assertThat(activities[0].category_id, equalTo(activity.category_id))
        assertThat(activities[0].hour_from, equalTo(activity.hour_from))
        assertThat(activities[0].hour_to, equalTo(activity.hour_to))
        assertThat(activities[0].name, equalTo(activity.name))

        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun readCategories() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        val dbDao = AppDataBase.getDatabase(
            context
        ).appDBDao()

        val categories = dbDao.readAllCategories()

        assert(categories.size == 7)
    }

    @Test
    @Throws(Exception::class)
    fun days() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        val db = Room.inMemoryDatabaseBuilder(
            context, AppDataBase::class.java).build()
        val dbDao = db.appDBDao()

        val date = Date()
        val day = Day(id = 0, date = date, evaluated = 0)

        dbDao.addDay(day)

        val dayRead = dbDao.readDay(date)!!

        assert(dayRead.date == date)
        assert(dayRead.evaluated == 0)

        day.evaluated = 1

        for(i in 1..100){
            dbDao.addDay(day)
        }

        val daysRead = dbDao.readAllDaysT()

        assert(daysRead.size == 101)
    }

    @Test
    @Throws(Exception::class)
    fun dayWithActivity() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        val dbDao = AppDataBase.getDatabase(
            context
        ).appDBDao()
        val date = Date()
        val day = Day(id = 0, date = date, evaluated = 0)

        dbDao.addDay(day)

        val dayRead = dbDao.readDay(date)!!

        val activity1 = Activity(id = 0, day_id = dayRead.id, category_id = 3, hour_from = date, hour_to = date, name = "test 2")
        val activity2 = Activity(id = 0, day_id = dayRead.id, category_id = 4, hour_from = date, hour_to = date, name = "test 3")

        dbDao.addActivityT(activity1)
        dbDao.addActivityT(activity2)

        val daysWithActivities = dbDao.readDaysWithActivities(date, date)

        assert(daysWithActivities.size == 1)

        assert(daysWithActivities[0].day.date == date)

        assert(daysWithActivities[0].activityWithCategory.size == 2)

        //category.id = 3 >> category.name Sport
        //category.id = 4 >> category.name Nauka
        assert(daysWithActivities[0].activityWithCategory[0].category.name == "Sport")
        assert(daysWithActivities[0].activityWithCategory[0].activity.name == "test 2")
        assert(daysWithActivities[0].activityWithCategory[1].category.name == "Nauka")
        assert(daysWithActivities[0].activityWithCategory[1].activity.name == "test 3")
    }

    @Test
    @Throws(Exception::class)
    fun rating() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        val db = Room.inMemoryDatabaseBuilder(
            context, AppDataBase::class.java).build()
        val dbDao = db.appDBDao()
        var date = Date()
        var rating = Rating(id = 0, day_id = 1, rate = 97)
        dbDao.addRating(rating)
        rating = Rating(id = 0, day_id = 1, rate = 44)
        dbDao.addRating(rating)
        rating = Rating(id = 0, day_id = 1, rate = 33)
        dbDao.addRating(rating)

        val rateRead = dbDao.getAllRatings()

        assert(rateRead.size == 3)

        var sum = 0

        for(rate in rateRead){
            sum += rate.rate
        }

        assert(sum == 97+44+33)

        db.close()
    }
}