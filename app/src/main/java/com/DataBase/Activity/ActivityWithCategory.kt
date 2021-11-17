package com.DataBase.Activity

import androidx.room.Embedded
import androidx.room.Relation
import com.DataBase.Category.Category
import com.DataBase.Objective.Objective

data class ActivityWithCategory(
    @Embedded val activity: Activity,
    @Relation(
        parentColumn = "category_id",
        entityColumn = "id"
    )
    val category: Category
)
