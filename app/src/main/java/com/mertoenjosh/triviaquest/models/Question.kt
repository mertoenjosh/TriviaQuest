package com.mertoenjosh.triviaquest.models

data class Question(
    val category: String,
    val id: String,
    val correctAnswer: String,
    val incorrectAnswers: ArrayList<String>,
    val question: String,
    val tags: ArrayList<String>,
    val type: String
)

//{
//    "category": "General Knowledge",
//    "id": "622a1c377cc59eab6f950427",
//    "correctAnswer": "The Hay",
//    "incorrectAnswers": [
//    "The Manger",
//    "A Donkey",
//    "The Stars"
//    ],
//    "question": "In the Christmas carol 'Away in a Manger', what was the little Lord Jesus asleep on?",
//    "tags": [],
//    "type": "Multiple Choice"
//}