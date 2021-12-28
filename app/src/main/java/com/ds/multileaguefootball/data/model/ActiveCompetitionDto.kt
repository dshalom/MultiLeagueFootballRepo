package com.ds.multileaguefootball.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ActiveCompetitionDto(
    val name: String? = null
) {
    fun toDomain(): String? {
        return name
    }
}
