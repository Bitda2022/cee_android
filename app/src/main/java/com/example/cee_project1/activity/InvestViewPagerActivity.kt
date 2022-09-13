package com.example.cee_project1.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.cee_project1.CEEApplication
import com.example.cee_project1.adapter.InvestViewPagerAdapter
import com.example.cee_project1.CEEApplication.Companion.gameManager
import com.example.cee_project1.CEEApplication.Companion.tts
import com.example.cee_project1.data.Term
import com.example.cee_project1.databinding.ActivityInvestViewPagerBinding
import com.example.cee_project1.fragment.InvestMainFragment
import com.example.cee_project1.fragment.InvestSelectFragment
import com.example.cee_project1.service.AccessibilityTTSHandler
import com.example.cee_project1.service.InvestViewPagerAdapter
import io.realm.Realm
import io.realm.kotlin.where

class InvestViewPagerActivity : AppCompatActivity() {
    var rules= mutableListOf<String>("게임소개","단, 7주차 모의투자 경험해보자.","단모경은 가상의 코인 1000코인을 가지고 해당 주차에 해당하는 스토리를\n" +
            "들으며 분산투자하는 모의 투자 게임입니다. ",
        "처음 기업들의 상장가는 모두 다르며 모든 기업에 투자할 수 있습니다. ",
        "화면은 주차별 스토리 -> 투자 -> 장마감결과 로 총 7번 반복됩니다.",
        "투자는 해당 기업을 두번 클릭하면 투자창으로 이동할 수 있으며 금액을 직접 적거나 스와이프를 통해 100단위로\n" +
                "조절 할 수 있습니다. ",
        "",
        "게임필독",
        "1. 투자 상품에는 자동차기업, 문구기업, 제약기업, 적금이 있습니다. ",
        "2. 적금은 단리로 0.1% 주차에 넣은 코인은 마지막 까지 뺄 수 없습니다. ",
        "3. 그 외 투자상품의 경우 각기 다른 상승률과 하락률을 갖고 있습니다. ",
        "4. 모든 코인은 투자할 필요가 없으며 자산은 현금과, 투자한 금액으로 나뉩니다.",
        "5. 주차별 투자가 완료된 시점에서 데이터는 세이브 됩니다.",
        "6. 상장가 이하로 투자는 불가능합니다.",
        "7. 실제 투자와 상이합니다.꼭 참고하시고 즐기세요 "
    )

    lateinit var binding:ActivityInvestViewPagerBinding
    private lateinit var ViewPagerAdapter: InvestViewPagerAdapter

    lateinit var fragmentList:ArrayList<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setGame()
        binding = ActivityInvestViewPagerBinding.inflate(layoutInflater)

        AccessibilityTTSHandler().handleAccessibility(this)
        whenPageChanged()

        var stories=CEEApplication.gameManager.getNowEventsStory()

        var rulesArray=ArrayList<String>()//rules를 ArrayList로 만들기 위한 장치
        rulesArray.addAll(rules)

        fragmentList =ArrayList<Fragment>()
        fragmentList.add(InvestMainFragment(rulesArray))//게임 규칙 설명
        fragmentList.add(InvestMainFragment(stories))//스토리
        fragmentList.add(InvestSelectFragment())//투자
        Log.d("sequence_deadline_get",CEEApplication.prefs.getString("sequence_deadline","-1"))
        if(CEEApplication.prefs.getString("sequence_deadline","-1")!="0"
            ||CEEApplication.prefs.getString("sequence_deadline","-1")!="-1"){
            //2주차 스토리부터 현재주차 스토리까지 꺼내서 배열로 반환해주는 함수 사용
            //그 스토리들을 fragmentList에 add할 것
            var sequence=CEEApplication.gameManager.getNowSequence()
            Log.d("sequence_deadline_get_if","if문 들어옴")
            Log.d("sequence_deadline_get_if_sequence",sequence.toString())
            for(i in 1..sequence){
                Log.d("sequence_deadline_get_for",i.toString()+"\n")
                var eventStory=CEEApplication.gameManager.getEventStory(i)
                fragmentList.add(InvestMainFragment(eventStory))
            }

        }
        initAdapter()
        setContentView(binding.root)
    }

    private fun setGame() {
        CEEApplication.gameManager.resetGame()
        Log.d( "invest_test:현재주차: ",CEEApplication.gameManager.getNowSequence().toString())

    }

    override fun onResume() {
        Log.d("viewpager_lifecycle","investViewPagerActivity onResume 호출")
        if(InvestResultActivity.btnFlag==true){

            if(CEEApplication.gameManager.goNextSequence()){//주차 증가
                //투자해야할 주차 prefs에 set
                CEEApplication.gameManager.saveState(this)
                var sequence=CEEApplication.gameManager.getNowSequence()
                CEEApplication.prefs.setString("sequence_deadline",sequence.toString())
                Log.d("sequence_deadline_set",sequence.toString())

                var stories=CEEApplication.gameManager.getNowEventsStory()
                fragmentList.add(fragmentList.size-1,InvestMainFragment(stories)) //이번 주차 스터리 추가
            }
            else{//7주차 -> 다음주차로 넘어가려고 할 때
                val intent = Intent(this, InvestFinalActivity::class.java)
                startActivity(intent)
            }


            Log.d( "invest_test:현재주차",CEEApplication.gameManager.getNowSequence().toString())
//            var sequence=CEEApplication.gameManager.getNowSequence()
            initAdapter()
            InvestResultActivity.btnFlag=false
            binding.vpSample.currentItem=fragmentList.size-2

        }

        super.onResume()
    }
    private fun initAdapter() {
        //ViewPagerAdapter 초기화
        ViewPagerAdapter = InvestViewPagerAdapter(this)
        ViewPagerAdapter.fragments.addAll(fragmentList)

        //ViewPager2와 Adapter 연동
        binding.vpSample.adapter = ViewPagerAdapter

    }

    private fun whenPageChanged() {
        binding.vpSample.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                Log.d("viewpager listener", "onPageSelected: $position")
                tts.reset()
                if(position != fragmentList.size - 1) {
                    if(position == 0) {
                        val ruleList = ArrayList<String>()
                        ruleList.addAll(rules)
                        for(rule in ruleList) {
                            tts.addContents(rule)
                        }
                    } else {
                        for(event in gameManager.getEventStory(position - 1))
                            tts.addContents(event)
                    }
                }
            }
        })
    }

}