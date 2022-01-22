package com.example.calendar_predict

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar_predict.databinding.ActivityPendingInvitesBinding

class PendingInvitesActivity: AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_friends_list)
//
//        val database = Firebase.database
//        val myRef = database.getReference("message")
//
//        myRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                val value = dataSnapshot.getValue<String>()
//                Log.d("12312", "Value is: $value")
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Failed to read value
//                Log.w("12312", "Failed to read value.", error.toException())
//            }
//        })
//
//        myRef.setValue("Hello, World!")
//
//
//
//
//    }

    private lateinit var binding: ActivityPendingInvitesBinding
    private lateinit var adapter: PendingAdapter
//    lateinit var objectiveListViewModel: ObjectiveListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityPendingInvitesBinding.inflate(layoutInflater)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        val view = binding.root
        setContentView(view)
        init()
    }

    private fun init() {
        val rvTask = findViewById<View>(R.id.recyclerViewPending) as RecyclerView
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        decoration.setDrawable(ColorDrawable(Color.WHITE))
        rvTask.addItemDecoration(decoration)

        adapter = PendingAdapter()
        rvTask.adapter = adapter

        rvTask.layoutManager = LinearLayoutManager(this)

        adapter.setData(listOf(Friend("Ada"), Friend("Joe"), Friend("Bob"), Friend("Alice")))

//        objectiveListViewModel = ViewModelProvider(this)[ObjectiveListViewModel::class.java]
//
//        val viewModel = AgregationViewModel(application)
//
//        //TODO: nie zaciągać przeterminowanych celów
//        objectiveListViewModel.allObjectiveWithCategory.observe(this, Observer { it->
//            adapter.setData(it, viewModel.prepareAgregate())
//        })
    }

//    companion object {
//        private lateinit var instance: Goals
//
//        fun getViewmodel(): ObjectiveListViewModel? {
//            if (!this::instance.isInitialized)
//            {
//                return null
//            }
//            return instance.objectiveListViewModel
//        }
//
//        fun showPopup(v: View, objectiveWithCategory: ObjectiveWithCategory) {
//            val popup = PopupMenu(instance.applicationContext, v, Gravity.END)
//            val inflater: MenuInflater = popup.menuInflater
//            inflater.inflate(R.menu.goals_context_menu, popup.menu)
//
//            popup.setOnMenuItemClickListener { item ->
//                when (item.itemId) {
//                    R.id.usun -> {
//                        AlertDialog.Builder(instance)
//                            .setTitle("Usuwanie celu")
//                            .setMessage("Czy na pewno chcesz usunąć ten cel?")
//                            .setPositiveButton("Potwierdż") { _, _ ->
//                                instance.objectiveListViewModel.deleteObjective(objectiveWithCategory.objective)
//                            }
//                            .setNegativeButton("Anuluj", null)
//                            .setIcon(android.R.drawable.ic_dialog_alert)
//                            .create()
//                            .show()
//                    }
//                    R.id.edytuj_cel -> {
//                        val intent = Intent(instance, AddGoalActivity::class.java)
//                        intent.putExtra("goal", objectiveWithCategory)
//
//                        ContextCompat.startActivity(instance, intent, null)
//                    }
//                }
//                true
//            }
//
//            popup.show()
//        }
//    }
//
//    fun addFriend(view: android.view.View) {
//        val intent = Intent(this, AddFriendActivity::class.java)
//        startActivity(intent)
//    }
//
//    fun showPendingInvites(view: android.view.View) {
//        val intent = Intent(this, PendingInvitesActivity::class.java)
//        startActivity(intent)

    fun acceptPending(view: View) {
        //TODO implement

        Toast.makeText(this, "Accepted " + adapter.getFriend(view.id).name, Toast.LENGTH_SHORT).show()
    }

    fun cancelPending(view: View) {
        //TODO implement
        Toast.makeText(this, "Cancelled " + adapter.getFriend(view.id).name, Toast.LENGTH_SHORT).show()

    }
}
