package com.example.cee_project1.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.cee_project1.R
import com.example.cee_project1.data.Term
import com.example.cee_project1.databinding.ActivityMainBinding
import com.example.cee_project1.fragment.InvestFragment
import com.example.cee_project1.fragment.QuizSettingFragment
import com.example.cee_project1.fragment.SettingFragment
import com.example.cee_project1.fragment.StudyFragment
import com.example.cee_project1.service.DownloadData


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

        // test code
        class MyHandler : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                val bundle = msg.data
                Log.d("main", "handleMessage: " + bundle.getString("version"))
            }
        }
        val handler = MyHandler()
        DownloadData().getVersion(handler)

        class MyHandler2 : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                val termList = msg.obj as ArrayList<Term>
                for (term in termList) {
                    var str = ""
                    str += (term.id.toString() + "\n")
                    str += (term.name + "\n")
                    str += (term.description + "\n")
                    Log.d("term", "handleMessage: \n$str\n---------------------------")
                }
            }
        }
        val handler2 = MyHandler2()
        DownloadData().downloadTerms(handler2)
        // --------------------------------------------
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


