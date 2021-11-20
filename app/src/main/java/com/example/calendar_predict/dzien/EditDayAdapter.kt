package com.example.calendar_predict.dzien

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.DataBase.Activity.ActivityWithCategory
import com.example.calendar_predict.R

class EditDayAdapter(): RecyclerView.Adapter<EditDayAdapter.ViewHolder>() {

    private var DayEditActivitiesList = emptyList<ActivityWithCategory>()
    var context: Context ?= null
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var poczatek: TextView =itemView.findViewById(R.id.poczatek)
        var koniec: TextView = itemView.findViewById(R.id.zakonczenie)
        var nazwa: TextView = itemView.findViewById(R.id.nazwa)

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditDayAdapter.ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)

        val DayView = inflater.inflate(R.layout.today_layout,parent,false)
        return ViewHolder(DayView)
    }

    override fun onBindViewHolder(viewHolder: EditDayAdapter.ViewHolder, position: Int) {
        val activity: ActivityWithCategory= DayEditActivitiesList[position]
        val name = viewHolder.nazwa
        val start = viewHolder.poczatek
        val end = viewHolder.koniec

        name.text = activity.activity.name
        start.text = activity.activity.hour_from.toString()
        end.text = activity.activity.hour_to.toString()

        viewHolder.itemView.setOnLongClickListener{
            EditDay.showPopup(it, activity )

            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int {
        return DayEditActivitiesList.size
    }

    fun setData(data: List<ActivityWithCategory>){
        this.DayEditActivitiesList = data
        notifyDataSetChanged()
    }

    fun getData(position: Int): ActivityWithCategory {
        return DayEditActivitiesList[position]
    }

}