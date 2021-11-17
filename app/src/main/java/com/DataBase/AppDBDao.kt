package com.DataBase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.DataBase.Category.Category
import com.DataBase.Objective.Objective
import com.DataBase.Objective.ObjectiveWithCategory

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

    //CATEGORY
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addObjective(objective: Objective)

    @Update
    suspend fun updateObjective(objective: Objective)

    @Delete
    suspend fun deleteObjective(objective: Objective)

    @Query("select * from objective_table O " +
            "join category_table T on O.category_id = T.id")
    fun readAllObjectivesWithCategories(): LiveData<List<ObjectiveWithCategory>>

//    @Query("select * from category_table where id in (:categories)")
//    suspend fun loadCategories(categories: List<Category>): List<Category>
}
