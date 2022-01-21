package com.example.calendar_predict

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.calendar_predict.googleCalendar.UpdateGoogleCalendarActivity
import com.google.android.material.tabs.TabLayout
import androidx.annotation.NonNull

import com.google.android.gms.tasks.OnCompleteListener

import com.firebase.ui.auth.AuthUI





lateinit var button_wyloguj: Button
lateinit var button_znajomi: Button
lateinit var button_wydarzenia: Button
lateinit var button_export: Button


class Settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)

        button_wyloguj = findViewById(R.id.button8)
        button_znajomi = findViewById(R.id.button9)
        button_wydarzenia = findViewById(R.id.button10)
        button_export = findViewById(R.id.button11)
    }
    fun openWyloguj(view: View){
        Log.i(null, "WYLOGUJ SIE DO NAPISANIA")
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                //TODO zmienic guzik na zaloguj?
                Toast.makeText(this, "Pomyślnie wylogowano", Toast.LENGTH_SHORT).show()
            }
    }

    fun openZnajomi(view: View){
        val intent = Intent(this, FriendsListActivity::class.java)
        startActivity(intent)
    }

    fun openWydarzenia(view: View){
        Log.i(null, "WYDARZENIE DO NAPISANIA")
    }

    fun openExport(view: View){
        val intent = Intent(this, UpdateGoogleCalendarActivity::class.java)
        startActivity(intent)
    }
}