package com.example.cee_project1.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.cee_project1.CEEApplication
import com.example.cee_project1.activity.QuizActivity
import com.example.cee_project1.data.Term
import com.example.cee_project1.databinding.TermInfoDialogBinding
import io.realm.Realm
import io.realm.kotlin.where

class TerminfoDialogFragment : DialogFragment(){
    private var _binding: TermInfoDialogBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val KEY = "key"
        fun newInstance(msg:String): TerminfoDialogFragment? {
            Log.d("KEYMESVAL",msg)
            val fragment = TerminfoDialogFragment()
            val bundle1 = Bundle()
            bundle1.putString(KEY,msg)
            fragment.arguments =bundle1


            Log.d("KEYMESCOM",fragment.arguments?.getString(KEY).toString())
            return fragment
        }
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = TermInfoDialogBinding.inflate(inflater, container, false)
        val view = binding.root
        // 레이아웃 배경을 투명하게 해줌, 필수 아님
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        initbtn()
        initData()

        val msg: String = arguments?.getString(KEY)?:"default"
        Log.d("KEYMES",msg)
        initView(msg)
        return view
    }

    private fun initView(msg:String) {

        val realm = Realm.getDefaultInstance()
        val version = CEEApplication.prefs.getString("version", "main_null")
        Log.d("version", "onCreate: version: $version")

        val term = realm.where<Term>().contains("name", msg).findFirst()
        Log.d("showTerm", "showTerm: " + term?.description)


        binding.termInfoDialogTermNameTv.text=term?.name
        binding.termInfoDialogTermContentTv.text=term?.description
        binding.termInfoDialogExampleTv.text=term?.example


    }

    private fun initbtn() {
        binding.termInfoDialogCloseBtnIv.setOnClickListener {
            this.dismiss()
        }
    }

    private fun initData() {
        //binding.termInfoDialogTermNameTv.text
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}