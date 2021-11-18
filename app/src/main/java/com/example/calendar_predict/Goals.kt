package com.example.calendar_predict

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuInflater
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.DataBase.Objective.Objective
import com.DataBase.Objective.ObjectiveListViewModel
import com.DataBase.Objective.ObjectiveWithCategory
import com.example.calendar_predict.databinding.GoalsBinding
import java.time.LocalDate

class Goals: AppCompatActivity() {
    private lateinit var binding: GoalsBinding
    lateinit var objectiveListViewModel: ObjectiveListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = GoalsBinding.inflate(layoutInflater)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        val view = binding.root
        setContentView(view)
        init()
    }

    private fun init() {
        instance = this

        val rvTask = findViewById<View>(R.id.recyclerView) as RecyclerView
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        decoration.setDrawable(ColorDrawable(Color.WHITE))
        rvTask.addItemDecoration(decoration)

        adapter = GoalAdapter()
        rvTask.adapter = adapter

        rvTask.layoutManager = LinearLayoutManager(this)

        objectiveListViewModel = ViewModelProvider(this).get(ObjectiveListViewModel::class.java)

        objectiveListViewModel.allObjectiveWithCategory.observe(this, Observer { it->
            adapter.setData(it)
        })
    }

    fun addGoal(view: View) {
        val intent = Intent(this, AddGoalActivity::class.java)
        startActivity(intent)
    }

    companion object {
        private lateinit var adapter: GoalAdapter
        private lateinit var instance: Goals


        fun addGoalToList(goal: Objective) {
            instance.objectiveListViewModel.addObjective(goal)
            //TODO: insert goal to db
//            GlobalScope.launch {
//                instance?.taskDao?.insertAll(DatabaseTask(task.name, task.icon, task.taskPriority, when {task.date != null -> task.date.toEpochSecond(
//                    ZoneOffset.UTC)
//                    else -> 0} ))
//            }
//            adapter.addGoalToList(goal)
//            adapter.notifyDataSetChanged()
        }

        fun showPopup(v: View, objectiveWithCategory: ObjectiveWithCategory) {
            val popup = PopupMenu(instance.applicationContext, v, Gravity.END)
            val inflater: MenuInflater = popup.menuInflater
            inflater.inflate(R.menu.goals_context_menu, popup.menu)

            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.usun -> {
                        AlertDialog.Builder(instance)
                            .setTitle("Usuwanie celu")
                            .setMessage("Czy na pewno chcesz usunąć ten cel?")
                            .setPositiveButton("Potwierdż") { _, _ ->
                                val goal = objectiveWithCategory
                                //val goal = adapter.getGoalList()[position]

                                //TODO: remove goal from db
//                                GlobalScope.launch {
//                                    val tmpTask = instance!!.taskDao.findByName(task.name, task.icon, task.taskPriority, when {task.date != null -> task.date.toEpochSecond(
//                                        ZoneOffset.UTC) else -> 0})
//                                    instance!!.taskDao.delete(tmpTask)
//                                }

                                Log.e("1234567890", objectiveWithCategory.objective.id.toString())
                                instance.objectiveListViewModel.deleteObjective(goal.objective)
//                                adapter.getGoalList().removeAt(position)
//                                adapter.notifyDataSetChanged()
                            }
                            .setNegativeButton("Anuluj", null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .create()
                            .show()
                    }
                    R.id.edytuj_cel -> {
                        //val goal = adapter.getGoalList()[position]
                        val goal = objectiveWithCategory

                        val intent = Intent(instance, AddGoalActivity::class.java)
                        intent.putExtra("goal", goal)

                        startActivity(instance, intent, null)
                    }
                }
                true
            }

            popup.show()
        }
    }
}