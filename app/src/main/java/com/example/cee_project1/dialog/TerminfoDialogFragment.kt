package com.example.cee_project1.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
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
//        initbtn()
        initData()
//        initMotion()

        val msg: String = arguments?.getString(KEY)?:"default"
        Log.d("KEYMES",msg)
        initView(msg)
        return view
    }

    private fun initMotion() {


        //GestureDetector 변수 전역선언
        var detector: GestureDetector
        detector = GestureDetector(context, object : GestureDetector.OnGestureListener {
            override fun onDown(motionEvent: MotionEvent): Boolean {
                // do something...
                Log.d("motionEvent", "onDown()호출")
                return true
            } /* onShowPress(), onSingleTapUp(), onScroll(), onLongPress(), onFling() ... 등등 */

            override fun onShowPress(p0: MotionEvent?) {

                Log.d("motionEvent", "onShowPress()호출")

            }

            override fun onSingleTapUp(p0: MotionEvent?): Boolean {
                Log.d("motionEvent", "onSingleTapUp()호출")
                return true
            }

            override fun onScroll(
                p0: MotionEvent?,
                p1: MotionEvent?,
                x: Float,
                y: Float
            ): Boolean {
                Log.d("motionEvent", "onScroll()호출")
                Log.d("motionEvent", "onScroll() ("+x+","+y+")")
                dismiss()
                return true
            }

            override fun onLongPress(p0: MotionEvent?) {
                Log.d("motionEvent", "onLongPress()호출")

            }

            override fun onFling(
                p0: MotionEvent?,
                p1: MotionEvent?,
                p2: Float,
                p3: Float
            ): Boolean {
                Log.d("motionEvent", "onFling()호출")
                return true
            }
        })


        binding.termInfoDialogCl.setOnTouchListener { view, motionEvent ->
            Log.d("motionEvent", "setOnTouchListener 들어옴")


            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    Log.d("motionEvent", "ACTION_DOWN")
                }
                MotionEvent.ACTION_MOVE -> {
                    Log.d("motionEvent", "ACTION_MOVE")
                }
                MotionEvent.ACTION_UP -> {
                    Log.d("motionEvent", "ACTION_UP")
                }
                MotionEvent.ACTION_POINTER_UP -> {
                    Log.d("motionEvent", "ACTION_POINTER_UP")
                }


            }
            detector.onTouchEvent(motionEvent) // detector에게 이벤트 전달, 처리요청
            true
        }


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

//    private fun initbtn() {
//        binding.termInfoDialogCloseBtnIv.setOnClickListener {
//            this.dismiss()
//        }
//    }

    private fun initData() {
        //binding.termInfoDialogTermNameTv.text
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }





}