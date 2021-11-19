package com.example.calendar_predict

import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.DataBase.Category.CategoryViewModel
import com.DataBase.Objective.Objective
import com.DataBase.Objective.ObjectiveWithCategory
import com.example.calendar_predict.databinding.ActivityAddGoalBinding
import java.time.Instant
import java.util.*

class AddGoalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddGoalBinding
    var amount = 15
    var finishDate: Date? = null
    var editingMode = false
    var goal: ObjectiveWithCategory? = null
    private var spinnerList: MutableList<GoalsCategorySpinner> = mutableListOf()

    private lateinit var categoryViewModel: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_goal)
        binding = ActivityAddGoalBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        categoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        val categories = categoryViewModel.allCategories
//        val actualCategories = categories.

        if (categories.value != null) {
            for (category in categories.value!!) {
                spinnerList.add(GoalsCategorySpinner(category))
            }
        }

        Log.e("56789", "Kategorii jest: " + categories.value + (categories != null))
        Log.e("56789", "lista ma elementów: " + (spinnerList.size))

//        val id = (binding.spinner.selectedItem as GoalsCategorySpinner).category.id


        val extras = intent.extras
        if (extras != null) {
            editingMode = true
            //check previously selected GoalKind box

            goal = extras.getParcelable("goal")
            val toCheck = when(goal!!.objective.kind) {
                GoalKind.WEEK.string -> binding.weekly
                GoalKind.MONTH.string -> binding.monthly
                else -> binding.daily
            }

            val boxes = arrayListOf(
                binding.daily,
                binding.weekly,
                binding.monthly,
            )
            for (box in boxes) {
                box.isChecked = (box == toCheck)
            }

//            todo: set spinner
            binding.spinner.setSelection(goal!!.category.id)

            amount = goal!!.objective.targetAmount
            binding.targetMinutes.text = "$amount minut"

            finishDate = goal!!.objective.date_to
            if (finishDate != null) {
                binding.checkBox.isChecked = true
                binding.selectedDateTimeDeadline.text = "Data wygaśnięcia: ${finishDate!!.day}:${finishDate!!.month}:${finishDate!!.year}\n"
            }

            binding.iconPickerTextView.text = "Edytuj cel..."
        }

        binding.targetMinutes.text = "$amount minut"
    }

    fun amountDecrease(view: android.view.View) {
        if (amount > 15) {
            amount -= 15
            binding.targetMinutes.text = "$amount minut"
        }
    }
    fun amountIncrease(view: android.view.View) {
        amount += 15
        binding.targetMinutes.text = "$amount minut"
    }

    fun selectDate(view: android.view.View) {
        val checkBox = view as CheckBox
        if (checkBox.isChecked) {
            DatePickerFragment().show(supportFragmentManager, "Wybierz datę wygaśnięcia celu")
        }
        else
        {
            binding.selectedDateTimeDeadline.text = ""
            finishDate = null
        }
    }
    fun setDate(year: Int, month: Int, day: Int) {

        val calendar = java.util.Calendar.getInstance()
        calendar[java.util.Calendar.YEAR] = year
        calendar[java.util.Calendar.MONTH] = month
        calendar[java.util.Calendar.DAY_OF_MONTH] = day
        calendar[java.util.Calendar.HOUR_OF_DAY] = 0
        calendar[java.util.Calendar.MINUTE] = 0
        calendar[java.util.Calendar.SECOND] = 0
        calendar[java.util.Calendar.MILLISECOND] = 0


        finishDate = calendar.time
        binding.selectedDateTimeDeadline.text = "Data wygaśnięcia: $day:$month:$year\n"
    }
    fun cancelDateSetting() {
        binding.checkBox.isChecked = false
        finishDate = null
        binding.selectedDateTimeDeadline.text = ""
    }

    fun chooseKind(view: android.view.View) {
        val clicked = view as CheckBox
        val boxes = arrayListOf(
            binding.daily,
            binding.weekly,
            binding.monthly,
        )
        for (box in boxes) {
            if (box != clicked) {
                box.isChecked = false
            }
        }
        if (!clicked.isChecked) {
            clicked.isChecked = true
        }
    }


    fun addTask(view: android.view.View) {
        val id = (binding.spinner.selectedItem as GoalsCategorySpinner).category.id
        val goalKind = when {
            binding.daily.isChecked -> GoalKind.DAY
            binding.weekly.isChecked -> GoalKind.WEEK
            binding.monthly.isChecked -> GoalKind.MONTH
            else -> GoalKind.DAY
        }

        if (editingMode) {
            if (finishDate == null) {
                goal!!.objective.category_id = id
                //TODO nullable date
//                    goal!!.objective.date_to = null
                goal!!.objective.kind = goalKind.string
                goal!!.objective.targetAmount = amount
                categoryViewModel.updateObjective(goal!!.objective)
            }
            else {
                //TODO date from
                goal!!.objective.category_id = id
                goal!!.objective.date_to = finishDate!!
                goal!!.objective.kind = goalKind.string
                goal!!.objective.targetAmount = amount
                categoryViewModel.updateObjective(goal!!.objective)
            }
        } else {
            if (finishDate == null) {
                //TODO nullable date
//                    categoryViewModel.addObjective(Objective(0, id, null, null, goalKind, amount))
            }
            else {
                categoryViewModel.addObjective(Objective(0, id, Date.from(Instant.now()), finishDate!!, goalKind.string, amount))
            }
        }
        finish()
    }
}