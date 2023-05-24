package com.example.mygithubuser.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mygithubuser.fragment.FollowFragment

class SectionPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    var userName: String = ""

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                val followerFragment = FollowFragment()
                followerFragment.arguments = Bundle().apply {
                    putInt(FollowFragment.ARG_POSITION, 1)
                    putString(FollowFragment.ARG_USERNAME, userName)
                }
                followerFragment
            }
            1 -> {
                val followingFragment = FollowFragment()
                followingFragment.arguments = Bundle().apply {
                    putInt(FollowFragment.ARG_POSITION, 2)
                    putString(FollowFragment.ARG_USERNAME, userName)
                }
                followingFragment
            }
            else -> throw IllegalStateException("Invalid adapter position")
        }
    }

}