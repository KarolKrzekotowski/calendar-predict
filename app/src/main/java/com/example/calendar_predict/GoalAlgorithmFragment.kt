package com.example.calendar_predict

import android.app.Application
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import com.DataBase.Category.Category
import com.DataBase.Objective.ObjectiveListViewModel
import com.example.calendar_predict.prediction.PredictionViewModel

/**
 * A fragment representing a list of Items.
 */
class GoalAlgorithmFragment(private val categories: List<Category>) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.algorithm_goal_list, container, false)

        val rvTask = view.findViewById<RecyclerView>(R.id.algorithmlist)
        val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        decoration.setDrawable(ColorDrawable(Color.WHITE))
        rvTask.addItemDecoration(decoration)

        val adapter = MyGoalAlgorithmRecyclerViewAdapter(categories)
        rvTask.adapter = adapter

        rvTask.layoutManager = LinearLayoutManager(requireContext())

        val predictionMap = PredictionViewModel(requireActivity().application).preparePredict()
        adapter.setData(predictionMap)
        return view
    }
}