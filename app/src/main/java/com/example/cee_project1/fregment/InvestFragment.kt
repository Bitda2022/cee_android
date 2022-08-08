package com.example.cee_project1.fregment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cee_project1.databinding.FragmentInvestBinding

class InvestFragment : Fragment() {

    lateinit var binding : FragmentInvestBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInvestBinding.inflate(inflater, container, false)

        return binding.root
    }

}