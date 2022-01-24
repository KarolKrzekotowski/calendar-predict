package com.example.calendar_predict

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar_predict.databinding.ActivityPendingInvitesBinding
import com.google.firebase.auth.FirebaseAuth

class PendingInvitesActivity: AppCompatActivity() {

    private lateinit var binding: ActivityPendingInvitesBinding
    private lateinit var adapter: PendingAdapter

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

        val myRef = MainActivity.getMyRef()
        myRef.child("messages").get().addOnSuccessListener {
            val list = mutableListOf<Friend>()
            for (message in it.children)
            {

                if (message.key != "0")
                {
                    if (message.child("type").value == "friend-invitation")
                    {
                        val child = Friend(message.child("sender").value.toString())
                        if (!list.contains(child))
                        {
                            list.add(child)
                        }
                    }
                }
            }
            adapter.setData(list)
        }
    }

    fun acceptPending(view: View) {
        val myRef = MainActivity.getMyRef()
        myRef.child("messages").get().addOnSuccessListener {
            for (message in it.children)
            {
                if (message.key != "0")
                {
                    if (message.child("type").value == "friend-invitation" && message.child("sender").value == adapter.getFriend(view.id - 1).name)
                    {
                        myRef.child("messages").child(message.key!!).setValue(null)
                    }
                }
            }
            myRef.child("friends").push().setValue(adapter.getFriend(view.id - 1).name.replace('.', ' '))
            myRef.parent?.child(adapter.getFriend(view.id - 1).name.replace('.', ' '))?.child("friends")?.push()?.setValue(FirebaseAuth.getInstance().currentUser?.email)

            Toast.makeText(this, "Zaakceptowano zaproszenie od: " + adapter.getFriend(view.id - 1).name, Toast.LENGTH_SHORT).show()
            adapter.deleteFriend(view.id - 1)
        }
    }

    fun cancelPending(view: View) {
        val myRef = MainActivity.getMyRef()
        myRef.child("messages").get().addOnSuccessListener {
            for (message in it.children)
            {
                if (message.key != "0")
                {
                    if (message.child("type").value == "friend-invitation" && message.child("sender").value == adapter.getFriend(view.id - 1).name)
                    {
                        myRef.child("messages").child(message.key!!).setValue(null)
                    }
                }
            }

            Toast.makeText(this, "Odrzucono zaproszenie od: " + adapter.getFriend(view.id - 1).name, Toast.LENGTH_SHORT).show()

            adapter.deleteFriend(view.id - 1)
        }
    }
}
