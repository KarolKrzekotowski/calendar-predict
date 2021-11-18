package com.DataBase.Rating

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "rating_table")
data class Rating(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var day_id: Int,
    var rate : Int
): Parcelable
