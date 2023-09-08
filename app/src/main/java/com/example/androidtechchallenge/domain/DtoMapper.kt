package com.example.androidtechchallenge.domain

import com.example.androidtechchallenge.data.model.ChallengeResponse
import com.example.androidtechchallenge.data.model.CompletedChallengeDto
import com.example.androidtechchallenge.data.model.CompletedChallengesResponse
import com.example.androidtechchallenge.data.model.RankDto
import com.example.androidtechchallenge.data.model.UserInfoDto
import com.example.androidtechchallenge.domain.models.ChallengeDetails
import com.example.androidtechchallenge.domain.models.ChallengeItem
import com.example.androidtechchallenge.domain.models.CompletedChallenges
import com.example.androidtechchallenge.domain.models.Rank
import com.example.androidtechchallenge.domain.models.UserInfo
import com.example.androidtechchallenge.util.getFormattedDateString

fun CompletedChallengesResponse.toCompletedChallenges() =
    CompletedChallenges(
        totalPages = totalPages,
        totalItems = totalItems,
        data = data.toChallengeItems()
    )

fun ChallengeResponse.toChallengeDetails() =
    ChallengeDetails(
        id = id,
        name = name,
        slug = slug,
        url = url,
        category = category,
        description = description,
        tags = tags,
        languages = languages,
        rank = rank?.toRank(),
        createdBy = createdBy.toUserInfo(),
        approvedBy = approvedBy.toUserInfo(),
        totalAttempts = totalAttempts,
        totalCompleted = totalCompleted,
        totalStars = totalStars,
        voteScore = voteScore,
        publishedAt = publishedAt?.getFormattedDateString() ?: "",
        approvedAt = approvedAt?.getFormattedDateString() ?: "",
    )

private fun List<CompletedChallengeDto>.toChallengeItems() =
    filter {
        it.id.isNotEmpty() &&
                it.name.isNotEmpty() &&
                it.completedAt.isNotEmpty() &&
                it.completedLanguages.isNotEmpty()
    }.map {
        ChallengeItem(
            id = it.id,
            name = it.name,
            slug = it.slug,
            completedAt = it.completedAt.getFormattedDateString(),
            completedLanguages = it.completedLanguages,
        )
    }

private fun RankDto.toRank() =
    Rank(
        id = id,
        name = name,
        color = color
    )

private fun UserInfoDto?.toUserInfo() =
    this?.run {
        UserInfo(
            username = username.ifEmpty { "Unknown" },
            url = url,
        )
    } ?: UserInfo(
        username = "Unknown",
        url = "",
    )

