package com.example.calendar_predict.statistics

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.calendar_predict.R

class StatisticsSpinnerAdapter(val context: Context?, var dataSource: Array<String>): BaseAdapter() {

    private val inflater: LayoutInflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(p0: Int): Any {
        return dataSource[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    private class ItemHolder(row:View?){
        val text: TextView = row?.findViewById(R.id.timeamount) as TextView
        val layout: ConstraintLayout = row?.findViewById(R.id.time_spinner_layout) as ConstraintLayout
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view:View
        val vh: ItemHolder
        if (convertView == null){
            view = inflater.inflate(R.layout.stats__item_page,parent,false)
            vh = ItemHolder(view)
            view?.tag = vh
        }
        else {
            view = convertView
            vh = view.tag as ItemHolder
        }


        vh.text.text = dataSource[position]
        return view

    }





}