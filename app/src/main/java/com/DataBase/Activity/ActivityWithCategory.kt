package com.DataBase.Activity

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import com.DataBase.Category.Category
import com.DataBase.Objective.Objective
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ActivityWithCategory(
    @Embedded val activity: Activity,
    @Relation(
        parentColumn = "category_id",
        entityColumn = "id"
    )
    val category: Category
): Parcelable
