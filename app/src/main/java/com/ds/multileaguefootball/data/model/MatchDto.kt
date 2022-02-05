package com.ds.multileaguefootball.data.model

import com.ds.multileaguefootball.domain.model.Match
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Serializable
data class MatchDto(
    val awayTeam: AwayTeamDto,
    val homeTeam: HomeTeamDto,
    val utcDate: String,
    val score: ScoreDto
) {
    fun toDomain(): Match {
        return Match(
            dateTime = LocalDateTime.parse(utcDate, DateTimeFormatter.ISO_DATE_TIME).format(
                DateTimeFormatter.ofPattern(FORMAT)
            ),
            homeTeam = homeTeam.name, awayTeam = awayTeam.name,
            homeTeamScore = score.fullTime?.homeTeam,
            awayTeamScore = score.fullTime?.awayTeam
        )
    }

    companion object {
        private const val FORMAT = "MMMM dd HH:mma"
    }
}
