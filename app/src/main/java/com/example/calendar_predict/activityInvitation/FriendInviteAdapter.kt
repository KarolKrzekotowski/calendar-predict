package com.example.calendar_predict.activityInvitation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar_predict.Friend
import com.example.calendar_predict.FriendsAdapter
import com.example.calendar_predict.R
import kotlinx.android.synthetic.main.friend_picker.view.*

class FriendInviteAdapter : RecyclerView.Adapter<FriendInviteAdapter.ViewHolder>() {
    var context: Context? = null
    private var friendsList = mutableListOf<Friend>()
//    private lateinit var agregationList: Map<Int, Int>

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val friendNameTextView: TextView = itemView.Friend2
        val theChosenButton: Button = itemView.thechosen
//        val nextDueTextView: TextView = itemView.findViewById<TextView>(R.id.nextDueTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendInviteAdapter.ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val taskView = inflater.inflate(R.layout.friend_picker, parent, false)

        return ViewHolder(taskView)
    }



    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return friendsList.size
    }

    fun getFriend(position: Int): String {
        return friendsList[position].name
    }


    fun setData(friends: MutableList<Friend>){
        this.friendsList = friends
//        this.agregationList = aggregated

        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val friend = friendsList[position]
        // Set item views based on your views and data model
        val title = holder.friendNameTextView
        title.text = friend.name

        holder.theChosenButton.id = position + 1
    }
}