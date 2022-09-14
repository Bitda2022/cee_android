package com.example.cee_project1.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.content.res.ColorStateListInflaterCompat.inflate
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.cee_project1.R
import com.example.cee_project1.databinding.ActivityFinishQuizBinding.inflate
import com.example.cee_project1.databinding.FragmentInvestBinding
import com.example.cee_project1.databinding.FragmentTutorialDialogBinding
import com.example.cee_project1.databinding.TermInfoDialogBinding


class TutorialDialogFragment : DialogFragment() {
    lateinit var binding : FragmentTutorialDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTutorialDialogBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        initMotion()
        return binding.root

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


        binding.fragmentTutorialDialogCl.setOnTouchListener { view, motionEvent ->
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



}