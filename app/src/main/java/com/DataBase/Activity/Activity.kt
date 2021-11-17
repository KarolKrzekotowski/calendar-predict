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
    val day_id: Int,
    val category_id: Int,
    val hour_from: Date,
    val hour_to: Date,
    val name: String
): Parcelable
