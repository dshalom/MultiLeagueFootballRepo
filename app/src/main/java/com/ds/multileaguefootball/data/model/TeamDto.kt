package com.ds.multileaguefootball.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TeamDto(
    val crestUrl: String?,
    val id: Int,
    val name: String
)
