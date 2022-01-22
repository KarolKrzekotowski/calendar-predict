package com.example.calendar_predict

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuInflater
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.DataBase.Objective.ObjectiveListViewModel
import com.DataBase.Objective.ObjectiveWithCategory
import com.example.calendar_predict.databinding.ActivityFriendsListBinding
import com.example.calendar_predict.prediction.AgregationViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class FriendsListActivity : AppCompatActivity() {
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

    private lateinit var binding: ActivityFriendsListBinding
    private lateinit var adapter: FriendsAdapter
//    lateinit var objectiveListViewModel: ObjectiveListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


         binding = ActivityFriendsListBinding.inflate(layoutInflater)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        val view = binding.root
        setContentView(view)
        val myRef = MainActivity.getMyRef()
        val friends = myRef.child("friends").get().addOnSuccessListener {
            Toast.makeText(this, "ładowanie listy znajomych powiodło się ${ it.value }", Toast.LENGTH_LONG).show()
        }.addOnFailureListener {
            Toast.makeText(this, "ładowanie listy znajomych nie powiodło się", Toast.LENGTH_LONG).show()
        }

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

//        myRef.setValue("Hello, World!")

        init()
    }

    private fun init() {
        val rvTask = findViewById<View>(R.id.recyclerViewFriends) as RecyclerView
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        decoration.setDrawable(ColorDrawable(Color.WHITE))
        rvTask.addItemDecoration(decoration)

        adapter = FriendsAdapter()
        rvTask.adapter = adapter

        rvTask.layoutManager = LinearLayoutManager(this)

        adapter.setData(listOf(Friend("Joe"), Friend("Bob"), Friend("Alice")))

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

    fun addFriend(view: android.view.View) {
        val intent = Intent(this, AddFriendActivity::class.java)
        startActivity(intent)
    }

    fun showPendingInvites(view: android.view.View) {
        val intent = Intent(this, PendingInvitesActivity::class.java)
        startActivity(intent)
    }
}