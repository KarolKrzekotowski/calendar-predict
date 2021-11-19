package com.example.calendar_predict

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import java.io.File

class GoalsCategorySpinnerAdapter(val context: Context, var dataSource: List<GoalsCategorySpinner>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.goals_category_spinner_item, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }

//        todo parse icon and set, remove setimagedrawable, might require permission to storage
//        vh.img.setBackgroundResource(dataSource[position].category.name)
//        val imgFile = File(dataSource[position].category.icon)
//        if(imgFile.exists())
//        {
//            vh.img.setImageURI(Uri.fromFile(imgFile))
//        }

        vh.img.setImageDrawable(ColorDrawable(Color.RED))

        vh.layout.setBackgroundColor(Color.parseColor("#" + Integer.toHexString(dataSource[position].category.colour)))
        vh.name.text = dataSource[position].category.name

        return view
    }

    override fun getItem(position: Int): Any {
        return dataSource[position];
    }

    override fun getCount(): Int {
        return dataSource.size;
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    fun getPosition(item: GoalsCategorySpinner) :Int {
        for (data in dataSource) {
            if (data == item) {
                return dataSource.indexOf(data)
            }
        }
        return -1
    }


    private class ItemHolder(row: View?) {
        val img: ImageView = row?.findViewById(R.id.imageView5) as ImageView
        val name: TextView = row?.findViewById(R.id.textView4) as TextView
        val layout: ConstraintLayout = row?.findViewById(R.id.goals_spinner_layout) as ConstraintLayout
    }

}