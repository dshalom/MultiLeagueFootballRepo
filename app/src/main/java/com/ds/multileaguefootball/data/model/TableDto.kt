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
            draw = draw.toString(),
            goalDifference = goalDifference.toString(),
            goalsAgainst = goalsAgainst.toString(),
            goalsFor = goalsFor.toString(),
            lost = lost.toString(),
            playedGames = playedGames.toString(),
            points = points.toString(),
            position = position.toString(),
            crestUrl = team.crestUrl ?: "",
            won = won.toString()
        )
    }
}
