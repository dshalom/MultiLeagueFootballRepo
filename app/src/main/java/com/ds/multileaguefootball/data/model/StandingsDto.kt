package com.ds.multileaguefootball.data.model

import com.ds.multileaguefootball.domain.model.Standings
import kotlinx.serialization.Serializable

@Serializable
data class StandingsDto(
    val competition: CompetitionDto,
    val standings: List<StandingDto>

) {

    fun toDomain(): Standings {
        return Standings(
            competition = competition.toDomain(),
            standings = standings.map { it.toDomain() }
        )
    }
}
