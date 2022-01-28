package com.example.calendar_predict.activityInvitation

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
import kotlinx.android.synthetic.main.activity_invitation.view.*
import java.lang.Thread.sleep

class ActivityInvitationFragment : Fragment() {
    var adapter: inviteAdapter?=null
    var rvInvite : RecyclerView?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        instance = this
        val myRef = MainActivity.getMyRef().child("invite")

        val ReadRef =myRef.orderByChild("From")
        val layout = inflater.inflate(R.layout.activity_invitation,container,false)
        rvInvite = layout?.rvInvitation
        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        decoration.setDrawable(ColorDrawable(Color.WHITE))
        rvInvite?.addItemDecoration(decoration)


        rvInvite?.layoutManager = LinearLayoutManager(context)



        Log.i("Read", ReadRef.toString())
        val options = FirebaseRecyclerOptions.Builder<Invitation>()
            .setQuery(
                ReadRef,
                Invitation::class.java
            ).build()
        adapter = inviteAdapter(options)
        rvInvite?.adapter = adapter
        Log.i("Read", adapter.toString())

        return layout
    }

    override fun onStart(){
        super.onStart()
        adapter?.startListening()


    }



companion object {
    private lateinit var instance: ActivityInvitationFragment

    fun AcceptActivity(view: View, model: Invitation) {
        val calendarFrom = model.From
        val calendarTo = model.To


        val intent= Intent(instance.requireContext(), AddDayActivity::class.java)

        intent.putExtra("calendar1", calendarFrom)
        intent.putExtra("nameofactivity", model.name)
        intent.putExtra("calendar2", calendarTo)
        intent.putExtra(" sentTime", model.sentTime)
        startActivity(instance.requireActivity(),intent,null)

//        Log.i("weszlo15", "hej")
//        sleep(1000)
//        val myRef = MainActivity.getMyRef()
//        val myEmail = FirebaseAuth.getInstance().currentUser?.email
//        val myFireEmail = myEmail.toString().replace(".", " ")
//        val friendEmail = model.Friend
//        val fireFriendEmail = friendEmail.replace('.',' ')
//        val firedatabase = Firebase.database("https://calendar-predict-default-rtdb.europe-west1.firebasedatabase.app/")
//        val friendRef  = firedatabase.getReference("users")
//        val calendar1 = model.sentTime
//
//        val succesfull2 = instance.succesfull2
//        Log.i("WEszlo?", succesfull2.toString())
//        if (succesfull2){
//
////            myRef.child("invite").child(calendar1).removeValue()
//
//        }
        //firebase
    }



    fun deleteActivity(view: View, model: Invitation) {
        val myRef = MainActivity.getMyRef()
        val myEmail = FirebaseAuth.getInstance().currentUser?.email
        val myFireEmail = myEmail.toString().replace(".", " ")
        val friendEmail = model.Friend
        val fireFriendEmail = friendEmail.replace('.',' ')
        val firedatabase = Firebase.database("https://calendar-predict-default-rtdb.europe-west1.firebasedatabase.app/")
        val friendRef  = firedatabase.getReference("users")
        val calendar1 = model.sentTime

        myRef.child("invite").child(calendar1).removeValue()




    }



    }

}

