package com.example.cee_project1.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.example.cee_project1.databinding.WrongAlertDialogBinding


class WrongAlertDialog(context: Context,private val okCallback:(String)->Unit): Dialog(context) {
    private lateinit var binding: WrongAlertDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=WrongAlertDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        //뒤로가기 버튼, 빈 화면 터치를 통해 dialog가 사라짐
        //setCancelMessage(true)

        //background를 투명하게 만듦
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    }
}