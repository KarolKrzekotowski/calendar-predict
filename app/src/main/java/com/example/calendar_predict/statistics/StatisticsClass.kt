package com.example.calendar_predict.statistics

import com.example.calendar_predict.statistics.StatisticsSpinnerAdapter
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.DataBase.Activity.Activity
import com.DataBase.Activity.ActivityWithCategory
import com.DataBase.AppDataBase
import com.DataBase.Category.CategoryViewModel
import com.DataBase.Day.*
import com.DataBase.Rating.Rating
import com.example.calendar_predict.R
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import kotlinx.android.synthetic.main.statistics_page.*
import java.util.*
import kotlin.collections.ArrayList

class StatisticsClass : Fragment() {


    val calendar1 = Calendar.getInstance()
    val calendar2 = Calendar.getInstance()
    val calendar3 = Calendar.getInstance()

    private var ActivitiesBeofre7 = emptyList<Activity>()
    private var ActivitiesBeofre6 = emptyList<Activity>()
    private var ActivitiesBeofre30 = emptyList<Activity>()

    private var timeSpentSport = 0
    private var timeSpentNauka = 0
    private var timeSpentSen = 0
    private var timeSpentGry = 0
    private var timeSpentRelaks = 0
    private var timeSpentPraca = 0
    private var timeSpentSpotkania = 0


    private var ratings7 = emptyList<Rating>()
    private var ratings6 = emptyList<Rating>()
    private var ratings30 = emptyList<Rating>()

    var gradeHighest=0
    var gradeMean=0
    var gradeLowest=100

    lateinit var DayListViewModel: DayListViewModel
    private lateinit var pieChart: PieChart
    private lateinit var pieChart2: PieChart
    var Sport = 0
    var Nauka = 0
    var Praca = 0
    var Sen = 0
    var Gry = 0
    var Relaks = 0
    var Spotkania = 0
    var loop = 0
    var petla2 = 0



    var TimeList: Array<String> = arrayOf("ostatnie 7 dni","ostatnie 30 dni", "ostatnie 6 miesięcy" )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.statistics_page2, container, false)
        val spinner = view.findViewById<Spinner>(R.id.spinner3)
        pieChart = view.findViewById(R.id.pieChart)
        pieChart2 = view.findViewById(R.id.pieChart2)

        initPieChart()
        initPieChart2()

        Log.i("tutajjse1",calendar1.timeInMillis.toString())
        calendar1[Calendar.HOUR_OF_DAY] = 0
        calendar1[Calendar.MINUTE] = 0
        calendar1[Calendar.SECOND] = 0
        calendar1[Calendar.MILLISECOND] = 0
        calendar1[Calendar.DAY_OF_MONTH]= calendar1[Calendar.DAY_OF_MONTH] -7

        calendar2[Calendar.HOUR_OF_DAY] = 0
        calendar2[Calendar.MINUTE] = 0
        calendar2[Calendar.SECOND] = 0
        calendar2[Calendar.MILLISECOND] = 0
        calendar2[Calendar.DAY_OF_MONTH]= calendar2[Calendar.DAY_OF_MONTH] -30


        calendar3[Calendar.HOUR_OF_DAY] = 0
        calendar3[Calendar.MINUTE] = 0
        calendar3[Calendar.SECOND] = 0
        calendar3[Calendar.MILLISECOND] = 0
        calendar3[Calendar.DAY_OF_MONTH]= calendar3[Calendar.DAY_OF_MONTH] -180




        val appDBDao = AppDataBase.getDatabase(
            requireContext()
        ).appDBDao()
        ActivitiesBeofre7 = appDBDao.readAllActivitieswithCategories(calendar1.time)
        ActivitiesBeofre30 = appDBDao.readAllActivitieswithCategories(calendar2.time)
        ActivitiesBeofre6 = appDBDao.readAllActivitieswithCategories(calendar3.time)

        ratings7 = appDBDao.readAllRates(calendar1.time)
        ratings30 = appDBDao.readAllRates(calendar2.time)
        ratings6 = appDBDao.readAllRates(calendar3.time)
        //spinner
        spinner?.adapter = StatisticsSpinnerAdapter(activity?.applicationContext, TimeList)
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                when(id){
                    0.toLong() -> {
                        Sport = 0
                        Praca = 0
                        Sen = 0
                        Gry = 0
                        Relaks = 0
                        Spotkania = 0
                        Nauka = 0

                        timeSpentSport = 0
                        timeSpentNauka = 0
                        timeSpentSen = 0
                        timeSpentGry = 0
                        timeSpentRelaks = 0
                        timeSpentPraca = 0
                        timeSpentSpotkania = 0
                        gradeLowest = 100

                        for(list in ActivitiesBeofre7){

                                Log.i("siema",list.category_id.toString())
                            //                Sport+=1
                                when(list.category_id) {
                                    3 -> {
                                        Sport += 1
                                        timeSpentSport += (list.hour_to.time.toInt() - list.hour_from.time.toInt())/60/60/1000
                                    }
                                    4 -> {
                                        Nauka += 1
                                        timeSpentNauka += (list.hour_to.time.toInt() - list.hour_from.time.toInt())/60/60/1000
                                        }
                                    5   ->{
                                        Praca += 1
                                        timeSpentPraca += (list.hour_to.time.toInt() - list.hour_from.time.toInt())/60/60/1000
                                        }
                                    6   ->{
                                        Sen += 1
                                        timeSpentSen += (list.hour_to.time.toInt() - list.hour_from.time.toInt())/60/60/1000
                                        }
                                    7   ->{
                                        Gry += 1
                                        timeSpentGry += (list.hour_to.time.toInt() - list.hour_from.time.toInt())/60/60/1000
                                        }
                                    8   ->{
                                        Relaks += 1
                                        timeSpentRelaks += (list.hour_to.time.toInt() - list.hour_from.time.toInt())/60/60/1000
                                        }
                                    9   ->
                                        {
                                        Spotkania += 1
                                        timeSpentSpotkania += (list.hour_to.time.toInt() - list.hour_from.time.toInt())/60/60/1000
                                        }

                                }
//
                            }
                        if (ratings7.isEmpty()){
                            gradeLowest = 0
                        }
                        for (list in ratings7){
                            if (list.rate > gradeHighest)
                                gradeHighest = list.rate
                            if(list.rate < gradeLowest){
                                gradeLowest = list.rate
                            }
                            gradeMean += list.rate
                            loop += 1
                        }
                        if (loop>0){
                            gradeMean = gradeMean/loop
                        }
                        highest.text="Najwyższa ocena "+gradeHighest.toString()
                        lowest.text = "Najniższa ocena " + gradeLowest.toString()
                        mean.text = "Średnia ocena " + gradeMean.toString()
                        loop = 0
                        gradeMean = 0
                        setDataToPieChart()
                        setDataToPieChart2()



                    }

                    1.toLong() -> {
                        Sport = 0
                        Praca = 0
                        Sen = 0
                        Gry = 0
                        Relaks = 0
                        Spotkania = 0
                        Nauka = 0

                        timeSpentSport = 0
                        timeSpentNauka = 0
                        timeSpentSen = 0
                        timeSpentGry = 0
                        timeSpentRelaks = 0
                        timeSpentPraca = 0
                        timeSpentSpotkania = 0
                        gradeLowest = 100

                        for(list in ActivitiesBeofre30){

                            Log.i("siema",list.category_id.toString())
                            //                Sport+=1
                            when(list.category_id) {
                                3 -> {
                                    Sport += 1
                                    timeSpentSport += (list.hour_to.time.toInt() - list.hour_from.time.toInt())/60/60/1000
                                }
                                4 -> {
                                    Nauka += 1
                                    timeSpentNauka += (list.hour_to.time.toInt() - list.hour_from.time.toInt())/60/60/1000
                                }
                                5   ->{
                                    Praca += 1
                                    timeSpentPraca += (list.hour_to.time.toInt() - list.hour_from.time.toInt())/60/60/1000
                                }
                                6   ->{
                                    Sen += 1
                                    timeSpentSen += (list.hour_to.time.toInt() - list.hour_from.time.toInt())/60/60/1000
                                }
                                7   ->{
                                    Gry += 1
                                    timeSpentGry += (list.hour_to.time.toInt() - list.hour_from.time.toInt())/60/60/1000
                                }
                                8   ->{
                                    Relaks += 1
                                    timeSpentRelaks += (list.hour_to.time.toInt() - list.hour_from.time.toInt())/60/60/1000
                                }
                                9   ->
                                {
                                    Spotkania += 1
                                    timeSpentSpotkania += (list.hour_to.time.toInt() - list.hour_from.time.toInt())/60/60/1000
                                }

                            }
//

//
                        }
                        if (ratings30.isEmpty()){
                            gradeLowest = 0
                        }
                        for (list in ratings30){
                            if (list.rate > gradeHighest)
                                gradeHighest = list.rate
                            if(list.rate < gradeLowest){
                                gradeLowest = list.rate
                            }
                            gradeMean += list.rate
                            loop += 1
                        }
                        if (loop>0){
                            gradeMean = gradeMean/loop
                        }
                        highest.text="Najwyższa ocena "+gradeHighest.toString()
                        lowest.text = "Najniższa ocena " + gradeLowest.toString()
                        mean.text = "Średnia ocena " + gradeMean.toString()

                        loop = 0
                        gradeMean = 0
                        setDataToPieChart()
                        setDataToPieChart2()

                    }




                    2.toLong() -> {
                        Sport = 0
                        Praca = 0
                        Sen = 0
                        Gry = 0
                        Relaks = 0
                        Spotkania = 0
                        Nauka = 0

                        timeSpentSport = 0
                        timeSpentNauka = 0
                        timeSpentSen = 0
                        timeSpentGry = 0
                        timeSpentRelaks = 0
                        timeSpentPraca = 0
                        timeSpentSpotkania = 0
                        gradeLowest = 100

                        for(list in ActivitiesBeofre6){

                            Log.i("siema",list.category_id.toString())
                            //                Sport+=1
                            when(list.category_id) {
                                3 -> {
                                    Sport += 1
                                    timeSpentSport += (list.hour_to.time.toInt() - list.hour_from.time.toInt())/60/60/1000
                                }
                                4 -> {
                                    Nauka += 1
                                    timeSpentNauka += (list.hour_to.time.toInt() - list.hour_from.time.toInt())/60/60/1000
                                }
                                5   ->{
                                    Praca += 1
                                    timeSpentPraca += (list.hour_to.time.toInt() - list.hour_from.time.toInt())/60/60/1000
                                }
                                6   ->{
                                    Sen += 1
                                    timeSpentSen += (list.hour_to.time.toInt() - list.hour_from.time.toInt())/60/60/1000
                                }
                                7   ->{
                                    Gry += 1
                                    timeSpentGry += (list.hour_to.time.toInt() - list.hour_from.time.toInt())/60/60/1000
                                }
                                8   ->{
                                    Relaks += 1
                                    timeSpentRelaks += (list.hour_to.time.toInt() - list.hour_from.time.toInt())/60/60/1000
                                }
                                9   ->
                                {
                                    Spotkania += 1
                                    timeSpentSpotkania += (list.hour_to.time.toInt() - list.hour_from.time.toInt())/60/60/1000
                                }

                            }
//

//
                        }
                        if (ratings6.isEmpty()){
                            gradeLowest = 0
                        }
                        for (list in ratings6){
                            if (list.rate > gradeHighest)
                                gradeHighest = list.rate
                            if(list.rate < gradeLowest){
                                gradeLowest = list.rate
                            }
                            gradeMean += list.rate
                            loop += 1
                        }
                        if (loop>0){
                            gradeMean = gradeMean/loop
                        }


                        highest.text="Najwyższa ocena "+gradeHighest.toString()
                        lowest.text = "Najniższa ocena " + gradeLowest.toString()
                        mean.text = "Średnia ocena " + gradeMean.toString()
                        loop = 0
                        gradeMean = 0
                        setDataToPieChart()
                        setDataToPieChart2()

                    }
                }
            }


        }


        return view
    }
    private fun initPieChart() {
        pieChart.setUsePercentValues(true)
        pieChart.description.text = ""
        //hollow pie chart
        pieChart.isDrawHoleEnabled = false
        pieChart.setTouchEnabled(false)
        pieChart.setDrawEntryLabels(false)
        //adding padding
        pieChart.setExtraOffsets(20f, 0f, 20f, 50f)
        pieChart.setUsePercentValues(true)
        pieChart.isRotationEnabled = false
        pieChart.setDrawEntryLabels(false)
        pieChart.legend.orientation = Legend.LegendOrientation.VERTICAL
        pieChart.legend.isWordWrapEnabled = true
        pieChart.legend.textSize=13f

    }

    private fun setDataToPieChart() {
        pieChart.setUsePercentValues(false)
        val dataEntries = ArrayList<PieEntry>()
        dataEntries.add(PieEntry(Sport.toFloat(), "Sport"))
        dataEntries.add(PieEntry(Nauka.toFloat(), "Nauka"))
        dataEntries.add(PieEntry(Praca.toFloat(), "Praca"))
        dataEntries.add(PieEntry(Sen.toFloat(), "Sen"))
        dataEntries.add(PieEntry(Gry.toFloat(), "Gry"))
        dataEntries.add(PieEntry(Relaks.toFloat(), "Relaks"))
        dataEntries.add(PieEntry(Spotkania.toFloat(), "Spotkania"))

        val colors: ArrayList<Int> = ArrayList()
        colors.add(Color.parseColor("#"+Integer.toHexString(15423901)))
        colors.add(Color.parseColor("#"+Integer.toHexString(15423969)))
        colors.add(Color.parseColor("#"+Integer.toHexString(5383122)))
        colors.add(Color.parseColor("#"+Integer.toHexString(2347730)))
        colors.add(Color.parseColor("#"+Integer.toHexString(2347608)))
        colors.add(Color.parseColor("#"+Integer.toHexString(13816355)))
        colors.add(Color.parseColor("#"+Integer.toHexString(13795619)))


        val dataSet = PieDataSet(dataEntries, "")
        val data = PieData(dataSet)

        // In Percentage
        data.setValueFormatter(PercentFormatter())
        dataSet.sliceSpace = 3f
        dataSet.colors = colors
        pieChart.data = data
        data.setValueTextSize(15f)
        pieChart.setExtraOffsets(0f, 10f, 0f, 20f)
        pieChart.animateY(1400, Easing.EaseInOutQuad)

        //create hole in center
        pieChart.holeRadius = 58f
        pieChart.transparentCircleRadius = 61f
        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(Color.WHITE)


        //add text in center
        pieChart.setDrawCenterText(true);
        pieChart.centerText = "Ilość aktywności o określonej kategorii"
        pieChart.setCenterTextSize(20f)




        pieChart.invalidate()

    }

    private fun initPieChart2() {
        pieChart2.setUsePercentValues(true)
        pieChart2.description.text = ""

        //hollow pie chart
        pieChart2.isDrawHoleEnabled = false
        pieChart2.setTouchEnabled(false)
        pieChart2.setDrawEntryLabels(false)
        //adding padding
        pieChart2.setExtraOffsets(0f, 0f, 20f, 20f)
        pieChart2.setUsePercentValues(true)
        pieChart2.isRotationEnabled = false
        pieChart2.setDrawEntryLabels(false)
        pieChart2.legend.orientation = Legend.LegendOrientation.VERTICAL
        pieChart2.legend.isWordWrapEnabled = true
        pieChart2.legend.textSize=12f
        pieChart2.legend.horizontalAlignment=Legend.LegendHorizontalAlignment.LEFT


    }

    private fun setDataToPieChart2() {
        pieChart2.setUsePercentValues(false)
        val dataEntries = ArrayList<PieEntry>()

        dataEntries.add(PieEntry(timeSpentSport.toFloat(), "Sport"))
        dataEntries.add(PieEntry(timeSpentNauka.toFloat(), "Nauka"))
        dataEntries.add(PieEntry(timeSpentPraca.toFloat(), "Praca"))
        dataEntries.add(PieEntry(timeSpentSen.toFloat(), "Sen"))
        dataEntries.add(PieEntry(timeSpentGry.toFloat(), "Gry"))
        dataEntries.add(PieEntry(timeSpentRelaks.toFloat(), "Relaks"))
        dataEntries.add(PieEntry(timeSpentSpotkania.toFloat(), "Spotkania"))


        val colors: ArrayList<Int> = ArrayList()
        colors.add(Color.parseColor("#"+Integer.toHexString(15423901)))
        colors.add(Color.parseColor("#"+Integer.toHexString(15423969)))
        colors.add(Color.parseColor("#"+Integer.toHexString(5383122)))
        colors.add(Color.parseColor("#"+Integer.toHexString(2347730)))
        colors.add(Color.parseColor("#"+Integer.toHexString(2347608)))
        colors.add(Color.parseColor("#"+Integer.toHexString(13816355)))
        colors.add(Color.parseColor("#"+Integer.toHexString(13795619)))


        val dataSet = PieDataSet(dataEntries, "")
        val data = PieData(dataSet)

        // In Percentage
        data.setValueFormatter(PercentFormatter())
        dataSet.sliceSpace = 3f
        dataSet.colors = colors
        pieChart2.data = data
        data.setValueTextSize(15f)
        pieChart2.setExtraOffsets(0f, 10f, 0f, 20f)
        pieChart2.animateY(1400, Easing.EaseInOutQuad)

        //create hole in center
        pieChart2.holeRadius = 58f
        pieChart2.transparentCircleRadius = 61f
        pieChart2.isDrawHoleEnabled = true
        pieChart2.setHoleColor(Color.WHITE)


        //add text in center
        pieChart2.setDrawCenterText(true);
        pieChart2.centerText = "Czas poświęcony na poszczególne kategorie w godzinach"
        pieChart2.setCenterTextSize(20f)




        pieChart2.invalidate()

    }



}