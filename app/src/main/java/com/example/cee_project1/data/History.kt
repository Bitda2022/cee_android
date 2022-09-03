package com.example.cee_project1.data

import java.io.Serializable

data class History(
    var events : ArrayList<ArrayList<Event>>,
    var choices : ArrayList<ArrayList<InvestOption>>
) : Serializable {
    fun getEvents(sequence : Int) : ArrayList<Event> {
        return events[sequence]
    }

    fun addEvent(sequence : Int, event : ArrayList<Event>) {
        events.add(sequence, event)
    }

    fun getChoice(sequence : Int) : ArrayList<InvestOption> {
        return choices[sequence]
    }

    fun addHistory(sequence : Int, choice : ArrayList<InvestOption>) {
        val tmp = ArrayList<InvestOption>()
        for(option in choice) {
            val tmpOp = InvestOption(option.name, option.amount, option.value, option.price, option.maxPoint, option.minPoint)
            tmp.add(tmpOp)
        }
        choices.add(sequence, tmp)
    }
}