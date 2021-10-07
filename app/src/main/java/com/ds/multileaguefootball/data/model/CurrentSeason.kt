package com.ds.multileaguefootball.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CurrentSeason(
    val currentMatchday: Int?,
    val endDate: String,
    val id: Int,
    val startDate: String,
)
