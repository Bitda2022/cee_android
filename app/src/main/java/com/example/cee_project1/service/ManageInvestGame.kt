package com.example.cee_project1.service

import com.example.cee_project1.data.Event
import com.example.cee_project1.data.History
import com.example.cee_project1.data.InvestOption
import com.example.cee_project1.data.Player

private const val PLAYER_INITIAL_MONEY = 100
private const val END_OF_SEQUENCE = 7

class ManageInvestGame {

    private var sequence : Int = 1; // 1주차, 2주차들을 의미
    private val options = ArrayList<InvestOption>() // 투자처들 총 목록
    private val events = ArrayList<Event>() // 사건들 총 목록
    private val history = History(ArrayList(), ArrayList()) // 앞으로의 일정, 과거들 관리
    private val player : Player = Player(PLAYER_INITIAL_MONEY, ArrayList()) // 플레이어

    // 게임을 시작하거나 끝낼때 게임 리셋
    fun resetGame() {
        sequence = 1
        history.events.clear()
        setEventsSequence()
        history.choice.clear()
        player.money = PLAYER_INITIAL_MONEY
        setPlayerOptions()
    }

    // 다음 주차로 갈때 실행하는 함수
    fun goNextSequence() {
        sequence++;
    }

    // 플레이어의 기업 선택지들 이름들을 반환하는 함수
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

    /*
    * 플레이어가 투자할때 실행하는 함수
    * 매개변수로 투자할 옵션의 정확한 이름(String), 투자할 양(Int)을 받음
    * 결과로는 플레이어의 money(금액)이 투자할 양만큼 줄고,
    * 해당하는 option.amount 가 증가되고 option.value 가 option.amount 와 일치됨
    * */
    fun playerInvest(optionName : String, amount : Int) {
        player.invest(optionName, amount)
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
        else " (" + (diff * -1).toString() + " 하락"
        return text
    }

    private fun setPlayerOptions() {
        player.options = options
    }

    private fun getOptionsData() {

    }

    private fun getEventsData() {

    }

    private fun setEventsSequence() {

    }

}