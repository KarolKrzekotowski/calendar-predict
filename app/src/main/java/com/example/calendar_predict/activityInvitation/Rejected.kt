package com.example.calendar_predict.activityInvitation

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar_predict.MainActivity
import com.example.calendar_predict.R
import com.firebase.ui.database.FirebaseRecyclerOptions
import kotlinx.android.synthetic.main.accepted_invite.view.*
import kotlinx.android.synthetic.main.rejected_invite.view.*

class Rejected : Fragment() {
    var adapter: RejectedAdapter? = null
    var rvRejected: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        instance  = this

        val myRef = MainActivity.getMyRef().child("Negative")

        val ReadRef = myRef.orderByChild("Friend")
        val layout = inflater.inflate(R.layout.rejected_invite, container, false)
        rvRejected = layout?.RvRejected
        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        decoration.setDrawable(ColorDrawable(Color.WHITE))
        rvRejected?.addItemDecoration(decoration)

//friendRef.child(fireFriendEmail).child("Positive").child(InviteTime.toString()).child("name").setValue(name)
        rvRejected?.layoutManager = LinearLayoutManager(context)



        Log.i("Read", ReadRef.toString())
        val options = FirebaseRecyclerOptions.Builder<InviteChoice>()
            .setQuery(
                ReadRef,
                InviteChoice::class.java
            ).build()
        adapter = RejectedAdapter(options)
        rvRejected?.adapter = adapter
        Log.i("Read", adapter.toString())

        return layout
    }

    override fun onStart() {
        super.onStart()
        adapter?.startListening()
    }


    companion object{


        private lateinit var instance: Rejected
        fun deleteActivity(view: View, model: InviteChoice){
            val calendar1 = model.Sent
            val myRef = MainActivity.getMyRef()
            myRef.child("Negative").child(calendar1).removeValue()


        }
    }
}