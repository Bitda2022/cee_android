package com.example.cee_project1.data

import io.realm.RealmObject

open class Quiz(
    var id : Int = -1,
    var term : String = "", // 개념
    var content : String = "", // 질문
    var answer : Boolean = false, // O, X
    var commentary : String = "", // 해설
    var wrong : Int = -1 // 틀린 횟수
) : RealmObject()
