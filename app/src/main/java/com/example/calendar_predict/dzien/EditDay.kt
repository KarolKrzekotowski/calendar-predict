package com.example.calendar_predict.dzien

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.calendar_predict.R
import com.example.calendar_predict.databinding.EditDayBinding

class EditDay: AppCompatActivity() {
    private lateinit var binding: EditDayBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EditDayBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


    }

}