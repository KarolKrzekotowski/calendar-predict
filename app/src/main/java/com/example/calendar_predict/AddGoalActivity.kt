package com.example.calendar_predict

import DatePickerFragment
import GoalKind
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.Toast
import com.example.calendar_predict.databinding.ActivityAddGoalBinding
import java.time.LocalDate

class AddGoalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddGoalBinding
    var amount = 15
    var finishDate: LocalDate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_goal)
        binding = ActivityAddGoalBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
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
        finishDate = LocalDate.of(year, month, day)
        binding.selectedDateTimeDeadline.text = "Data: $day:$month:$year\n"
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
        //TODO: insert task to db
        //TODO: select category instead of writing

        val name = binding.editTextTaskName.text.toString()
        val goalKind = when {
            binding.daily.isChecked -> GoalKind.DAY
            binding.weekly.isChecked -> GoalKind.WEEK
            binding.monthly.isChecked -> GoalKind.MONTH
            else -> GoalKind.DAY
        }

        if (name != "") {
            Goals.addGoalToList(Goal(name, goalKind, 0, amount))

            finish()
        }
        else
        {
            Toast.makeText(this, "Nadaj jakąś nazwę kategorii", Toast.LENGTH_LONG).show()
        }
    }
}