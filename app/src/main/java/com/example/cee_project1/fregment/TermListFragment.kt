package com.example.cee_project1.fregment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cee_project1.databinding.FragmentStudyBinding
import com.example.cee_project1.databinding.FragmentTermListBinding

class TermListFragment : Fragment() {

    lateinit var binding : FragmentTermListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTermListBinding.inflate(inflater, container, false)

        return binding.root
    }

}