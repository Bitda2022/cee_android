package com.example.cee_project1.data

import java.io.Serializable
import java.lang.Exception

data class Player(
    var money : Int = 0,
    var options : ArrayList<InvestOption>
) : Serializable {
    fun invest(optionName : String, amount : Int) : Boolean {
        val option = findOption(optionName)
        if(option.name == "적금" && amount < 0) {
            return false
        }
        return if(option.price > amount + option.amount)
            if(amount + option.amount != 0)
                false
            else {
                option.amount = 0
                option.value = 0.0
                money -= amount
                true
            }
        else if(money < amount)
            false
        else {
            option.amount += amount
            option.value = option.amount.toDouble()
            money -= amount
            true
        }
    }

    fun setAmount2Value() {
        for(option in options) {
            if(option.name != "적금") {
                option.amount = option.value.toInt()
            }
        }
    }

    fun getTotalMoney() : Int {
        var sum = 0.0
        for(option in options) {
            sum += option.value
        }
        return sum.toInt() + money
    }

    fun findOption(optionName : String) : InvestOption {
        for(option in options) {
            if(option.name == optionName)
                return option
        }
        throw Exception("there is no option whose name is $optionName!")
    }
}