package com.example.cee_project1.service

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cee_project1.data.Term
import com.example.cee_project1.databinding.ItemTermListBinding
import io.realm.Realm
import io.realm.kotlin.where

class TermListRVAdapter(url : String) : RecyclerView.Adapter<TermListRVAdapter.ViewHolder>() {

    private val termList = Realm.getDefaultInstance().where<Term>().contains("type", url).findAll()

    inner class ViewHolder(val binding : ItemTermListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(term : Term) {
            binding.itemTermNameTv.text = term.name
            binding.itemTermLine.visibility = View.VISIBLE
            binding.root.setOnClickListener {
                onItemClickListener.click(term.id)
            }
        }
    }

    open class OnItemClickListener() {
        open fun click(id : Int) {
            return
        }
    }

    private var onItemClickListener = OnItemClickListener()

    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTermListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(termList[position] != null) {
            //Log.d("recycler view", "onBindViewHolder: $position / ${termList.size} / " + termList[position]!!.name)
            holder.bind(termList[position]!!)
            if(position == termList.size - 1)
                holder.binding.itemTermLine.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = termList.size

}