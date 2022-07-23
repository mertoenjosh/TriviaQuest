package com.mertoenjosh.triviaquest.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.mertoenjosh.triviaquest.models.Question

object Constants {
    var QUESTIONS: ArrayList<Question> = ArrayList()
    var PLAYER_NAME:String? = null
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

    const val BASE_URL = "https://the-trivia-api.com/api/"
    const val SAPLE_URL_END_POINT: String = "https://the-trivia-api.com/questions?categories=general_knowledge&limit=2"

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true

                else -> false
            }
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo

            return networkInfo != null && networkInfo.isConnectedOrConnecting
        }
    }
}