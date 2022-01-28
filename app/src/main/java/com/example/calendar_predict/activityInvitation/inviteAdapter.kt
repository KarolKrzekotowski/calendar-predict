package com.example.calendar_predict.activityInvitation

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar_predict.DayAddedActivitiyFragment
import com.example.calendar_predict.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

import kotlinx.android.synthetic.main.received_invitation.view.*
import java.util.*

class inviteAdapter(options: FirebaseRecyclerOptions<Invitation>): FirebaseRecyclerAdapter<Invitation, inviteAdapter.ViewHolder>(
    options
) {
    var calendar = Calendar.getInstance()
    var calendar2 = Calendar.getInstance()

    inner class ViewHolder(invitationView:View) : RecyclerView.ViewHolder(invitationView) {
        val activity: TextView = itemView.WhatsThat
        val acceptButton : ImageButton = itemView.Accept
        val declineButton : ImageButton = itemView.decline
        val friend : TextView = itemView.textView7

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): inviteAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.received_invitation,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Invitation) {
        holder.itemView.setBackgroundColor(Color.parseColor("#e7e7e7"))
        holder.activity.text =model.name
        holder.acceptButton.id = position+1
        holder.acceptButton.setOnClickListener{
            ActivityInvitationFragment.AcceptActivity(it, model )


        }

        holder.declineButton.id = holder.acceptButton.id+ 10000
        holder.declineButton.setOnClickListener{
            ActivityInvitationFragment.deleteActivity(it,model)
        }
        calendar.timeInMillis=model.From.toLong()
        calendar2.timeInMillis=model.To.toLong()
        var day = calendar[Calendar.DAY_OF_MONTH].toString() +" "+ (calendar[Calendar.MONTH]+1).toString() +" "+calendar[Calendar.YEAR].toString()+
                "\n"+ calendar[Calendar.HOUR].toString() + " " + calendar[Calendar.MINUTE].toString()+ "\n"+
                calendar2[Calendar.HOUR].toString() + " " + calendar2[Calendar.MINUTE].toString()
        holder.friend.text = model.Friend + "\n" + day

    }


    fun deleteFriend(position: Int) {

        notifyDataSetChanged()
    }

}