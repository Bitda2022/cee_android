package com.example.cee_project1.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.cee_project1.R
import com.example.cee_project1.databinding.ActivityMainBinding
import com.example.cee_project1.fregment.InvestFragment
import com.example.cee_project1.fregment.QuizSettingFragment
import com.example.cee_project1.fregment.SettingFragment
import com.example.cee_project1.fregment.StudyFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pager.isUserInputEnabled = true
        binding.pager.adapter = ViewPagerAdapter(supportFragmentManager,lifecycle)
        binding.pager.registerOnPageChangeCallback(PageChangeCallback())
        binding.bottomNavigationView.setOnItemSelectedListener {navigationSelected(it) }
    }

    private fun navigationSelected(item: MenuItem): Boolean {
        val checked = item.setChecked(true)
        when (checked.itemId) {
            R.id.studyfragment-> {
                binding.pager.currentItem = 0
                return true
            }
            R.id.quizfragment -> {
                binding.pager.currentItem = 1
                return true
            }
            R.id.investfragment -> {
                binding.pager.currentItem = 2
                return true
            }
            R.id.settingfragment -> {
                binding.pager.currentItem = 3
                return true
            }
        }
        return false
    }

    private inner class ViewPagerAdapter (fragmentManager: FragmentManager,lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager,lifecycle){
        override fun getItemCount(): Int = 4

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> StudyFragment()
                1 -> QuizSettingFragment()
                2 -> InvestFragment()
                3 -> SettingFragment()
                else -> error("no such position: $position")
            }
        }

    }

    private inner class PageChangeCallback: ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            binding.bottomNavigationView.selectedItemId = when (position) {
                0 -> R.id.studyfragment
                1 -> R.id.quizfragment
                2 -> R.id.investfragment
                3 -> R.id.settingfragment
                else -> error("no such position: $position")
            }
        }
    }


}


