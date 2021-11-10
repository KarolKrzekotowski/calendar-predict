package com.example.calendar_predict

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TableLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    lateinit var tablayout: TabLayout
    lateinit var viewPager: ViewPager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tablayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)
        tablayout.addTab(tablayout.newTab().setText("Dzień"))
        tablayout.addTab(tablayout.newTab().setText("Kalendarz"))
        tablayout.addTab(tablayout.newTab().setText("Statystyki"))
        val adapter = MyPagerAdapter(this,supportFragmentManager,tablayout.tabCount)
        viewPager.adapter =  adapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tablayout))
        tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        }
        )
    }
    fun openSettings(view: View){
        val myintent = Intent(this,Settings::class.java)
        startActivity(myintent)
    }
    fun openGoals(view: View){
        val intent = Intent(this,Goals::class.java)
        startActivity(intent)
    }

}