package com.example.calendar_predict

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.DataBase.Day.DayViewModel


class DayClass : Fragment() {

    private lateinit var dayViewModel: DayViewModel

    var recyclerView: RecyclerView?=null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.day2_page, container,false)

        val adapter = RecyclerDayAdapter()
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = adapter

        dayViewModel = ViewModelProvider(this).get(DayViewModel::class.java)

//        dayViewModel.dayWithActivities.observe(viewLifecycleOwner, Observer { data ->
//            if(data != null){
//                adapter.setData(data.activityWithCategory)
//            }
//        })

//        val entries:List<LeaderBoards> =entryDao.getLeaderboard()
//        data_array.addAll(entries)
//        data_array.sortByDescending { it.poczatek }

        return view
    }

}