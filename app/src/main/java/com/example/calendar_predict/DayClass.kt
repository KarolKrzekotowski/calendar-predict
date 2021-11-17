package com.example.calendar_predict

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class DayClass : Fragment() {

    var recyclerView: RecyclerView?=null
    private var data_array:MutableList<dzien> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.day2_page, container,false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView?.setLayoutManager(LinearLayoutManager(context))

//        val entries:List<LeaderBoards> =entryDao.getLeaderboard()
//        data_array.addAll(entries)
//        data_array.sortByDescending { it.poczatek }
//        recyclerView.adapter = RecyclerDayAdapter(data_array,entryDao)

        return view
    }

}