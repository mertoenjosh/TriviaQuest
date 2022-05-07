package com.mertoenjosh.triviaquest.utils

import com.mertoenjosh.triviaquest.models.Question

object Constants {
    var QUESTIONS: ArrayList<Question> = ArrayList()
    var PLAYER_NAME:String? = null
    const val LIMIT = 5
    const val DEFAULT_CATEGORY: String = "general_knowledge"

    const val EXTRA_CATEGORY = "EXTRA_CATEGORY"
    const val EXTRA_DIFFICULTY = "EXTRA_DIFFICULTY"
    const val EXTRA_CORRECT_ANSWERS = "EXTRA_CORRECT_ANSWERS"
    const val TIMER_DURATION = "timeDuration"
    const val TIMER_EASY = 20
    const val TIMER_MEDIUM = 15
    const val TIMER_HARD = 10

    const val EASY = "easy"
    const val MEDIUM = "medium"
    const val HARD = "hard"

    const val BASE_URL = "https://the-trivia-api.com/questions?"
    const val URL_END_POINT: String = "https://the-trivia-api.com/questions?categories=general_knowledge&limit=2"
}