package com.example.androidtechchallenge.data

import android.accounts.NetworkErrorException
import com.example.androidtechchallenge.data.model.ChallengeResponse
import com.example.androidtechchallenge.data.model.CompletedChallengesResponse
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class ChallengesRepository {

    suspend fun getCompletedChallenges(page: Int = 0) =
        suspendCancellableCoroutine { continuation ->
            val call = Api.retrofitService.getCompletedChallenges(page)
            call.enqueue(object : Callback<CompletedChallengesResponse> {
                override fun onResponse(
                    call: Call<CompletedChallengesResponse>,
                    response: Response<CompletedChallengesResponse>
                ) {
                    if (response.isSuccessful) {
                        continuation.resume(response.body())
                    } else {
                        val error =
                            NetworkErrorException(
                                response.errorBody()?.string() ?: "Error with call ${
                                    call.request().url()
                                }"
                            )
                        continuation.resumeWithException(error)
                    }
                }

                override fun onFailure(call: Call<CompletedChallengesResponse>, t: Throwable) {
                    val error = NetworkErrorException(
                        t.message ?: "Error with call ${
                            call.request().url()
                        }"
                    )
                    continuation.resumeWithException(error)
                }

            })
        }

    suspend fun getChallengeDetails(idChallenge: String) =
        suspendCancellableCoroutine { continuation ->
            val call = Api.retrofitService.getCodeChallenge(idChallenge)
            call.enqueue(object : Callback<ChallengeResponse> {
                override fun onResponse(
                    call: Call<ChallengeResponse>,
                    response: Response<ChallengeResponse>
                ) {
                    if (response.isSuccessful) {
                        continuation.resume(response.body())
                        response.body()
                    } else {
                        val error =
                            NetworkErrorException(
                                response.message() + "With call ${call.request().url()}" ?: "Error with call ${call.request().url()}"
                            )
                        continuation.resumeWithException(error)
                    }
                }

                override fun onFailure(call: Call<ChallengeResponse>, t: Throwable) {
                    val error =
                        NetworkErrorException(t.message + "With call ${call.request().url()}" ?: "Error with call ${call.request().url()}")
                    continuation.resumeWithException(error)
                }
            })
        }
}
