package com.example.cee_project1.data

data class History(
    var events : ArrayList<ArrayList<Event>>,
    var choice : ArrayList<ArrayList<InvestOption>>
) {
    fun getNowEvents(sequence : Int) : ArrayList<Event> {
        return events[sequence]
    }
}