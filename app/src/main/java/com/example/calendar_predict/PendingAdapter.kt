package com.example.calendar_predict

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.DataBase.Objective.ObjectiveWithCategory
import java.time.LocalDate
import java.time.LocalTime

class PendingAdapter : RecyclerView.Adapter<PendingAdapter.ViewHolder>() {
    var context: Context? = null
    private var friendsList = emptyList<Friend>()
//    private lateinit var agregationList: Map<Int, Int>

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val friendNameTextView: TextView = itemView.findViewById<TextView>(R.id.friendNameTextView)
        val acceptButton: Button = itemView.findViewById<Button>(R.id.acceptPendingButton)
        val cancelButton: Button = itemView.findViewById<Button>(R.id.cancelPendingButton)
//        val amountDoneTextView: TextView = itemView.findViewById<TextView>(R.id.amountDoneTextView)
//        val nextDueTextView: TextView = itemView.findViewById<TextView>(R.id.nextDueTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingAdapter.ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val taskView = inflater.inflate(R.layout.pending, parent, false)

        return ViewHolder(taskView)
    }

    override fun onBindViewHolder(viewHolder: PendingAdapter.ViewHolder, position: Int) {
        // Get the data model based on position
        val friend = friendsList[position]
        // Set item views based on your views and data model
        val title = viewHolder.friendNameTextView
        title.text = friend.name
        viewHolder.acceptButton.id = position
        viewHolder.cancelButton.id = position
    }

    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return friendsList.size
    }




    fun getFriend(position: Int): Friend {
        return friendsList[position]
    }

    fun setData(friends: List<Friend>){
        this.friendsList = friends
//        this.agregationList = aggregated

        notifyDataSetChanged()
    }
}