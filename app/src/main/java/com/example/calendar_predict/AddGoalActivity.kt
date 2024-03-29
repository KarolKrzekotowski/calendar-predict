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
    var calendar2: Calendar = Calendar.getInstance()

    private lateinit var categoryViewModel: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_goal)
        binding = ActivityAddGoalBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        categoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        val categories = categoryViewModel.allCategories
        for (category in categories) {
            spinnerList.add(GoalsCategorySpinner(category))
        }
        binding.spinner.adapter = GoalsCategorySpinnerAdapter(this, spinnerList)
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

            //can be problematic if we allow to delete categories
            binding.spinner.setSelection(goal!!.category.id - 3)

            amount = goal!!.objective.targetAmount
            binding.targetMinutes.text = "$amount minut"

            finishDate = goal!!.objective.date_to

            calendar2.time =finishDate


            if (finishDate != null) {
                binding.checkBox.isChecked = true
                binding.selectedDateTimeDeadline.text = "Data wygaśnięcia:" +((calendar2[Calendar.DAY_OF_MONTH]).toString()+":"+(calendar2[Calendar.MONTH]+1).toString()+":"+ calendar2[Calendar.YEAR].toString())
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
        binding.selectedDateTimeDeadline.text = "Data wygaśnięcia: $day:${month+1}:$year\n"
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
                goal!!.objective.date_to = null
                goal!!.objective.kind = goalKind.string
                goal!!.objective.targetAmount = amount
                categoryViewModel.updateObjective(goal!!.objective)
            }
            else {
                goal!!.objective.category_id = id
                goal!!.objective.date_to = finishDate!!
                goal!!.objective.kind = goalKind.string
                goal!!.objective.targetAmount = amount
                categoryViewModel.updateObjective(goal!!.objective)
            }
        } else {
            if (finishDate == null) {
                categoryViewModel.addObjective(Objective(0, id, Date.from(Instant.now()), null, goalKind.string, amount))
            }
            else {
                categoryViewModel.addObjective(Objective(0, id, Date.from(Instant.now()), finishDate!!, goalKind.string, amount))
            }
        }
        finish()
    }
}