package com.example.calendar_predict

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.DataBase.Day.DayViewModel

@Suppress("DEPRECATION")
internal class DayActivityPagerAdapter (
    var context: Context,
    fm: FragmentManager,
    var totalTabs: Int,
    val viewModel: DayViewModel
) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                DayAddedActivitiyFragment(viewModel)
            }
            1 -> {
                GoalsDayFragment()
            }
            2 -> {
                GoalLongtermFragment()
            }
            3 -> {
                GoalAlgorithmFragment()
            }

            else -> getItem(position)
        }
    }
    override fun getCount(): Int {
        return totalTabs
    }
}