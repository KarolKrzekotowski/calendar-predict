package com.DataBase.Objective

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*


@Parcelize
@Entity(tableName = "objective_table")
data class Objective(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var category_id: Int,
    var date_from : Date,
    var date_to : Date,
    var kind : String,
    var targetAmount: Int
): Parcelable
