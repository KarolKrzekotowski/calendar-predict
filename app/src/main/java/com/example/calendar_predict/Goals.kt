package com.example.calendar_predict

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar_predict.databinding.GoalsBinding

class Goals: AppCompatActivity() {
    private lateinit var binding: GoalsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = GoalsBinding.inflate(layoutInflater)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        val view = binding.root
        setContentView(view)
        init()
    }

    private fun init() {
        val rvTask = findViewById<View>(R.id.recyclerView) as RecyclerView
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        decoration.setDrawable(ColorDrawable(Color.WHITE))
        rvTask.addItemDecoration(decoration)

        adapter = GoalAdapter(mutableListOf())
        rvTask.adapter = adapter

        rvTask.layoutManager = LinearLayoutManager(this)

        //get goals from local database
        adapter.addGoalToList(Goal("TEST1", GoalKind.DAY, 60, 120))

        adapter.addGoalToList(Goal("TEST2", GoalKind.WEEK, 45, 135))

        adapter.addGoalToList(Goal("TEST3", GoalKind.MONTH, 90, 90))
    }

    fun addGoal(view: android.view.View) {
        val intent = Intent(this, AddGoalActivity::class.java)
        startActivity(intent)
    }

    companion object {
        private lateinit var adapter: GoalAdapter

        fun addGoalToList(goal: Goal) {
            //TODO:
//            GlobalScope.launch {
//                instance?.taskDao?.insertAll(DatabaseTask(task.name, task.icon, task.taskPriority, when {task.date != null -> task.date.toEpochSecond(
//                    ZoneOffset.UTC)
//                    else -> 0} ))
//            }
            adapter.addGoalToList(goal)
            adapter.notifyDataSetChanged()
        }
    }
}