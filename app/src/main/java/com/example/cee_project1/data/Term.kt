package com.example.cee_project1.data

data class Term(
    var id:Int,
    var type:String,
    var term:String,
    var discription:String,
    var metaphor:String?,
    var example:String,
    var hasStudied:Boolean,
    var quiz:Quiz?
)
