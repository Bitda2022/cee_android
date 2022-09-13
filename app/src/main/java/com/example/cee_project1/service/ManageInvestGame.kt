package com.example.cee_project1.service

import android.content.Context
import android.util.Log
import com.example.cee_project1.CEEApplication.Companion.gameManager
import com.example.cee_project1.data.Event
import com.example.cee_project1.data.History
import com.example.cee_project1.data.InvestOption
import com.example.cee_project1.data.Player
import java.io.ObjectOutputStream
import java.io.Serializable

/*
게임 시작 후 대략적인 순서 가이드
resetGame
getNowEventStory -> 투자 스토리 보여줌
getNowSequence -> 몇번째 주차인지 보여줌
getPlayerMoney -> 보유 코인 보여줌
playerInvest -> 플레이어가 각 기업에 투자함
applyEvents -> 플레이어 투자에 스토리 반영하여 결과 도출
getResults -> 각 기업들의 변화량을 문자로 보여줌
goNextSequence -> 다음 주차로 이동
 */

private const val PLAYER_INITIAL_MONEY = 100
private const val END_OF_SEQUENCE = 6

class ManageInvestGame : Serializable {

    private var sequence : Int = 0; // 1주차, 2주차들을 의미
    private val history = History(ArrayList(), ArrayList()) // 앞으로의 일정, 과거들 관리
    private val player : Player = Player(PLAYER_INITIAL_MONEY, ArrayList()) // 플레이어

    /* 게임을 시작하거나 끝낼때 게임 리셋
    * sequence 1로 초기화 (첫 주차임을 의미)
    * history.choices 초기화
    * 플레이어 money 초기화
    * */
    fun resetGame() {
        sequence = 0
        history.choices.clear()
        player.money = PLAYER_INITIAL_MONEY
    }

    /*
    * 다음 라운드로 갈 때 실행하는 함수
    * 현재 옵션들의 결과를 히스토리에 넣음 (applyEvents 함수 실행 후 실행 요망)
    * sequence 를 증가시켜 다음 주차임을 확정
    * 플레이어 돈 자동 회수
    * */
    fun goNextSequence() : Boolean {
        history.addHistory(sequence, player.options)
        player.setAmount2Value()
        return if(sequence == END_OF_SEQUENCE) {
            false
        } else {
            sequence++
            true
        }
    }

    // 플레이어의 기업 선택지들 이름을 반환하는 함수
    fun getPlayersOptionsName() : ArrayList<String> {
        val names = ArrayList<String>()
        for(option in player.options) names.add(option.name)
        return names
    }

    /*
    * 현재 주차의 사건들의 스토리를 반환하는 함수
    * 반환값은 이벤트들의 스토리들이 담긴 ArrayList
    * 만약 스토리가 같은 이벤트가 여러개일 경우는 중복된 스토리는 제거 후 반환
    * */
    fun getNowEventsStory() : ArrayList<String> {
        return getEventStory(sequence)
    }

    fun getEventStory(sequence : Int) : ArrayList<String> {
        val events = history.getEvents(sequence)
        val stories = ArrayList<String>()
        for(event in events) {
            if(!stories.contains(event.story))
                stories.add(event.story)
        }
        return stories
    }

    // 현재 몇주차인지 반환해주는 함수
    fun getNowSequence() : Int {
        return sequence
    }

    // 플레이어의 남은 돈을 반환해주는 함수
    fun getPlayerMoney() : Int {
        return player.money
    }

    // 플레이어 총 자본을 반환해주는 함수
    fun getPlayerTotalMoney() : Int {
        return player.getTotalMoney()
    }

    /*
    * 플레이어가 투자할때 실행하는 함수
    * 매개변수로 투자할 옵션의 정확한 이름(String), 투자할 양(Int)을 받음
    * 결과로는 플레이어의 money(금액)이 투자할 양만큼 줄고,
    * 해당하는 option.amount 가 증가되고 option.value 가 option.amount 와 일치됨
    * */
    fun playerInvest(optionName : String, amount : Int) : Boolean {
        return player.invest(optionName, amount)
    }


    /*
    * 최종 투자 결정 이후 해당 주차의 사건들을 기업에 적용해주는 함수
    * */
    fun applyEvents() {
        val events = history.getEvents(sequence)
        for(event in events) {
            event.apply()
        }
    }

    /*
    * 원하는 옵션의 amount 값을 가져오는 메소드
    * 매개변수로 해당 옵션의 이름을 받으면 Int 타입의 amount 값을 리턴해줌
    * */
    fun getOptionAmount(optionName : String) : Int {
        val option = player.findOption(optionName)
        return option.amount
    }

    fun getOptionPrice(optionName : String) : Int {
        val option = player.findOption(optionName)
        return option.price
    }

    /*
    * 현재 주차의 투자 결과를 반환하는 함수
    * 매개변수로 투자 결과를 반환받고 싶은 옵션의 정확한 이름(String)을 받음
    * 해당하는 기업 옵션의 변화의 설명이 String 형태로 반환됨
    * */
    fun getResults(optionName : String) : String {
        val option = player.findOption(optionName)
        var text = ""
        text += option.amount.toString() + "->" + option.value.toInt().toString()
        val diff = option.value.toInt() - option.amount
        text += if(diff > 0) " ($diff 상승)"
        else " (" + (diff * -1).toString() + " 하락)"
        return text
    }

    fun setPlayerOptions(options : ArrayList<InvestOption>) {
        player.options = options
    }

    fun setEventsSequence(events : ArrayList<ArrayList<Event>>) {
        history.events = events
        Log.d("tts", "setEventsSequence: ${events.size}")
    }

    fun saveState(context : Context) {
        val fos = context.openFileOutput("gameManager", Context.MODE_PRIVATE)
        val oos = ObjectOutputStream(fos)
        oos.writeObject(gameManager)
        oos.close()
        fos.close()
        Log.d("invest", "handleMessage: complete")
    }

    fun test() {
        for(option in player.options) {
            Log.d("invest", "test: " + option.name)
        }
        for(event in history.events.first()) {
            Log.d("invest", "test: " + event.story)
        }
    }

}