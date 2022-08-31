package com.example.cee_project1.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.cee_project1.R
import com.example.cee_project1.databinding.ActivityInvestSelectBinding
import com.example.cee_project1.databinding.ActivityQuizBinding

class InvestSelectActivity : AppCompatActivity() {
    lateinit var binding:ActivityInvestSelectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInvestSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activityInvestSelectCompany1CoinCl.visibility= View.GONE
        binding.activityInvestSelectCompany2CoinCl.visibility= View.GONE
        binding.activityInvestSelectCompany3CoinCl.visibility= View.GONE
        binding.activityInvestSelectSavingsCoinCl.visibility= View.GONE
    }
}