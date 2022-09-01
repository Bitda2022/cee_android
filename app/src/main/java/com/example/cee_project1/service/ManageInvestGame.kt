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

    fun resetGame() {
        sequence = 1;
        history.events.clear()
        setEventsSequence()
        history.choice.clear()
        player.money = PLAYER_INITIAL_MONEY
        setPlayerOptions()
    }

    fun goNextSequence() {
        sequence++;
        for(option in player.options) {
            if(option.name != "적금") {
                player.money += option.value
                option.amount = 0
                option.value = 0
            }
        }
    }

    fun getNowEvents() : ArrayList<Event> {
        return history.getNowEvents(sequence)
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