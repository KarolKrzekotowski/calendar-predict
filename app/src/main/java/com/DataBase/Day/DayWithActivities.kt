package com.DataBase.Day

import androidx.room.Embedded
import androidx.room.Relation
import com.DataBase.Activity.Activity
import com.DataBase.Activity.ActivityWithCategory

data class DayWithActivities(
    @Embedded val day: Day,
    @Relation(
        entity = Activity::class,
        parentColumn = "id",
        entityColumn = "day_id"
    )
    val activityWithCategory: List<ActivityWithCategory>
)

