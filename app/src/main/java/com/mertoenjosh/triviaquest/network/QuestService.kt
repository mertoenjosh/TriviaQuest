package com.mertoenjosh.triviaquest.network

import com.mertoenjosh.triviaquest.models.Question
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface QuestService {

    @GET("questions")
    fun getQuiz(
        @Query("categories") categories: String,
        @Query("limit") limit: Int,
        @Query("difficulty") difficulty: String,
    ): Call<ArrayList<Question>>
}