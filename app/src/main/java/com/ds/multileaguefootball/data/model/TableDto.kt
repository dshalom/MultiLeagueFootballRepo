package com.ds.multileaguefootball.data.model

import com.ds.multileaguefootball.domain.model.TableEntry
import kotlinx.serialization.Serializable

@Serializable
data class TableDto(
    val draw: Int,
    val goalDifference: Int,
    val goalsAgainst: Int,
    val goalsFor: Int,
    val lost: Int,
    val playedGames: Int,
    val points: Int,
    val position: Int,
    val team: TeamDto,
    val won: Int
) {
    fun toDomain(): TableEntry {
        return TableEntry(
            id = team.id,
            name = team.name,
            draw = draw,
            goalDifference = goalDifference,
            goalsAgainst = goalsAgainst,
            goalsFor = goalsFor,
            lost = lost,
            playedGames = playedGames,
            points = points,
            position = position,
            crestUrl = team.crestUrl,
            won = won
        )
    }
}
