package com.ds.multileaguefootball.data.model

data class SeasonDto(
    val currentMatchday: Int,
    val endDate: String,
    val id: Int,
    val startDate: String,
    val winner: Any
)
