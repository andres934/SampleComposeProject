package com.example.androidtechchallenge

import com.example.androidtechchallenge.data.mocks.challengeDetailMockItem
import com.example.androidtechchallenge.data.mocks.completedChallengesItemMock
import com.example.androidtechchallenge.data.model.CompletedChallengeDto
import com.example.androidtechchallenge.data.model.UserInfoDto
import com.example.androidtechchallenge.domain.models.ChallengeDetails
import com.example.androidtechchallenge.domain.models.ChallengeItem
import com.example.androidtechchallenge.domain.models.CompletedChallenges
import com.example.androidtechchallenge.domain.models.Rank
import com.example.androidtechchallenge.domain.models.UserInfo
import com.example.androidtechchallenge.domain.toChallengeDetails
import com.example.androidtechchallenge.domain.toCompletedChallenges
import com.example.androidtechchallenge.util.getFormattedDateString
import org.junit.Test

class DtoMapperTest {

    private val expectedMappedChallengeDetails = ChallengeDetails(
        id = challengeDetailMockItem.id,
        name = challengeDetailMockItem.name,
        slug = challengeDetailMockItem.slug,
        url = challengeDetailMockItem.url,
        category = challengeDetailMockItem.category,
        description = challengeDetailMockItem.description,
        tags = challengeDetailMockItem.tags,
        languages = challengeDetailMockItem.languages,
        rank = Rank(
            id = challengeDetailMockItem.rank!!.id,
            name = challengeDetailMockItem.rank!!.name,
            color = challengeDetailMockItem.rank!!.color
        ),
        createdBy = UserInfo(
            username = challengeDetailMockItem.createdBy!!.username,
            url = challengeDetailMockItem.createdBy!!.url
        ),
        approvedBy = UserInfo(
            username = challengeDetailMockItem.approvedBy!!.username,
            url = challengeDetailMockItem.approvedBy!!.url
        ),
        totalAttempts = challengeDetailMockItem.totalAttempts,
        totalCompleted = challengeDetailMockItem.totalCompleted,
        totalStars = challengeDetailMockItem.totalStars,
        voteScore = challengeDetailMockItem.voteScore,
        publishedAt = challengeDetailMockItem.publishedAt?.getFormattedDateString() ?: "",
        approvedAt = challengeDetailMockItem.approvedAt?.getFormattedDateString() ?: "",
    )

    private val expectedMappedCompletedChallenges =
        CompletedChallenges(
            totalPages = completedChallengesItemMock.totalPages,
            totalItems = completedChallengesItemMock.totalItems,
            data = listOf(
                ChallengeItem(
                    id = completedChallengesItemMock.data[0].id,
                    name = completedChallengesItemMock.data[0].name,
                    slug = completedChallengesItemMock.data[0].slug,
                    completedAt = completedChallengesItemMock.data[0].completedAt.getFormattedDateString(),
                    completedLanguages = completedChallengesItemMock.data[0].completedLanguages,
                ),
                ChallengeItem(
                    id = completedChallengesItemMock.data[1].id,
                    name = completedChallengesItemMock.data[1].name,
                    slug = completedChallengesItemMock.data[1].slug,
                    completedAt = completedChallengesItemMock.data[1].completedAt.getFormattedDateString(),
                    completedLanguages = completedChallengesItemMock.data[1].completedLanguages,
                )
            )
        )

    @Test
    fun `on CompletedChallengesResponse parsed should return correct CompletedChallenges`() {
        // Given expectedMappedCompletedChallenge

        // When
        val mappedCompletedChallenges = completedChallengesItemMock.toCompletedChallenges()

        // Then
        assert(mappedCompletedChallenges == expectedMappedCompletedChallenges)
    }

    @Test
    fun `on ChallengeResponse mapped should return correct ChallengeDetails`() {
        // Given @expectedMappedChallengeDetails

        // When
        val mappedChallengeDetails = challengeDetailMockItem.toChallengeDetails()

        // Then
        assert(mappedChallengeDetails == expectedMappedChallengeDetails)
    }

    @Test
    fun `on CompletedChallengesResponse parsed with any empty value except slug should avoid it`() {
        // Given
        val expectedChallengeResponse = completedChallengesItemMock.copy(
            data = listOf(
                CompletedChallengeDto(
                    id = "514b92a657cdc65150000006",
                    name = "",
                    slug = "multiples-of-3-and-5",
                    completedAt = "2017-04-06T16:32:09Z",
                    completedLanguages = listOf(
                        "javascript",
                        "coffeescript",
                        "ruby",
                        "C++",
                        "kotlin",
                        "typescript",
                    )
                ),
                CompletedChallengeDto(
                    id = "514b92a657cdc65150000126",
                    name = "Predict your age!",
                    slug = "predict-your-age!",
                    completedAt = "2018-04-06T16:32:09Z",
                    completedLanguages = listOf(
                        "kotlin",
                        "typescript",
                        "ruby",
                        "php",
                        "java"
                    )
                )
            )
        )
        val expectedMappedChallenges = expectedMappedCompletedChallenges.copy(
            data = listOf(
                ChallengeItem(
                    id = completedChallengesItemMock.data[1].id,
                    name = completedChallengesItemMock.data[1].name,
                    slug = completedChallengesItemMock.data[1].slug,
                    completedAt = completedChallengesItemMock.data[1].completedAt.getFormattedDateString(),
                    completedLanguages = completedChallengesItemMock.data[1].completedLanguages,
                )
            )
        )

        // When
        val mappedCompletedChallenges = expectedChallengeResponse.toCompletedChallenges()

        // Then
        assert(mappedCompletedChallenges == expectedMappedChallenges)
    }

    @Test
    fun `on ChallengeResponse mapped with empty username in UserInfo should use unknown as default`() {
        // Given
        val expectedChallengeResponse = challengeDetailMockItem.copy(
            createdBy = UserInfoDto(
                username = "",
                url = "thisismyurl.com"
            )
        )
        val expectedChallengeDetails = expectedMappedChallengeDetails.copy(
            createdBy = UserInfo(
                username = "Unknown",
                url = "thisismyurl.com"
            )
        )

        // When
        val mappedChallengeDetails = expectedChallengeResponse.toChallengeDetails()

        // Then
        assert(mappedChallengeDetails == expectedChallengeDetails)
    }

    @Test
    fun `on ChallengeResponse mapped with null UserInfo should use username unknown and empty url as default`() {
        // Given
        val expectedChallengeResponse = challengeDetailMockItem.copy(
            createdBy = null
        )
        val expectedChallengeDetails = expectedMappedChallengeDetails.copy(
            createdBy = UserInfo(
                username = "Unknown",
                url = ""
            )
        )

        // When
        val mappedChallengeDetails = expectedChallengeResponse.toChallengeDetails()

        // Then
        assert(mappedChallengeDetails == expectedChallengeDetails)
    }
}
