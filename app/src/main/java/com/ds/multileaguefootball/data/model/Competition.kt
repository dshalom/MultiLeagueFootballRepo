package com.ds.multileaguefootball.data.model
import kotlinx.serialization.Serializable

@Serializable
data class Competition(
    val area: Area,
    val currentSeason: CurrentSeason?,
    val emblemUrl: String?,
    val id: Int,
    val lastUpdated: String,
    val name: String,
    val numberOfAvailableSeasons: Int,
    val plan: String
)
