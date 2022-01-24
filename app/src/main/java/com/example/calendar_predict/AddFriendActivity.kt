package com.example.calendar_predict

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class AddFriendActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_friend)
    }

    fun sendFriendInvitation(view: android.view.View) {
        val myRef = MainActivity.getMyRef()


        if (findViewById<EditText>(R.id.editTextTextEmailAddress).text.toString() != FirebaseAuth.getInstance().currentUser?.email ?: 0) {

            val email = findViewById<EditText>(R.id.editTextTextEmailAddress).text.replace(Regex("\\."), " ")

            myRef.parent?.child(email)
                ?.get()
                ?.addOnSuccessListener {
                    Log.i("Firebasetest", it.value.toString() + " " + email + "op")
                    for (category in it.children) {
                        if (category.key.equals("messages")) {
                            val invitation = mutableMapOf<String, String>()
                            invitation.putIfAbsent("type", "friend-invitation")
                            FirebaseAuth.getInstance().currentUser?.email?.let {
                                invitation.putIfAbsent(
                                    "sender",
                                    it
                                )
                            }
                            invitation.putIfAbsent("type", "friend-invitation")

                            myRef.parent?.child(email)!!.child("messages").push()
                                .setValue(invitation)
                            Toast.makeText(this, "Zaproszenie zostało wysłane", Toast.LENGTH_LONG).show()

                            finish()
                            return@addOnSuccessListener
                        }
                    }
                    Toast.makeText(this, "Użytkownik nie istnieje", Toast.LENGTH_LONG).show()
                }?.addOnFailureListener {
                    Toast.makeText(this, "Niepowodzenie: $it", Toast.LENGTH_SHORT).show()
                }
        }
        else
        {
            Toast.makeText(this, "Niepowodzenie", Toast.LENGTH_SHORT).show()
        }
    }
}