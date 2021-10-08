package com.ds.multileaguefootball.data.model

import com.ds.multileaguefootball.domain.model.Competition
import kotlinx.serialization.Serializable

@Serializable
data class CompetitionsDto(
    val competitions: List<CompetitionDto>,
    val count: Int
) {
    fun toDomain(): List<Competition> {
        return competitions.map { it.toDomain() }
    }
}
