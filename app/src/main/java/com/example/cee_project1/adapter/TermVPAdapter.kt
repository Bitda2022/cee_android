package com.example.cee_project1.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cee_project1.data.Term
import com.example.cee_project1.databinding.ItemTermBinding
import io.realm.Realm
import io.realm.kotlin.where

class TermVPAdapter : RecyclerView.Adapter<TermVPAdapter.ViewHolder>() {

    private val termList = Realm.getDefaultInstance().where<Term>().findAll()

    inner class ViewHolder(val binding : ItemTermBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(term : Term) {
            binding.itemTermNameTv.text = term.name
            binding.itemTermDescriptionTv.text = term.description
            if(term.example.isEmpty() && term.metaphor.isEmpty()) {
                binding.itemTermStepCl.visibility = View.INVISIBLE
                binding.itemTermLine2.visibility = View.INVISIBLE
                binding.itemTermExamplesTv.text = ""
            } else if(term.example.isNotEmpty()) {
                binding.itemTermStepCl.visibility = View.VISIBLE
                binding.itemTermLine2.visibility = View.VISIBLE
                binding.itemTermExamplesTv.text = term.example
            } else if(term.metaphor.isNotEmpty()) {
                binding.itemTermStepCl.visibility = View.VISIBLE
                binding.itemTermLine2.visibility = View.VISIBLE
                binding.itemTermExamplesTv.text = term.metaphor
            } else {
                binding.itemTermStepCl.visibility = View.VISIBLE
                binding.itemTermLine2.visibility = View.VISIBLE
                binding.itemTermExamplesTv.text = term.example + "\n\n" + term.metaphor
            }
            binding.itemTermPageTv.text = (term.id + 1).toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTermBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(termList[position] != null) {
            holder.bind(termList[position]!!)
        }
    }

    override fun getItemCount(): Int = termList.size

}