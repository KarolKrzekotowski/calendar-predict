package com.example.calendar_predict

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.calendar_predict.dzien.Calendar
import com.example.calendar_predict.dzien.DayClass
import com.example.calendar_predict.statistics.StatisticsClass

@Suppress("DEPRECATION")
internal class MyPagerAdapter(
    var context: Context,
    fm: FragmentManager,
    var totalTabs: Int
) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                DayClass()
            }
            1 -> {
                Calendar()
            }
            2 ->{
                StatisticsClass()
            }


            else -> getItem(position)
        }
    }
    override fun getCount(): Int {
        return totalTabs
    }
}