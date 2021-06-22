package com.example.rottentomato

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.rottentomato.View.FragmentAdapter
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {


    lateinit var tabLayout: TabLayout
    lateinit var viewPager:ViewPager2
    lateinit var adapter: FragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager2)

        adapter = FragmentAdapter(supportFragmentManager,lifecycle)
        viewPager.adapter = adapter

        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    viewPager.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })

    }

}