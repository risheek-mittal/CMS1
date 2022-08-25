package com.example.ecomm.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ecomm.nav_fragments.CartFragment
import com.example.ecomm.nav_fragments.HomeFragment
import com.example.ecomm.tab_fragment.TabHomeFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->{
                TabHomeFragment()
            }
            1->{
                CartFragment()
            }
            else->{
                Fragment()
            }
        }
    }
}