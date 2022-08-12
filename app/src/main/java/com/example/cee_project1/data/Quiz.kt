package com.example.cee_project1.data

import io.realm.RealmObject

data class Quiz(
    var term : Term, // 개념
    var content : String, // 질문
    var answer : Boolean, // O, X
    var wrong : Int // 틀린 횟수
) : RealmObject()
