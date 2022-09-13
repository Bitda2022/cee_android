package com.example.cee_project1.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.cee_project1.CEEApplication.Companion.prefs
import com.example.cee_project1.CEEApplication.Companion.tts
import com.example.cee_project1.data.Term
import com.example.cee_project1.databinding.ActivityTermBinding
import com.example.cee_project1.service.AccessibilityTTSHandler
import com.example.cee_project1.service.TTSService
import com.example.cee_project1.adapter.TermVPAdapter
import io.realm.Realm
import io.realm.kotlin.where

class TermActivity : AppCompatActivity() {

    lateinit var binding : ActivityTermBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AccessibilityTTSHandler().handleAccessibility(this)

        val termVPAdapter = TermVPAdapter()
        binding.termViewPager.adapter = termVPAdapter
        whenPageChanged()

        val id = intent.getIntExtra("id", -1)
        if(id != -1) {
            binding.termViewPager.setCurrentItem(id, true)
        }
    }

    private fun whenPageChanged() {
        binding.termViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                Log.d("viewpager listener", "onPageSelected: $position")
                val realm = Realm.getDefaultInstance()

                setTTSContents(position)

                realm.executeTransaction {
                    val term : Term? = realm.where<Term>().containsValue("id", position).findFirst()
                    if(term != null) {
                        term.hasStudied = true
                        prefs.setString("term_save_point", position.toString())
                    }
                }
            }
        })
    }

    private fun setTTSContents(position : Int) {
        val realm = Realm.getDefaultInstance()

        tts.reset()
        val tmpTerm = realm.where<Term>().containsValue("id", position).findFirst()!!
        val name = tmpTerm.name
        val description = tmpTerm.description
        val example = tmpTerm.example
        val metaphor = tmpTerm.metaphor

        tts.addContents(description)
        if(example.isNotEmpty())
            tts.addContents(example)
        if(metaphor.isNotEmpty())
            tts.addContents(metaphor)

        tts.readNotice(name, prefs.getFloat("tts_speed", 1f))

        tts.setOnDoneListener(object : TTSService.OnDoneListener() {
            override fun afterDone() {
                tts.addContents(description)
                if(example.isNotEmpty())
                    tts.addContents(example)
                if(metaphor.isNotEmpty())
                    tts.addContents(metaphor)

                tts.readNotice(name, prefs.getFloat("tts_speed", 1f))
            }
        })
    }

}