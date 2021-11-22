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
import com.DataBase.Day.DayWithActivities
import com.example.calendar_predict.databinding.EditDayBinding

class EditDay: AppCompatActivity() {

    var together:String?=null
    var day:String?=null
    var month:String?=null
    var year:String?=null

    private lateinit var adapter: EditDayAdapter
    lateinit var  dayViewModel: DayViewModel
    private lateinit var binding:EditDayBinding



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
        dayViewModel = ViewModelProvider(this).get(DayViewModel::class.java)

        dayViewModel.dayWithActivities.observe(this,{it->
            adapter.setData(it.activityWithCategory)
        })









    }

    companion object{
        private lateinit var instance: EditDay

        fun showPopup(v:View,ActivityWithCategory: ActivityWithCategory){
            val popup = PopupMenu(instance.applicationContext, v , Gravity.END)
            val inflater:MenuInflater = popup.menuInflater
            inflater.inflate(R.menu.edit_day_menu,popup.menu)


            popup.setOnMenuItemClickListener { item ->
                when(item.itemId){
                    R.id.usun_aktywność ->{
                        AlertDialog.Builder(instance)
                            .setTitle("usuwanie aktywności")
                            .setMessage("Czy na pewno usunąć aktywność?")
/*                            .setPositiveButton("Potwierdź"){ _ ,_ ->
                                instance.dayViewModel.SdeleteActivity(ActivityWithCategory.activity)
                            }*/
                            .setNegativeButton("Anuluj",null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .create()
                            .show()
                    }
                    R.id.edytuj_aktywność ->{
                        val editintent = Intent(instance,AddDayActivity::class.java)
                        editintent.putExtra("activity",ActivityWithCategory )
                        startActivity(instance,editintent,null)

                    }
                }
                true
            }
            popup.show()
        }
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