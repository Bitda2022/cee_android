package com.example.cee_project1.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cee_project1.CEEApplication
import com.example.cee_project1.CEEApplication.Companion.tts
import com.example.cee_project1.R
import com.example.cee_project1.databinding.FragmentInvestMainBinding
import com.example.cee_project1.databinding.FragmentInvestSelectBinding


class InvestMainFragment(var stories:ArrayList<String>) : Fragment() {

    lateinit var binding : FragmentInvestMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInvestMainBinding.inflate(inflater, container, false)

        setStories()

        return binding.root
    }

    private fun setStories() {
        var storiesString=""
        for(story in stories){
            storiesString+=story
            storiesString+="\n"
        }
        binding.fragmentInvestMainStoriesTv.text=storiesString
        Log.d("invest_test:스토리",storiesString)
    }








}