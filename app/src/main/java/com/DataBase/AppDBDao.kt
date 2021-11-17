package com.DataBase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.DataBase.Activity.Activity
import com.DataBase.Category.Category
import com.DataBase.Day.Day
import com.DataBase.Day.DayWithActivities
import com.DataBase.Objective.Objective
import com.DataBase.Objective.ObjectiveWithCategory
import java.util.*

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
    fun readAllCategories(): LiveData<List<Category>>

    //Objective
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addObjective(objective: Objective)

    @Update
    suspend fun updateObjective(objective: Objective)

    @Delete
    suspend fun deleteObjective(objective: Objective)

    @Transaction
    @Query("select * from objective_table O " +
            "join category_table T on O.category_id = T.id")
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
    suspend fun addDay(day: Day)

    @Update
    suspend fun updateDay(day: Day)


    @Delete
    suspend fun deleteDay(day: Day)

    @Transaction
    @Query("select * from day_table where date = :date")
    fun readDayWithActivities(date: Date):LiveData<DayWithActivities>

    @Transaction
    @Query("select * from day_table")
    fun readAllDaysWithActivities():LiveData<List<DayWithActivities>>

//    @Query("select * from category_table where id in (:categories)")
//    suspend fun loadCategories(categories: List<Category>): List<Category>
}
