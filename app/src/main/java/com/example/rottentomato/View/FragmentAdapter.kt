package com.example.rottentomato.View

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :FragmentStateAdapter(fragmentManager,lifecycle) {

    private val tabs = arrayOf("top_rated","upcoming","popular","now_playing")

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
      return CategoryFrag(tabs[position])
    }

}

