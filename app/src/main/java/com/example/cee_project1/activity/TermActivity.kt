package com.example.cee_project1.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cee_project1.databinding.ActivitySplashBinding
import com.example.cee_project1.databinding.ActivityTermBinding
import com.example.cee_project1.service.TermVPAdapter

class TermActivity : AppCompatActivity() {

    lateinit var binding : ActivityTermBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val termVPAdapter = TermVPAdapter()
        binding.termViewPager.adapter = termVPAdapter

        val id = intent.getIntExtra("id", -1)
        if(id != -1) {
            binding.termViewPager.setCurrentItem(id, true)
        }
    }

}