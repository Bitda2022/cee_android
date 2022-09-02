package com.example.cee_project1.data

data class History(
    var events : ArrayList<ArrayList<Event>>,
    var choice : ArrayList<ArrayList<InvestOption>>
) {
    fun getEvents(sequence : Int) : ArrayList<Event> {
        return events[sequence]
    }

    fun getChoice(sequence : Int) : ArrayList<InvestOption> {
        return choice[sequence]
    }

    fun addHistory(sequence : Int, events : ArrayList<Event>, choice : ArrayList<InvestOption>) {
        events[sequence]
    }
}