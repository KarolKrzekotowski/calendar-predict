package com.example.calendar_predict.dzien

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.DataBase.Rating.RatingViewModel
import com.example.calendar_predict.databinding.GradeDayBinding


import kotlinx.android.synthetic.main.grade_day.*

class GradeDay : AppCompatActivity(){

    private lateinit var binding: GradeDayBinding
    private lateinit var ratingViewModel: RatingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = GradeDayBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        ratingViewModel = ViewModelProvider(this).get(RatingViewModel::class.java)
    }

    fun AcceptGrade(view: View){
        if(mark.text==null || mark.text.toString().toInt()>100 || mark.text.toString().toInt()<0) {
            Toast.makeText(this,"Podaj liczbę od 1 do 100",Toast.LENGTH_SHORT).show()
        }
        else {
            //dzien zmien evaluated na 1 a nastepnie update day
            //ratingViewModel.updateDay()
            //stworz nowa ocena, id = 0, day_id z dzien, rate = zczytaj ocene
                //ratingViewModel.addRating()
            finish()
        }
    }
    fun cancelMark(view: View){
        finish()
    }
}