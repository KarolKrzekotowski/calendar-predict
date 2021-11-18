package com.DataBase.Activity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*


@Parcelize
@Entity(tableName = "activity_table")
data class Activity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var day_id: Int,
    var category_id: Int,
    var hour_from: Date,
    var hour_to: Date,
    var name: String
): Parcelable
