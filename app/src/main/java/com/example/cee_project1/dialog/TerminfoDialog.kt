package com.example.cee_project1.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.cee_project1.databinding.CorrectAlertDialogBinding
import com.example.cee_project1.databinding.TermInfoDialogBinding

class TerminfoDialog(context: Context, private val okCallback:(String)->Unit): Dialog(context) {
    private lateinit var binding: TermInfoDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= TermInfoDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initbtn()
        initData()
    }

    private fun initbtn() {
        binding.termInfoDialogCloseBtnIv.setOnClickListener {
            this.dismiss()
        }
    }

    private fun initData() {
        //binding.termInfoDialogTermNameTv.text
    }
}