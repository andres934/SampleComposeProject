package com.example.androidtechchallenge.data

import com.example.androidtechchallenge.model.ChallengeResponse
import com.example.androidtechchallenge.model.CompletedChallengesResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ChallengesService {
    @GET("code-challenges/completed")
    fun getCompletedChallenges(@Query("page") page: Int): Call<CompletedChallengesResponse>

    @GET("code-challenges/{challenge}")
    fun getCodeChallenge(@Path("challenge") idChallenge: String): Response<ChallengeResponse>
}