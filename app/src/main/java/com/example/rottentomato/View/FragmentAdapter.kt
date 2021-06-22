package com.example.rottentomato.View

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :FragmentStateAdapter(fragmentManager,lifecycle) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {

      return  when(position){
            1 ->  upcomingFrag()
            2 ->  popularFrag()
            3 ->  nowPlayingFrag()
          else -> TopRatedFrag()
        }
    }

}

// only one fragment should be there

