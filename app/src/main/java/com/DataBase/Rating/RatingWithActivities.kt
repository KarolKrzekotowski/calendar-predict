package com.DataBase.Rating

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import com.DataBase.Activity.Activity
import com.DataBase.Category.Category
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RatingWithActivities(
    @Embedded val rating: Rating,
    @Relation(
        parentColumn = "day_id",
        entityColumn = "day_id"
    )
    val activities: List<Activity>
): Parcelable
