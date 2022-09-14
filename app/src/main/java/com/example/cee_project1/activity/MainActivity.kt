package com.example.cee_project1.activity

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.cee_project1.CEEApplication
import com.example.cee_project1.CEEApplication.Companion.tts
import com.example.cee_project1.R
import com.example.cee_project1.data.Quiz
import com.example.cee_project1.data.Term
import com.example.cee_project1.databinding.ActivityMainBinding
import com.example.cee_project1.fragment.InvestFragment
import com.example.cee_project1.fragment.SettingFragment
import com.example.cee_project1.fragment.StudyFragment
import com.example.cee_project1.fregment.QuizSettingFragment
import io.realm.Realm
import io.realm.kotlin.where


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pager.isUserInputEnabled = true
        binding.pager.adapter = ViewPagerAdapter(supportFragmentManager,lifecycle)
        binding.pager.registerOnPageChangeCallback(PageChangeCallback())
        binding.bottomNavigationView.setOnItemSelectedListener { navigationSelected(it) }

        testDatabase()



    }



    private fun testDatabase() {
        val realm = Realm.getDefaultInstance()
        val version = CEEApplication.prefs.getString("version", "main_null")
        Log.d("version", "onCreate: version: $version")

        val term = realm.where<Term>().contains("name", "생산").findFirst()
        Log.d("test", "testDatabase: " + term?.quizs?.size.toString())

        val termList = realm.where<Term>().findAll()
        var str1 : String
        for(tmp in termList) {
            str1 = "\n-----------------------------------\n"
            str1 += termList.size.toString() + "\n"
            str1 += "id : " + tmp.id + "\n"
            str1 += "name : " + tmp.name + "\n"
            str1 += "type : " + tmp.type + "\n"
            str1 += "description : " + tmp.description + "\n"
            str1 += "hasStudied : " + tmp.hasStudied + "\n"
            if(tmp.quizs?.size!! > 0)
                str1 += "quizs : " + tmp.quizs?.get(0)?.content + "\n"
            Log.d("term", "onCreate: terms: $str1")
        }

        val quizList = realm.where<Quiz>().findAll()
        var str2 : String
        for(tmp in quizList) {
            str2 = "\n-----------------------------------\n"
            str2 += quizList.size.toString() + "\n"
            str2 += "id : " + tmp.id + "\n"
            str2 += "term : " + tmp.term + "\n"
            str2 += "content : " + tmp.content + "\n"
            str2 += "answer : " + tmp.answer + "\n"
            str2 += "commentary : " + tmp.commentary + "\n"
            str2 += "wrong : " + tmp.wrong + "\n"
            Log.d("quiz", "onCreate: quizs: $str2")
        }
    }

    private fun navigationSelected(item: MenuItem): Boolean {
        val checked = item.setChecked(true)
        when (checked.itemId) {
            R.id.study_fragment-> {
                binding.pager.currentItem = 0
                return true
            }
            R.id.quiz_fragment -> {
                binding.pager.currentItem = 1
                return true
            }
            R.id.invest_fragment -> {
                binding.pager.currentItem = 2
                return true
            }
            R.id.setting_fragment -> {
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
                0 -> R.id.study_fragment
                1 -> R.id.quiz_fragment
                2 -> R.id.invest_fragment
                3 -> R.id.setting_fragment
                else -> error("no such position: $position")
            }
        }
    }




}


