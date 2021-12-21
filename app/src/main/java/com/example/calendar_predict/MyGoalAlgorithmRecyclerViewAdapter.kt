package com.example.calendar_predict

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.DataBase.Category.Category
import com.DataBase.Category.CategoryViewModel
import com.DataBase.Objective.Objective
import com.DataBase.Objective.ObjectiveWithCategory
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

class MyGoalAlgorithmRecyclerViewAdapter(val categories: List<Category>)  : RecyclerView.Adapter<MyGoalAlgorithmRecyclerViewAdapter.ViewHolder>() {
    var context: Context? = null
    private var goalList = emptyList<ObjectiveWithCategory>()
    private lateinit var aggregated: Map<Int, Int>
    private lateinit var categoryViewModel: CategoryViewModel

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val goalNameTextView: TextView = itemView.findViewById<TextView>(R.id.goalNameTextViewAlgorithm)
        val amountDoneTextView: TextView = itemView.findViewById<TextView>(R.id.amountDoneTextViewAlgorithm)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyGoalAlgorithmRecyclerViewAdapter.ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val taskView = inflater.inflate(R.layout.algorithm_goal, parent, false)

        return ViewHolder(taskView)
    }

    override fun onBindViewHolder(viewHolder: MyGoalAlgorithmRecyclerViewAdapter.ViewHolder, position: Int) {
        // Get the data model based on position
        val goal: ObjectiveWithCategory = goalList[position]
        // Set item views based on your views and data model
        val title = viewHolder.goalNameTextView
        title.text = goal.category.name
        val amount = viewHolder.amountDoneTextView
        amount.text = "" + aggregated[goal.category.id] + " / " + goal.objective.targetAmount

        if (15 < goal.objective.targetAmount) {
            amount.setTextColor(Color.RED)
        }
        else {
            amount.setTextColor(Color.GREEN)
        }

        var backgroundColor = Integer.toHexString(goal.category.colour)
        viewHolder.itemView.setBackgroundColor(Color.parseColor("#"+ backgroundColor))
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

    fun setData(data: Map<Int, Int>, agg: Map<Int, Int>) {
        val tmp = mutableListOf<ObjectiveWithCategory>()

        for (category in categories)
        {
            val obj = data[category.id]?.let {
                Objective(0,0, Calendar.getInstance().time, Calendar.getInstance().time, GoalKind.DAY.string, it)
            }
            if (obj != null)
            {
                val tmpp = ObjectiveWithCategory(obj, category)
                tmp.add(tmpp)
            }
        }
        this.goalList = tmp
        this.aggregated = agg

        notifyDataSetChanged()
    }
}