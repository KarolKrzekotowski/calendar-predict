package com.example.calendar_predict.activityInvitation

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.DataBase.Activity.ActivityWithCategory
import com.example.calendar_predict.Friend
import com.example.calendar_predict.MainActivity
import com.example.calendar_predict.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.choose_friend.*
import java.util.*

class InviteToActivity : AppCompatActivity() {
    var calendar1 = Calendar.getInstance()
    var calendar2 = Calendar.getInstance()
    var calendar3 = Calendar.getInstance()
    var day = ""

    var name =""

    var aktywność : ActivityWithCategory ?= null
    var calendar = Calendar.getInstance()



    val firedatabase = Firebase.database("https://calendar-predict-default-rtdb.europe-west1.firebasedatabase.app/")

    private lateinit var adapter: FriendInviteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choose_friend)

        calendar3[Calendar.HOUR_OF_DAY] = 0
        calendar3[Calendar.MINUTE] = 0
        calendar3[Calendar.SECOND] = 0
        calendar3[Calendar.MILLISECOND] = 0


        val datarecived = intent.extras
        if( datarecived != null){
            //
            Log.i("weszlo", "weszlo")
            aktywność = datarecived?.getParcelable("activity")
            name = aktywność!!.activity.name
            calendar1.time = aktywność!!.activity.hour_from
            calendar2.time = aktywność!!.activity.hour_to
            day = calendar3.timeInMillis.toString()

        }


        val rvTask = friendRecycler2
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        decoration.setDrawable(ColorDrawable(Color.WHITE))
        rvTask.addItemDecoration(decoration)

        adapter = FriendInviteAdapter()
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



    fun goBack(view : View){
        finish()
    }

    fun theChosen(view: View){
        val myRef = MainActivity.getMyRef()
        val myEmail = FirebaseAuth.getInstance().currentUser?.email
        val myFireEmail = myEmail.toString().replace(".", " ")
        val friendEmail = adapter.getFriend(view.id-1)
        val fireFriendEmail = friendEmail.replace('.',' ')
        val friendRef  = firedatabase.getReference("users")
        Toast.makeText(this, friendEmail.toString(), Toast.LENGTH_SHORT).show()
        AlertDialog.Builder(this).setTitle("wysyłanie zaproszenia")
            .setMessage("Czy na pewno chcesz wysłać zaproszenie do $friendEmail ")
            .setPositiveButton("Tak"){_,_ ->
                //wyślij do bazy
                Log.d("ciupapimuniano",calendar1.timeInMillis.toString())

                friendRef.child(fireFriendEmail).child("invite").child(calendar.timeInMillis.toString()).child("From").setValue(calendar1.timeInMillis.toString())
                friendRef.child(fireFriendEmail).child("invite").child(calendar.timeInMillis.toString()).child("To").setValue(calendar2.timeInMillis.toString())
                friendRef.child(fireFriendEmail).child("invite").child(calendar.timeInMillis.toString()).child("name").setValue(name)
                friendRef.child(fireFriendEmail).child("invite").child(calendar.timeInMillis.toString()).child("day").setValue(day)
                friendRef.child(fireFriendEmail).child("invite").child(calendar.timeInMillis.toString()).child("Friend").setValue(myEmail)
                friendRef.child(fireFriendEmail).child("invite").child(calendar.timeInMillis.toString()).child("sentTime").setValue(calendar.timeInMillis.toString())

            }
            .setNegativeButton("Nie",null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .create()
            .show()
    }
}