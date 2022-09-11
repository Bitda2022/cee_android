package com.example.cee_project1.service

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import com.example.cee_project1.CEEApplication.Companion.tts
import java.util.*
import kotlin.concurrent.timer

class AccessibilityTTSHandler {

    private enum class AccessibilityState { HOVER_IN, HOVER_EXIT, FOCUS, FOCUS_CLEAR, SCROLLED }
    private var state : AccessibilityState = AccessibilityState.FOCUS_CLEAR
    private var timer : Timer? = null
    private var time = 0

    fun handleAccessibility(activity : Activity) {
        val contentView = activity.findViewById<View>(android.R.id.content)
        contentView.accessibilityDelegate = object : View.AccessibilityDelegate() {
            override fun onRequestSendAccessibilityEvent(
                host: ViewGroup?,
                child: View?,
                event: AccessibilityEvent?
            ): Boolean {
                Log.d("tts", "onRequestSendAccessibilityEvent: / $event")
                when (event?.eventType) {
                    AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED -> {
                        state = AccessibilityState.FOCUS
                    }
                    AccessibilityEvent.TYPE_VIEW_HOVER_ENTER -> {
                        goBackTimerStart()
                        state = AccessibilityState.HOVER_IN
                    }
                    AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED -> {
                        if(state != AccessibilityState.HOVER_IN) {
                            tts.pause()
                        }
                        state = AccessibilityState.FOCUS_CLEAR
                    }
                    AccessibilityEvent.TYPE_VIEW_HOVER_EXIT -> {
                        goBackTimerCancel()
                        tts.execute()
                        state = AccessibilityState.HOVER_EXIT
                    }
                    AccessibilityEvent.TYPE_VIEW_SCROLLED -> {
                        state = AccessibilityState.SCROLLED
                    }
                    else -> {
                        Log.d("tts", "onRequestSendAccessibilityEvent: else")
                    }
                }
                return super.onRequestSendAccessibilityEvent(host, child, event)
            }
        }
    }

    private fun goBackTimerStart() {
        timer = timer(period = 500) {
            time++
            if(time > 4) {
                tts.goBack()
            }
        }
    }

    private fun goBackTimerCancel() {
        timer?.cancel()
        time = 0
    }

}