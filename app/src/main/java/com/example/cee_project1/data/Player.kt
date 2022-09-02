package com.example.cee_project1.data

import java.lang.Exception

data class Player(
    var money : Int = 0,
    var options : ArrayList<InvestOption>
) {
    fun invest(optionName : String, amount : Int) {
        val option = findOption(optionName)
        option.amount += amount
        option.value = option.amount.toDouble()
        money -= amount
    }

    fun retrieveMoney() {
        var tmp = 0.0
        for(option in options) {
            if(option.name != "적금") {
                tmp += option.value
                option.value = 0.0
                option.amount = 0
            }
        }
        money = tmp.toInt()
    }

    fun findOption(optionName : String) : InvestOption {
        for(option in options) {
            if(option.name == optionName)
                return option
        }
        throw Exception("there is no option whose name is $optionName!")
    }
}
