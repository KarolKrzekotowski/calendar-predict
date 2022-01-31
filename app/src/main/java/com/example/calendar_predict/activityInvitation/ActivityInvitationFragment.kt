package com.example.calendar_predict.activityInvitation

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar_predict.DayAddedActivitiyFragment
import com.example.calendar_predict.Friend
import com.example.calendar_predict.MainActivity
import com.example.calendar_predict.R
import com.example.calendar_predict.dzien.AddDayActivity
import com.example.calendar_predict.dzien.EditDay
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.accepted_invite_list.*
import kotlinx.android.synthetic.main.activity_invitation.view.*
import java.lang.Thread.sleep

class ActivityInvitationFragment : Fragment() {
    var adapter: inviteAdapter? = null
    var rvInvite: RecyclerView? = null
    var successfull :Boolean ?=false
    var sentTime :String?=null
    val myRef = MainActivity.getMyRef().child("invite")
    var launchSomeActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {result ->
        if (result.resultCode == Activity.RESULT_OK){
            val data = result.data
            successfull = data?.getBooleanExtra("success",false)
            if (successfull ==true){
                myRef.child(sentTime.toString()).removeValue()
            }

        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        instance = this
        val myRef = MainActivity.getMyRef().child("invite")

        val ReadRef = myRef.orderByChild("From")
        val layout = inflater.inflate(R.layout.activity_invitation, container, false)
        rvInvite = layout?.rvInvitation
        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        decoration.setDrawable(ColorDrawable(Color.WHITE))
        rvInvite?.addItemDecoration(decoration)


        rvInvite?.layoutManager = LinearLayoutManager(context)




        val options = FirebaseRecyclerOptions.Builder<Invitation>()
            .setQuery(
                ReadRef,
                Invitation::class.java
            ).build()
        adapter = inviteAdapter(options)
        rvInvite?.adapter = adapter


        return layout
    }

    override fun onStart() {
        super.onStart()
        adapter?.startListening()
    }



    companion object {
        private lateinit var instance: ActivityInvitationFragment

        private var calendar1: String? = null
        private var FriendName: String? = null
        private var name: String? = null
        fun AcceptActivity(view: View, model: Invitation) {
            val calendarFrom = model.From
            val calendarTo = model.To


            val intent = Intent(instance.requireContext(), AddDayActivity::class.java)
            FriendName = model.Friend
            name = model.name
            intent.putExtra("calendar1", calendarFrom)
            intent.putExtra("nameofactivity", model.name)
            intent.putExtra("calendar2", calendarTo)
            intent.putExtra("sentTime", model.sentTime)
            intent.putExtra("FriendName", model.Friend)
//            startActivityForResult(instance.requireActivity(), intent, 222, null)

            instance.sentTime = model.sentTime
            instance.launchSomeActivity.launch(intent)


        }


        fun deleteActivity(view: View, model: Invitation) {
            val myRef = MainActivity.getMyRef()
            val myEmail = FirebaseAuth.getInstance().currentUser?.email
            val myFireEmail = myEmail.toString().replace(".", " ")
            val friendEmail = model.Friend
            val fireFriendEmail = friendEmail.replace('.', ' ')
            val firedatabase =
                Firebase.database("https://calendar-predict-default-rtdb.europe-west1.firebasedatabase.app/")
            val friendRef = firedatabase.getReference("users")
            calendar1 = model.sentTime

            myRef.child("invite").child(calendar1!!).removeValue()
            friendRef.child(fireFriendEmail).child("Negative").child(calendar1!!).child("name")
                .setValue(model.name)
            friendRef.child(fireFriendEmail).child("Negative").child(calendar1!!).child("Friend")
                .setValue(myFireEmail)
            friendRef.child(fireFriendEmail).child("Negative").child(calendar1!!).child("Sent")
                .setValue(calendar1)


        }


    }




}

