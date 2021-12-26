package com.ds.multileaguefootball.data.model

import com.ds.multileaguefootball.domain.model.Team
import kotlinx.serialization.Serializable

@Serializable
data class TeamDto(
    val activeCompetitionDtos: List<ActiveCompetitionDto>? = null,
    val crestUrl: String,
    val id: Int,
    val name: String,
    val shortName: String? = null,
    val squad: List<SquadDto>? = null,
    val venue: String? = null,
    val website: String? = null
) {
    fun toDomain(): Team {
        return Team(
            id = id,
            name = name,
            shortName = shortName,
            squadMembers = squad?.map { it.toDomain() },
            venue = venue,
            website = website,
            activeCompetitions = activeCompetitionDtos?.map { it.toDomain() }
        )
    }
}
