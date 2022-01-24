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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class FriendsListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFriendsListBinding
    private lateinit var adapter: FriendsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityFriendsListBinding.inflate(layoutInflater)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        val view = binding.root
        setContentView(view)

        init()
    }

    override fun onResume() {
        super.onResume()

        val myRef = MainActivity.getMyRef()

        myRef.child("friends").get().addOnSuccessListener {
            val list = mutableListOf<Friend>()
            for (friend in it.children) {
                list.add(Friend(friend.value.toString().replace(' ', '.')))
            }
            adapter.setData(list)
        }.addOnFailureListener {
            Toast.makeText(this, "Niepowodzenie: $it", Toast.LENGTH_SHORT).show()
        }
    }

    private fun init() {
        val rvTask = findViewById<View>(R.id.recyclerViewFriends) as RecyclerView
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        decoration.setDrawable(ColorDrawable(Color.WHITE))
        rvTask.addItemDecoration(decoration)

        adapter = FriendsAdapter()
        rvTask.adapter = adapter

        rvTask.layoutManager = LinearLayoutManager(this)

        val myRef = MainActivity.getMyRef()

        myRef.child("friends").get().addOnSuccessListener {
            val list = mutableListOf<Friend>()
            for (friend in it.children) {
                list.add(Friend(friend.value.toString().replace(' ', '.')))
            }
            adapter.setData(list)
        }.addOnFailureListener {
            Toast.makeText(this, "Niepowodzenie: $it", Toast.LENGTH_SHORT).show()
        }

    }

    fun addFriend(view: android.view.View) {
        val intent = Intent(this, AddFriendActivity::class.java)
        startActivity(intent)
    }


    fun deleteFriend(view: View) {
        val myRef = MainActivity.getMyRef()

        //delete me from friends friend list
        val myEmail = FirebaseAuth.getInstance().currentUser?.email
        myRef.parent?.child(adapter.getFriend(view.id - 1).name.replace('.', ' '))?.child("friends")?.get()?.addOnSuccessListener {
            for (friend in it.children) {
                if (friend.value.toString().replace(' ', '.') == myEmail)
                {
                    myRef.parent?.child(adapter.getFriend(view.id - 1).name.replace('.', ' '))?.child("friends")?.child(friend.key!!)?.setValue(null)
                }
            }
            //delete messages to that friend
            myRef.parent?.child(adapter.getFriend(view.id - 1).name.replace('.', ' '))?.child("messages")?.get()?.addOnSuccessListener {
                for (message in it.children) {
                    if (message.child("sender").value == myEmail)
                    {
                        myRef.parent?.child(adapter.getFriend(view.id - 1).name.replace('.', ' '))?.child("messages")?.child(message.key!!)?.setValue(null)
                    }
                }

                //delete messages from that friend
                myRef.child("messages").get().addOnSuccessListener {
                    for (message in it.children) {
                        if (message.child("sender").value == adapter.getFriend(view.id - 1).name) {
                            myRef.child("messages").child(message.key!!).setValue(null)
                        }
                    }

                    //delete friend from my friend list
                    myRef.child("friends").get().addOnSuccessListener {
                        for (friend in it.children) {
                            if (friend.value.toString().replace(' ', '.') == adapter.getFriend(view.id - 1).name)
                            {
                                myRef.child("friends").child(friend.key!!).setValue(null)
                            }
                        }

                        Toast.makeText(this, "Usunięto z listy znajomych: " + adapter.getFriend(view.id - 1), Toast.LENGTH_LONG).show()
                        adapter.deleteFriend(view.id - 1)
                    }.addOnFailureListener {
                        Toast.makeText(this, "Niepowodzenie: $it", Toast.LENGTH_SHORT).show()
                    }

                }.addOnFailureListener {
                    Toast.makeText(this, "Niepowodzenie: $it", Toast.LENGTH_SHORT).show()
                }
            }?.addOnFailureListener {
                Toast.makeText(this, "Niepowodzenie: $it", Toast.LENGTH_SHORT).show()
            }

        }?.addOnFailureListener {
            Toast.makeText(this, "Niepowodzenie: $it", Toast.LENGTH_SHORT).show()
        }
    }

    fun showPendingInvites(view: android.view.View) {
        val intent = Intent(this, PendingInvitesActivity::class.java)
        startActivity(intent)
    }
}