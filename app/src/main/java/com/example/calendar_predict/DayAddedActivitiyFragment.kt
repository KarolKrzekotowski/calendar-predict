package com.example.calendar_predict

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.DataBase.Activity.ActivityWithCategory
import com.DataBase.Day.DayViewModel
import com.example.calendar_predict.activityInvitation.InviteToActivity
import com.example.calendar_predict.dzien.AddDayActivity
import com.example.calendar_predict.dzien.DayClass
import com.example.calendar_predict.dzien.EditDay
import com.example.calendar_predict.dzien.RecyclerDayAdapter
import java.util.*

/**
 * A fragment representing a list of Items.
 */
class DayAddedActivitiyFragment(var dayViewModel: DayViewModel, val calendarcheck: Calendar) : Fragment() {
    var recyclerView: RecyclerView?=null

    private var columnCount = 1
    val adapter = MyDayAddedActivitiyRecyclerViewAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        instance = this
        val view = inflater.inflate(R.layout.added_activitiy_day_list, container, false)

        val rvActivity = view.findViewById<View>(R.id.dayEditRecyclerS) as RecyclerView

        dayViewModel.dayWithActivities.observe(viewLifecycleOwner, { data ->
            if (data != null) {
                adapter.setData(data.activityWithCategory.sortedBy { it.activity.hour_from })
            }
        })
        rvActivity.adapter = adapter
        rvActivity.layoutManager = LinearLayoutManager(requireContext())

        if(calendarcheck.time > DayClass.getCalendar().time){
            requireActivity().findViewById<Button>(R.id.podsumowanko).visibility = View.INVISIBLE
        }

        if((requireActivity()as EditDay).getEdycjaa() == "1"){
            requireActivity().findViewById<Button>(R.id.podsumowanko).visibility = View.INVISIBLE
        }

        return view
    }


    companion object {
        private lateinit var instance: DayAddedActivitiyFragment
        fun setModel(model: DayViewModel)
        {
            if(this::instance.isInitialized) {
                instance.dayViewModel = model
                instance.dayViewModel.dayWithActivities.observe(instance.viewLifecycleOwner, { data ->
                    if (data != null) {
                        instance.adapter.setData(data.activityWithCategory.sortedBy { it.activity.hour_from })
                    }
                })
            }
        }


        fun showPopup(v:View, activityWithCategory: ActivityWithCategory){
            val popup = PopupMenu(instance.requireContext(), v , Gravity.END)
            val inflater: MenuInflater = popup.menuInflater
            inflater.inflate(R.menu.edit_day_menu,popup.menu)


            popup.setOnMenuItemClickListener { item ->
                when(item.itemId){
                    R.id.usun_aktywność ->{
                        AlertDialog.Builder(instance.requireContext())
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
                        val editintent = Intent(instance.requireContext(), AddDayActivity::class.java)
                        editintent.putExtra("activity",activityWithCategory )
                        ContextCompat.startActivity(instance.requireContext(), editintent, null)

                    }
                    R.id.zapros ->{
                        val intentInvite = Intent(instance.requireContext(), InviteToActivity::class.java)
                        intentInvite.putExtra("activity",activityWithCategory)
                        ContextCompat.startActivity(instance.requireContext(),intentInvite,null)
                    }
                }
                true
            }
            popup.show()
        }
    }
}