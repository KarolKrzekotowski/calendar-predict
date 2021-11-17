package com.DataBase.Objective

import androidx.room.Embedded
import androidx.room.Relation
import com.DataBase.Activity.Activity
import com.DataBase.Category.Category

data class ObjectiveWithCategory(
    @Embedded val objective: Objective,
    @Relation(
        parentColumn = "category_id",
        entityColumn = "id"
    )
    val category: Category
)
