package com.example.cee_project1.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.cee_project1.R
import com.example.cee_project1.databinding.FragmentSettingBinding


class SettingFragment : Fragment() {

    lateinit var binding: FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(inflater, container, false)

        eyespinner()

        return binding.root

    }

    private fun eyespinner() {
        val handicap = resources.getStringArray(R.array.handicap_level)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, handicap)
        binding.setEyeSp.adapter = adapter
    }
}

