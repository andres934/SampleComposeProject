package com.example.androidtechchallenge.data

import android.accounts.NetworkErrorException
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class ChallengesRepository {

    suspend fun getCompletedChallenges(page: Int = 0) =
        suspendCancellableCoroutine { continuation ->
            val response = Api.retrofitService.getCompletedChallenges(page)
            if (response.isSuccessful) {
                continuation.resume(response.body())
                response.body()
            } else {
                val error =
                    NetworkErrorException(response.errorBody()?.string() ?: "Error with call")
                continuation.resumeWithException(error)
            }
        }

    suspend fun getCompletedChallenges(idChallenge: String) =
        suspendCancellableCoroutine { continuation ->
            val response = Api.retrofitService.getCodeChallenge(idChallenge)
            if (response.isSuccessful) {
                continuation.resume(response.body())
                response.body()
            } else {
                val error =
                    NetworkErrorException(response.errorBody()?.string() ?: "Error with call")
                continuation.resumeWithException(error)
            }
        }
}
