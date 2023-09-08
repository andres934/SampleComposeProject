package com.example.androidtechchallenge.data

import com.example.androidtechchallenge.model.ChallengeResponse
import com.example.androidtechchallenge.model.CompletedChallenge
import com.example.androidtechchallenge.model.Rank
import com.example.androidtechchallenge.model.UserInfo

val challengesMockItems = listOf(
    CompletedChallenge(
        id = "514b92a657cdc65150000006",
        name = "Multiples of 3 and 5",
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
    CompletedChallenge(
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

val challengeDetailMockItem = ChallengeResponse(
    id = "5277c8a221e209d3f6000b56",
    name = "Pair of gloves",
    slug = "pair-of-gloves",
    url = "https://www.codewars.com/kata/58235a167a8cb37e1a0000db",
    category = "algorithms",
    description = "Winter is coming, you must prepare your ski holidays. The objective of this kata is to determine the number of pair of gloves you can constitute from the gloves you have in your drawer.\n" +
            "\n" +
            "Given an array describing the color of each glove, return the number of pairs you can constitute, assuming that only gloves of the same color can form pairs",
    tags = listOf("Algorithms", "Validation", "Logic", "Utilities"),
    languages = listOf("javascript", "coffeescript"),
    rank = Rank(
        id = -4,
        name = "4 kyu",
        color = "blue"
    ),
    createdBy = UserInfo(
        username = "xDranik",
        url = "http://www.codewars.com/users/xDranik"
    ),
    approvedBy = UserInfo(
        username = "xDranik",
        url = "http://www.codewars.com/users/xDranik"
    ),
    totalAttempts = 4911,
    totalCompleted = 919,
    totalStars = 12,
    voteScore = 512,
    publishedAt = "2013-11-05T00:07:31Z",
    approvedAt = "2013-12-20T14:53:06Z"
)