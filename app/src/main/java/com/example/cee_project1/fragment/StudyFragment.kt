package com.example.cee_project1.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cee_project1.activity.TermListActivity
import com.example.cee_project1.databinding.FragmentStudyBinding

class StudyFragment : Fragment() {

    lateinit var binding : FragmentStudyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudyBinding.inflate(inflater, container, false)

        binding.studySeeListBtn.setOnClickListener {
            val intent = Intent(context, TermListActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

}