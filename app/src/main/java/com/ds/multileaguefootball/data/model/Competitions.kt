package com.ds.multileaguefootball.data.model
import kotlinx.serialization.Serializable

@Serializable
data class Competitions(
    val competitions: List<Competition>,
    val count: Int,
    val filters: Filters
)
