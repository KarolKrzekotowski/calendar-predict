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
    val category_id: Int,
    val date_from : Date,
    val date_to : Date,
    val kind : Char,
    val targetAmount: Int
): Parcelable
