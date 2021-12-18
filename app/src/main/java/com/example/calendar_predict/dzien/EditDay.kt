package com.example.calendar_predict.dzien

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuInflater
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.edit_day.*
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.DataBase.Activity.ActivityWithCategory
import com.DataBase.Objective.ObjectiveListViewModel
import com.example.calendar_predict.*
import com.example.calendar_predict.DayActivityPagerAdapter
import com.example.calendar_predict.databinding.EditDayBinding
import com.google.android.material.tabs.TabLayout

import java.util.Calendar

class EditDay: AppCompatActivity() {

    var together:String?=null
    var day:String?=null
    var month:String?=null
    var year:String?=null
    var edycja:String?=null
    private lateinit var binding: EditDayBinding
    val calendar: Calendar = Calendar.getInstance()

    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EditDayBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        init()
    }
    private fun init() {

        val datereceived =intent.extras
        year = datereceived?.getString("year")
        day = datereceived?.getString("day")
        month = datereceived?.getString("month")
        edycja = datereceived?.getString("edycja",0.toString())
        together =(day.toString()+" "+(month!!.toInt()+1).toString()+ "  "+ year.toString())
        EdycjaDniaS.text = together

        //adapter
//


        calendar[Calendar.YEAR] = year!!.toInt()
        calendar[Calendar.MONTH] = month!!.toInt()
        calendar[Calendar.DAY_OF_MONTH] = day!!.toInt()
        calendar[Calendar.HOUR_OF_DAY] = 0
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0

        tabLayout = findViewById(R.id.tabLayout2)
        viewPager = findViewById(R.id.viewPager2)
        //TODO my fragments
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Dzień"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Codzienne"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Długoterminowe"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Sugestie"))

        var viewModel: ObjectiveListViewModel? = Goals.getViewmodel()
        if (viewModel == null) {
            viewModel = ViewModelProvider(this)[ObjectiveListViewModel::class.java]
        }
        val pagerAdapter = DayActivityPagerAdapter(this, supportFragmentManager, tabLayout!!.tabCount, DayClass.getViewmodel(calendar), viewModel, calendar)
        viewPager!!.adapter =  pagerAdapter
        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        }
        )
    }

    fun sumuj(view:View){
        val myintent = Intent(this,GradeDay::class.java)
        myintent.putExtra("day",day.toString())
        myintent.putExtra("month",month.toString())
        myintent.putExtra("year",year.toString())

        startActivity(myintent)
    }


    fun addActivity(view:View){
        val intent = Intent(this,AddDayActivity::class.java)
        intent.putExtra("day",day.toString())
        intent.putExtra("month",month.toString())
        intent.putExtra("year",year.toString())

        startActivity(intent)
    }

    fun acceptChanges(view:View){


        finish()
    }

    fun getEdycjaa(): String? {
        return edycja
    }
}