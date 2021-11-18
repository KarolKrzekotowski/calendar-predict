package com.DataBase.Objective

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import com.DataBase.Activity.Activity
import com.DataBase.Category.Category
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ObjectiveWithCategory(
    @Embedded val objective: Objective,
    @Relation(
        parentColumn = "category_id",
        entityColumn = "id"
    )
    val category: Category
): Parcelable
