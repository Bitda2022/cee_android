package com.example.cee_project1.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cee_project1.activity.TermActivity
import com.example.cee_project1.databinding.FragmentTermListBinding
import com.example.cee_project1.service.TermListRVAdapter

class TermListFragment(private val position : Int) : Fragment() {

    lateinit var binding : FragmentTermListBinding

    private val urls = arrayOf("knowledge/economy_basic.html", "knowledge/financial_basic.html", "knowledge/stock_advanced.html")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTermListBinding.inflate(inflater, container, false)

        val termListRVAdapter = TermListRVAdapter(urls[position])
        termListRVAdapter.setOnItemClickListener(object : TermListRVAdapter.OnItemClickListener() {
            override fun click(id : Int) {
                val intent = Intent(context, TermActivity::class.java)
                intent.putExtra("id", id)
                startActivity(intent)
                activity?.finish()
            }
        })
        binding.termListRecyclerView.adapter = termListRVAdapter

        return binding.root
    }

}