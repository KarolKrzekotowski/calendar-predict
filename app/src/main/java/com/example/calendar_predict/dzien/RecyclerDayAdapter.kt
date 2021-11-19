package com.example.calendar_predict.dzien

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.DataBase.Activity.ActivityWithCategory
import com.example.calendar_predict.R


class RecyclerDayAdapter(): RecyclerView.Adapter<RecyclerDayAdapter.ViewHolder>() {
    var context: Context?=null
    private var dayActivitiesList = emptyList<ActivityWithCategory>()



    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var poczatek: TextView =itemView.findViewById(R.id.poczatek)
        var koniec: TextView = itemView.findViewById(R.id.koniec)
        var nazwa: TextView = itemView.findViewById(R.id.nazwa)

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)

        val DayView = inflater.inflate(R.layout.today_layout, parent, false)


        return ViewHolder(DayView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val activity: ActivityWithCategory= dayActivitiesList[position]
        val name = viewHolder.nazwa
        val start = viewHolder.poczatek
        val end = viewHolder.koniec

        name.text = activity.activity.name
        start.text = activity.activity.hour_from.toString()
        end.text = activity.activity.hour_to.toString()



    }

    override fun getItemCount(): Int {
        return dayActivitiesList.size
    }

    fun setData(data: List<ActivityWithCategory>){
        this.dayActivitiesList = data
        notifyDataSetChanged()
    }
    fun getData(position: Int): ActivityWithCategory {
        return dayActivitiesList[position]
    }

}