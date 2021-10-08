package com.ds.multileaguefootball.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CurrentSeasonDto(
    val currentMatchday: Int?,
    val endDate: String,
    val id: Int,
    val startDate: String,
)
