package com.mertoenjosh.triviaquest.services

import com.mertoenjosh.triviaquest.models.Question
import retrofit2.Call
import retrofit2.http.*

interface QuestionService {
    @GET("questions")
    fun getQuestions(
        // TODO use QueryMap
        @QueryMap filters: HashMap<String, Any>
//        @Query("categories") categories: String,
//        @Query("limit") limit: Int,
//        @Query("difficulty") difficulty: String,
    ): Call<ArrayList<Question>>

    @GET("questions/{id}")
    fun getQuestion(
      @Path("id") id: Int
    ): Call<Question>

    @POST("questions")
    fun createQuestion(
        @Body question: Question
    ): Call<Question>

}