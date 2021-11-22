package com.example.calendar_predict.dzien

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.DataBase.Category.CategoryViewModel
import com.DataBase.Day.DayUpdateViewModel
import com.DataBase.Day.DayUpdateViewModelFactory
import com.DataBase.Day.DayViewModel
import com.DataBase.Rating.Rating
import com.DataBase.Rating.RatingViewModel
import com.example.calendar_predict.databinding.GradeDayBinding


import kotlinx.android.synthetic.main.grade_day.*
import java.util.Calendar

class GradeDay : AppCompatActivity(){

    private lateinit var binding: GradeDayBinding
    private lateinit var ratingViewModel: RatingViewModel
    var calendar = Calendar.getInstance()
    var dayid =0
    var ocena = 0
    var year = 0
    var day= 0
    var month = 0
    private lateinit var dayUpdateViewModel:DayUpdateViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = GradeDayBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val datereceived =intent.extras

            
            day = datereceived?.getString("day")!!.toInt()
            month = datereceived.getString("month")!!.toInt()

            year = datereceived.getString("year")!!.toInt()


            calendar[Calendar.DAY_OF_MONTH] = day.toInt()
            calendar[Calendar.MONTH] = month.toInt()
            calendar[Calendar.YEAR] = year.toInt()
            calendar[Calendar.HOUR_OF_DAY] = 0
            calendar[Calendar.MINUTE] = 0
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0


            val factory = DayUpdateViewModelFactory(application, calendar.time)
            dayUpdateViewModel =
                ViewModelProvider(this, factory).get(DayUpdateViewModel::class.java)

            ratingViewModel = ViewModelProvider(this).get(RatingViewModel::class.java)

        }


    fun AcceptGrade(view: View){
        if(mark.text==null || mark.text.toString().toInt()>100 || mark.text.toString().toInt()<0) {
            Toast.makeText(this,"Podaj liczbę od 1 do 100",Toast.LENGTH_SHORT).show()
        }
        else {
            dayUpdateViewModel.day.evaluated=1
            ratingViewModel.updateDay(dayUpdateViewModel.day)

            ocena = mark.text.toString().toInt()
            var rate = Rating(0,dayUpdateViewModel.day.id,ocena)
            ratingViewModel.addRating(rate)

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