package com.example.calendar_predict

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import com.DataBase.Objective.ObjectiveListViewModel
import com.example.calendar_predict.prediction.AgregationViewModel

/**
 * A fragment representing a list of Items.
 */
class GoalsDayFragment(private val viewModel: ObjectiveListViewModel) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.daily_goal_list, container, false)

        val rvTask = view.findViewById<RecyclerView>(R.id.dailylist)
        val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        decoration.setDrawable(ColorDrawable(Color.WHITE))
        rvTask.addItemDecoration(decoration)

        val adapter = MygoalsDayRecyclerViewAdapter()
        rvTask.adapter = adapter

        rvTask.layoutManager = LinearLayoutManager(requireContext())
        val viewModelag = AgregationViewModel(requireActivity().application)

        //TODO: nie zaciągać przeterminowanych celów
        viewModel.allObjectiveWithCategory.observe(viewLifecycleOwner, {
            adapter.setData(it, viewModelag.prepareAgregate())
        })
        return view
    }
}