package com.DataBase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.DataBase.Activity.Activity
import com.DataBase.Category.Category
import com.DataBase.Day.Day
import com.DataBase.Day.DayWithActivities
import com.DataBase.Objective.Objective
import com.DataBase.Objective.ObjectiveWithCategory
import com.DataBase.Rating.Rating
import java.util.*
import kotlin.collections.ArrayList

@Dao
interface AppDBDao {
    //CATEGORY
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCategory(category: Category)

    @Update
    suspend fun updateCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("select * from category_table")
    fun readAllCategories(): List<Category>

    //Objective
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addObjective(objective: Objective)

    @Update
    suspend fun updateObjective(objective: Objective)

    @Delete
    suspend fun deleteObjective(objective: Objective)
    /*
        @Query("select * from objective_table O " +
                "join category_table T on O.category_id = T.id")
     */
    @Transaction
    @Query("select * from objective_table")
    fun readAllObjectivesWithCategories(): LiveData<List<ObjectiveWithCategory>>


    //Activity
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addActivity(activity: Activity)

    @Update
    suspend fun updateActivity(activity: Activity)


    @Delete
    suspend fun deleteActivity(activity: Activity)

    //Day
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDay(day: Day)

    @Update
    suspend fun updateDay(day: Day)


    @Delete
    suspend fun deleteDay(day: Day)

    @Transaction
    @Query("select * from day_table where date = :date")
    fun readDay(date: Date): Day?

    @Transaction
    @Query("select * from day_table where date = :date")
    fun readDayWithActivities(date: Date):LiveData<DayWithActivities>


    @Transaction
    @Query("select * from day_table")
    fun readAllDaysWithActivities():LiveData<List<DayWithActivities>>

    @Transaction
    @Query("select * from day_table where date between :dateFrom and :dateTo")
    fun readDaysWithActivities(dateFrom: Date, dateTo: Date):List<DayWithActivities>

    @Transaction
    @Query("select * from day_table")
    fun readAllDays():LiveData<List<Day>>

//Rating

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRating(rating: Rating)

//Statistics
    @Query("select * from activity_table where hour_from >= :date")
    fun readAllActivitieswithCategories(date: Date): List<Activity>

    @Query("select * from rating_table as r, day_table as d where d.date>= :date and  d.evaluated =1 and d.id =r.day_id")
    fun readAllRates(date: Date): List<Rating>

//Prediction & objective agreg

    @Query("select * from activity_table")
    fun getAllActivity(): List<Activity>

    @Query("select * from rating_table")
    fun getAllRatings(): List<Rating>

    @Query("select * from objective_table as o where o.date_to <= :date")
    fun getAllObjective(date: Date): List<Objective>

}
