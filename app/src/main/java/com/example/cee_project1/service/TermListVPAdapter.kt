package com.example.cee_project1.service

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cee_project1.fragment.TermListFragment

class TermListVPAdapter(fragment : FragmentActivity) : FragmentStateAdapter(fragment) {

    override fun getItemCount() : Int = 3

    override fun createFragment(position: Int): Fragment {
        return TermListFragment(position)
    }

}