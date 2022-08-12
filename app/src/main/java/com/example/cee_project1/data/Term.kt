package com.example.cee_project1.data

data class Term(
    var id : Int,
    var type : String, // 경제기초, 금융기반, 주식심화 구분
    var name : String, // 용어
    var description : String, // 용어 설명
    var metaphor : String, // 비유
    var example : String, // 실생활 예시
    var hasStudied : Boolean, // 공부 완료/미완료
    var quizs : ArrayList<Quiz> // 퀴즈들
)
