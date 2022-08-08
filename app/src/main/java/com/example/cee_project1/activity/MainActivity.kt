package com.example.cee_project1.activity

import ViewPagerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.cee_project1.R
import com.example.cee_project1.databinding.ActivityMainBinding
import com.example.cee_project1.databinding.ActivitySplashBinding
import com.example.cee_project1.fregment.InvestFragment
import com.example.cee_project1.fregment.QuizSettingFragment
import com.example.cee_project1.fregment.SettingFragment
import com.example.cee_project1.fregment.StudyFragment


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation()
    }

    private fun initBottomNavigation(){

        supportFragmentManager.beginTransaction()
            .replace(R.id.pager, StudyFragment())
            .commitAllowingStateLoss()

        binding.bottomNavigationView.setOnItemSelectedListener{ item ->
            when (item.itemId) {

                R.id.studyfragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.pager, StudyFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.quizfragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.pager, QuizSettingFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.investfragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.pager, InvestFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.settingfragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.pager, SettingFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

}


