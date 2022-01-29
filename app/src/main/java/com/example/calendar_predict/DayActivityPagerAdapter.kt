package com.example.calendar_predict

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.DataBase.Category.Category
import com.DataBase.Day.DayViewModel
import com.DataBase.Objective.ObjectiveListViewModel
import com.example.calendar_predict.activityInvitation.Accepted
import com.example.calendar_predict.activityInvitation.ActivityInvitationFragment
import com.example.calendar_predict.activityInvitation.Rejected
import java.util.*

@Suppress("DEPRECATION")
internal class DayActivityPagerAdapter (
    var context: Context,
    fm: FragmentManager,
    var totalTabs: Int,
    val viewModel: DayViewModel,
    val viewModel2: ObjectiveListViewModel,
    val categories: List<Category>,
    val calendar: Calendar
) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                DayAddedActivitiyFragment(viewModel, calendar)
            }
            1 -> {
                GoalsDayFragment(viewModel2)
            }
            2 -> {
                GoalLongtermFragment(viewModel2)
            }
            3 -> {
                GoalAlgorithmFragment(categories)
            }
            4 -> {
                ActivityInvitationFragment()
            }
            5 -> {
               Accepted()
            }
            6 -> {
                Rejected()
            }

            else -> getItem(position)
        }
    }
    override fun getCount(): Int {
        return totalTabs
    }
}