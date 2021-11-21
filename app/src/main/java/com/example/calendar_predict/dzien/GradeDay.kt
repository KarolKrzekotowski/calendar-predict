package com.example.calendar_predict.dzien

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.calendar_predict.databinding.GradeDayBinding


import kotlinx.android.synthetic.main.grade_day.*

class GradeDay : AppCompatActivity(){

    private lateinit var binding: GradeDayBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = GradeDayBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    
    }

    fun AcceptGrade(view: View){
        if(mark.text==null || mark.text.toString().toInt()>100 || mark.text.toString().toInt()<0) {
            Toast.makeText(this,"Podaj liczbę od 1 do 100",Toast.LENGTH_SHORT).show()
        }
        else {
            //TODO zapisz dane
            finish()
        }
    }
    fun cancelMark(view: View){
        finish()
    }
}