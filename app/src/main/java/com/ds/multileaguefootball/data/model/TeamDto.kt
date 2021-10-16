package com.ds.multileaguefootball.data.model

import com.ds.multileaguefootball.domain.model.Team
import kotlinx.serialization.Serializable

@Serializable
data class TeamDto(
    val crestUrl: String?,
    val id: Int,
    val name: String
) {
    fun toDomain(): Team {
        return Team(crestUrl = crestUrl, id = id, name = name)
    }
}
