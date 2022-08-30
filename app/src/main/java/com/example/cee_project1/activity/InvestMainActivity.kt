package com.example.cee_project1.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cee_project1.R
import com.example.cee_project1.databinding.ActivityInvestMainBinding
import com.example.cee_project1.databinding.ActivityInvestSelectBinding

class InvestMainActivity : AppCompatActivity() {
    lateinit var binding:ActivityInvestMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInvestMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}