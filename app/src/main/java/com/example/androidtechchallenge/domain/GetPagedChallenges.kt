package com.example.androidtechchallenge.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.androidtechchallenge.data.ChallengesPagingSource
import com.example.androidtechchallenge.data.ChallengesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.map

class GetPagedChallenges(
    private val challengesRepository: ChallengesRepository
) {

    companion object {
        private const val INITIAL_PAGE_KEY = 0
        private const val PAGE_SIZE = 20
    }

    operator fun invoke(viewModelScope: CoroutineScope) =
        Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            initialKey = INITIAL_PAGE_KEY,
            pagingSourceFactory = { ChallengesPagingSource(challengesRepository) }
        ).flow.map { pagingData ->
            pagingData.map { itemDto ->
                itemDto.toChallengeItem()
            }
        }.cachedIn(viewModelScope)
}