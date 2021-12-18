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
import androidx.lifecycle.ViewModelProvider
import com.DataBase.Activity.ActivityWithCategory
import com.DataBase.Day.DayViewModel
import com.example.calendar_predict.dzien.AddDayActivity
import com.example.calendar_predict.dzien.DayClass
import com.example.calendar_predict.dzien.EditDay
import com.example.calendar_predict.dzien.RecyclerDayAdapter
import java.util.*

/**
 * A fragment representing a list of Items.
 */
class DayAddedActivitiyFragment(val dayViewModel: DayViewModel) : Fragment() {
    var recyclerView: RecyclerView?=null

    private var columnCount = 1
    var calendarcheck: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        instance = this
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.added_activitiy_day_list, container, false)

        val rvActivity = view.findViewById<View>(R.id.dayEditRecyclerS) as RecyclerView
        val adapter = RecyclerDayAdapter()
//        rvActivity.layoutManager = LinearLayoutManager(context)
//        rvActivity.adapter = adapter
//        dayViewModel = DayViewModelFactory(calendar.time)
//        val factory = (requireActivity() as EditDay).defaultViewModelProviderFactory
//        dayViewModel = ViewModelProvider(this, factory).get(DayViewModel::class.java)
//
////        var activity = Activity(0,2,1,May 04 09:51:52 CDT 2009,13 )
        dayViewModel.dayWithActivities.observe(viewLifecycleOwner, { data ->
            if (data != null) {
                adapter.setData(data.activityWithCategory.sortedBy { it.activity.hour_from })
            }
        })
        rvActivity.adapter = adapter
        rvActivity.layoutManager = LinearLayoutManager(requireContext())
//
//
        if(calendarcheck.time < DayClass.getCalendar().time){
            requireActivity().findViewById<Button>(R.id.podsumowanko).visibility = View.INVISIBLE
        }

        if((requireActivity()as EditDay).getEdycjaa() == "1"){
            requireActivity().findViewById<Button>(R.id.podsumowanko).visibility = View.INVISIBLE
        }

        return view
    }


    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

//        // TODO: Customize parameter initialization
//        @JvmStatic
//        fun newInstance(columnCount: Int) =
//            DayAddedActivitiyFragment().apply {
//                arguments = Bundle().apply {
//                    putInt(ARG_COLUMN_COUNT, columnCount)
//                }
//            }


        private lateinit var instance: DayAddedActivitiyFragment



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
                }
                true
            }
            popup.show()
        }
    }
}