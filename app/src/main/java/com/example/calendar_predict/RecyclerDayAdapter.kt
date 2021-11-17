package com.example.calendar_predict

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.DataBase.Activity.ActivityWithCategory

class RecyclerDayAdapter(): RecyclerView.Adapter<RecyclerDayAdapter.ViewHolder>() {

    private var activitiesList = emptyList<ActivityWithCategory>()

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var poczatek: TextView
        var koniec: TextView
        var nazwa: TextView

        init{
            poczatek=itemView.findViewById(R.id.poczatek)
            koniec = itemView.findViewById(R.id.koniec)
            nazwa = itemView.findViewById(R.id.nazwa)
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerDayAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val LeadsView = inflater.inflate(R.layout.today_layout, parent, false)
        return ViewHolder(LeadsView)
    }

    override fun onBindViewHolder(holder: RecyclerDayAdapter.ViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.poczatek).text =activitiesList[position].activity.hour_from.toString()
        holder.itemView.findViewById<TextView>(R.id.koniec).text=activitiesList[position].activity.hour_to.toString()
        holder.itemView.findViewById<TextView>(R.id.nazwa).text=activitiesList[position].activity.name
    }

    override fun getItemCount(): Int {
        return activitiesList.size
    }

    fun setData(data: List<ActivityWithCategory>){
        this.activitiesList = data
        notifyDataSetChanged()
    }

}