package com.example.cee_project1.data

import java.io.Serializable

data class InvestOption(
    var name : String = "",
    var amount : Int = 0,
    var value : Double = 0.0,
    var price : Int = 0,
    var maxPoint : Int = 0,
    var minPoint : Int = 0
) : Serializable
