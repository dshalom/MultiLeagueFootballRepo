package com.ds.multileaguefootball.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ScoreDto(
    val fullTime: FullTime?
)

@Serializable
data class FullTime(
    val awayTeam: Int?,
    val homeTeam: Int?
)
