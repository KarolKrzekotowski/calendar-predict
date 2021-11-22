package com.example.calendar_predict

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout


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
    }

    fun openZnajomi(view: View){
        Log.i(null, "ZNAJOMI DO NAPISANIA")
    }

    fun openWydarzenia(view: View){
        Log.i(null, "WYDARZENIE DO NAPISANIA")
    }

    fun openExport(view: View){
        Log.i(null, "EXPORT DO NAPISANIA")
    }
}