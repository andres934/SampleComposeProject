package com.example.androidtechchallenge.data

import com.example.androidtechchallenge.model.CompletedChallenge

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