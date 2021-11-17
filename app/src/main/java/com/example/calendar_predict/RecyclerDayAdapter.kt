package com.example.calendar_predict

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerDayAdapter(): RecyclerView.Adapter<RecyclerDayAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var poczatek: TextView
        var koniec: TextView
        var nazwa: TextView

        init{
            poczatek=itemView.findViewById(R.id.poczatek)
            koniec = itemView.findViewById(R.id.koniec)
            nazwa = itemView.findViewById(R.id.data)
        }
    }







    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerDayAdapter.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerDayAdapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}