package com.example.calendar_predict

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.DataBase.Objective.ObjectiveWithCategory
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

class MyGoalLongtermRecyclerViewAdapter()  : RecyclerView.Adapter<MyGoalLongtermRecyclerViewAdapter.ViewHolder>() {
    var context: Context? = null
    private var goalList = emptyList<ObjectiveWithCategory>()
    private lateinit var agregationList: Map<Int, Int>

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val goalNameTextView: TextView = itemView.findViewById<TextView>(R.id.goalNameTextView)
        val amountDoneTextView: TextView = itemView.findViewById<TextView>(R.id.cancelPendingButton)
        val nextDueTextView: TextView = itemView.findViewById<TextView>(R.id.acceptPendingButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyGoalLongtermRecyclerViewAdapter.ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val taskView = inflater.inflate(R.layout.goal, parent, false)

        return ViewHolder(taskView)
    }

    override fun onBindViewHolder(viewHolder: MyGoalLongtermRecyclerViewAdapter.ViewHolder, position: Int) {
        // Get the data model based on position
        val goal: ObjectiveWithCategory = goalList[position]
        // Set item views based on your views and data model
        val title = viewHolder.goalNameTextView
        title.text = goal.category.name

        val amount = viewHolder.amountDoneTextView
        amount.text = ""
        if (agregationList[goal.category.id] != null) {
            amount.text = amount.text.toString() + agregationList[goal.category.id]
        }
        else {
            amount.text = amount.text.toString() + "0"
        }
        amount.text = amount.text.toString() + " / " + goal.objective.targetAmount

        if (agregationList[goal.category.id] == null || agregationList[goal.category.id]!! < goal.objective.targetAmount) {
            amount.setTextColor(Color.RED)
        }
        else {
            amount.setTextColor(Color.GREEN)
        }

        var backgroundColor = Integer.toHexString(goal.category.colour)
        viewHolder.itemView.setBackgroundColor(Color.parseColor("#"+ backgroundColor))

        val nextDue = viewHolder.nextDueTextView

        val secsTillDayEnd = getSecsTillDayEnd()
        val daysTillWeekEnd = getDaysTillWeekEnd()
        //pierwszy dzień następnego miesiąca - minus dziś - 1
        val daysTillMonthEnd = getDaysTillMonthEnd()

        nextDue.text = when(goal.objective.kind) {
            GoalKind.DAY.string -> "Pozostało ${secsTillDayEnd / 3600} godzin i ${(secsTillDayEnd % 3600) / 60} minut"
            GoalKind.WEEK.string -> "Pozostało $daysTillWeekEnd dni i ${secsTillDayEnd / 3600} godzin"
            GoalKind.MONTH.string -> "Pozostało $daysTillMonthEnd dni i ${secsTillDayEnd / 3600} godzin"
            else -> ""
        }
    }

    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return goalList.size
    }

    fun getSecsTillDayEnd(): Int {
        return LocalTime.of(23, 59, 59).toSecondOfDay() - LocalTime.now().toSecondOfDay()
    }

    fun getDaysTillWeekEnd(): Int {
        return  7 - LocalDate.now().dayOfWeek.value
    }

    fun getDaysTillMonthEnd(): Long {
        val date = LocalDate.now()
        return LocalDate.of(date.year + date.monthValue / 12, (date.monthValue) % 12 + 1, 1).toEpochDay() - date.toEpochDay() - 1
    }

    fun getGoal(position: Int): ObjectiveWithCategory {
        return goalList[position]
    }

    fun setData(data: List<ObjectiveWithCategory>, aggregated: Map<Int, Int>){
        val tmp = mutableListOf<ObjectiveWithCategory>()
        for (int in data)
        {
            if (int.objective.kind != GoalKind.DAY.string)
            {
                if(int.objective.date_to == null || (int.objective.date_to != null && int.objective.date_to!! > Date.from(Instant.now())))
                    tmp.add(int)
            }
        }
        this.goalList = tmp
        this.agregationList = aggregated

        notifyDataSetChanged()
    }
}