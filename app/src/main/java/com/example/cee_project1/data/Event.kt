package com.example.cee_project1.data

import kotlin.random.Random

data class Event(
    var target : InvestOption,
    var story : String = "",
    var degree : Degree
) {
    enum class Degree(val minPercent : Int, val maxPercent : Int, val precisePercent : Int) {
        BIG_RAISE(15, 30, 0),
        MIDDLE_RAISE(5, 15, 0),
        SMALL_RAISE(1, 5, 0),
        BIG_DECLINE(-30, -15, 0),
        MIDDLE_DECLINE(-15, -5, 0),
        SMALL_DECLINE(-1, -5, 0),
        INTEREST_RATE(0, 0, 1)
    }

    fun apply() {
        target.value = target.amount * getRate()
    }

    private fun getRate() : Double {
        return if (degree == Degree.INTEREST_RATE)
            ((degree.precisePercent + 100) / 100).toDouble()
        else
            (100 + Random(System.currentTimeMillis())
                .nextDouble(degree.minPercent.toDouble(), degree.maxPercent.toDouble())) / 100
    }
}
