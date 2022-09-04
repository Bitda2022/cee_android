package com.example.cee_project1.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cee_project1.databinding.ActivityInvestFinalBinding
import com.example.cee_project1.databinding.ActivityInvestResultBinding

class InvestFinalActivity : AppCompatActivity() {
    lateinit var binding : ActivityInvestFinalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInvestFinalBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}