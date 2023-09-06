package com.example.androidtechchallenge.data

import com.example.androidtechchallenge.model.ChallengeResponse
import com.example.androidtechchallenge.model.CompletedChallengesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ChallengesService {
    @GET("code-challenges/completed?page={page}")
    fun getCompletedChallenges(@Path("page") page: Int): Response<CompletedChallengesResponse>

    @GET("code-challenges/{challenge}")
    fun getCodeChallenge(@Path("challenge") idChallenge: String): Response<ChallengeResponse>
}