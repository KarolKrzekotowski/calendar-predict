package com.example.calendar_predict

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class GoalAdapter(private val goalList: MutableList<Goal>)  : RecyclerView.Adapter<GoalAdapter.ViewHolder>() {
    var context: Context? = null

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {

        val goalNameTextView: TextView = itemView.findViewById<TextView>(R.id.goalNameTextView)
        val amountDoneTextView: TextView = itemView.findViewById<TextView>(R.id.amountDoneTextView)
        val nextDueTextView: TextView = itemView.findViewById<TextView>(R.id.nextDueTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalAdapter.ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val taskView = inflater.inflate(R.layout.goal, parent, false)


        // Return a new holder instance
        return ViewHolder(taskView)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: GoalAdapter.ViewHolder, position: Int) {
        // Get the data model based on position
        val goal: Goal = goalList[position]
        // Set item views based on your views and data model
        val title = viewHolder.goalNameTextView
        title.text = goal.name
        val amount = viewHolder.amountDoneTextView
        amount.text = goal.amountDone.toString() + " / " + goal.targetAmount
        if (goal.amountDone < goal.targetAmount) {
            amount.setTextColor(Color.RED)
        }
        else {
            amount.setTextColor(Color.GREEN)
        }

        val nextDue = viewHolder.nextDueTextView
        nextDue.text = goal.kind.toString()

        viewHolder.itemView.setOnLongClickListener{
            Toast.makeText(context, "You triggered me! ~" + goal.name + "LongClickListener", Toast.LENGTH_LONG).show()
            return@setOnLongClickListener true
        }
    }

    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return goalList.size
    }

    fun addGoalToList(goal: Goal) {
        goalList.add(goal)
    }

    fun getGoalList(): MutableList<Goal> {
        return goalList
    }

    fun sortByTitle() {
        goalList.sortWith { t1, t2 ->
            t1.name.compareTo(t2.name)
        }
    }
}