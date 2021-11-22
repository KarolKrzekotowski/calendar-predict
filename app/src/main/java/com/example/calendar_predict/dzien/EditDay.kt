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
import com.example.calendar_predict.R

import kotlinx.android.synthetic.main.edit_day.*
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.DataBase.Activity.ActivityWithCategory
import com.DataBase.Day.DayViewModel
import com.DataBase.Day.DayViewModelFactory
import com.DataBase.Day.DayWithActivities
import com.example.calendar_predict.databinding.EditDayBinding
import java.time.LocalDateTime
import java.util.Calendar

class EditDay: AppCompatActivity() {

    var together:String?=null
    var day:String?=null
    var month:String?=null
    var year:String?=null

    private lateinit var adapter: EditDayAdapter
    lateinit var  dayViewModel: DayViewModel
    private lateinit var binding:EditDayBinding
    var calendarcheck:Calendar = Calendar.getInstance()
    val calendar = Calendar.getInstance()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EditDayBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        init()
    }
    private fun init() {
        instance = this

        val datereceived =intent.extras
        year = datereceived?.getString("year")
        day = datereceived?.getString("day")
        month = datereceived?.getString("month")
        together =(day.toString()+" "+month.toString()+ "  "+ year.toString())
        EdycjaDnia.text = together

        //adapter
        val rvActivity = findViewById<View>(R.id.dayEditRecycler) as RecyclerView
        adapter = EditDayAdapter()
        rvActivity.adapter = adapter
        rvActivity.layoutManager = LinearLayoutManager(this)


        calendar[Calendar.YEAR] = year!!.toInt()
        calendar[Calendar.MONTH] = month!!.toInt()
        calendar[Calendar.DAY_OF_MONTH] = day!!.toInt()
        calendar[Calendar.HOUR_OF_DAY] = 0
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0
        Log.i("tutaj",month.toString())
        val factory = DayViewModelFactory(application, calendar.time)
        dayViewModel = ViewModelProvider(this, factory).get(DayViewModel::class.java)


        dayViewModel.dayWithActivities.observe(this,{it->
            //Log.e("1234456780", it.activityWithCategory)
            adapter.setData(it.activityWithCategory)
        })

        if(calendarcheck.time<calendar.time){
            podsumowanko.setVisibility(View.INVISIBLE)


        }




    }


    companion object{
        private lateinit var instance: EditDay



        fun showPopup(v:View, activityWithCategory: ActivityWithCategory){
            val popup = PopupMenu(instance.applicationContext, v , Gravity.END)
            val inflater:MenuInflater = popup.menuInflater
            inflater.inflate(R.menu.edit_day_menu,popup.menu)


            popup.setOnMenuItemClickListener { item ->
                when(item.itemId){
                    R.id.usun_aktywność ->{
                        AlertDialog.Builder(instance)
                            .setTitle("usuwanie aktywności")
                            .setMessage("Czy na pewno usunąć aktywność?")
                            .setPositiveButton("Potwierdź"){ _ ,_ ->
                                instance.dayViewModel.deleteActivity(activityWithCategory.activity)
                            }
                            .setNegativeButton("Anuluj",null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .create()
                            .show()
                    }
                    R.id.edytuj_aktywność ->{
                        val editintent = Intent(instance,AddDayActivity::class.java)
                        editintent.putExtra("activity",activityWithCategory )
                        startActivity(instance,editintent,null)

                    }
                }
                true
            }
            popup.show()
        }
    }






    fun sumuj(view:View){
        val myintent = Intent(this,GradeDay::class.java)
        myintent.putExtra("day",day.toString())
        myintent.putExtra("month",month.toString())
        myintent.putExtra("year",year.toString())

        startActivity(myintent)
    }


    fun addActivity(view:View){
        Log.e("123456","$together")
        val intent = Intent(this,AddDayActivity::class.java)
        intent.putExtra("day",day.toString())
        intent.putExtra("month",month.toString())
        intent.putExtra("year",year.toString())

        startActivity(intent)
    }

    fun acceptChanges(view:View){


        finish()
    }

}