package com.example.calendar_predict

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast

class AddFriendActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_friend)
    }

    fun sendFriendInvitation(view: android.view.View) {
        //TODO save to firebase

        Toast.makeText(this, findViewById<EditText>(R.id.editTextTextEmailAddress).text, Toast.LENGTH_LONG).show()
    }
}