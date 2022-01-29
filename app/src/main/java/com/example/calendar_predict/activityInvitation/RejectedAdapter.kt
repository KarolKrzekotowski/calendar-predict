package com.example.calendar_predict.activityInvitation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar_predict.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import kotlinx.android.synthetic.main.accepted_invite_list.view.*

class RejectedAdapter (options: FirebaseRecyclerOptions<InviteChoice>): FirebaseRecyclerAdapter<InviteChoice, RejectedAdapter.ViewHolder>(options){
    inner class ViewHolder(invitationView: View) : RecyclerView.ViewHolder(invitationView) {

        val deleteButton: Button = itemView.button17

        var friend : TextView = itemView.friendback
        var name : TextView = itemView.nameback
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RejectedAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.accepted_invite_list,parent,false))
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: InviteChoice) {
        holder.friend.text = model.Friend
        holder.name.text = model.name
        holder.deleteButton.setOnClickListener {
            Rejected.deleteActivity(it,model)
        }
    }
}