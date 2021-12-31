package com.ds.multileaguefootball.data.model

import kotlinx.serialization.Serializable

@Serializable
data class HomeTeamDto(
    val id: Int,
    val name: String
)
