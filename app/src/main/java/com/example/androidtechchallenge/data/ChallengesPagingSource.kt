package com.example.androidtechchallenge.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.androidtechchallenge.data.model.CompletedChallengeDto

class ChallengesPagingSource(
    private val repository: ChallengesRepository
): PagingSource<Int, CompletedChallengeDto>() {
    override fun getRefreshKey(state: PagingState<Int, CompletedChallengeDto>): Int? =
        state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CompletedChallengeDto> {
        val currentPage = params.key ?: 0
        val challenges = repository.getCompletedChallenges(currentPage)

        val nextKey = when (challenges?.totalPages) {
            currentPage -> null
            else -> currentPage + 1
        }

        return LoadResult.Page(
            data = challenges?.data ?: emptyList(),
            prevKey = null,
            nextKey = nextKey
        )
    }
}