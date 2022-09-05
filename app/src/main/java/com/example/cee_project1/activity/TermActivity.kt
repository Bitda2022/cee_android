package com.example.cee_project1.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.cee_project1.CEEApplication.Companion.prefs
import com.example.cee_project1.data.Term
import com.example.cee_project1.databinding.ActivitySplashBinding
import com.example.cee_project1.databinding.ActivityTermBinding
import com.example.cee_project1.service.TermVPAdapter
import io.realm.Realm
import io.realm.kotlin.where

class TermActivity : AppCompatActivity() {

    lateinit var binding : ActivityTermBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val termVPAdapter = TermVPAdapter()
        binding.termViewPager.adapter = termVPAdapter
        binding.termViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                Log.d("viewpager listener", "onPageSelected: $position")
                val realm = Realm.getDefaultInstance()
                realm.executeTransaction {
                    val term : Term? = realm.where<Term>().containsValue("id", position).findFirst()
                    if(term != null) {
                        if (!term.hasStudied) {
                            term.hasStudied = true
                            prefs.setString("term_save_point", position.toString())
                        }
                    }
                }
            }
        })

        val id = intent.getIntExtra("id", -1)
        if(id != -1) {
            binding.termViewPager.setCurrentItem(id, true)
        }
    }

}