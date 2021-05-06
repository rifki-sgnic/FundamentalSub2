package com.dicoding.picodiploma.fundamentalsub2.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.picodiploma.fundamentalsub2.FollowersFragment
import com.dicoding.picodiploma.fundamentalsub2.FollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var username: String? = null

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = username?.let { FollowersFragment.newInstance(it) }
            1 -> fragment = username?.let { FollowingFragment.newInstance(it) }
        }
        return fragment as Fragment
    }

}