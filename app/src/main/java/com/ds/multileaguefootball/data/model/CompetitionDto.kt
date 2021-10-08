package com.ds.multileaguefootball.data.model

import com.ds.multileaguefootball.domain.model.Competition
import kotlinx.serialization.Serializable

@Serializable
data class CompetitionDto(
    val area: AreaDto,
    val currentSeason: CurrentSeasonDto?,
    val emblemUrl: String?,
    val id: Int,
    val lastUpdated: String,
    val name: String,
    val numberOfAvailableSeasons: Int,
    val plan: String,
) {
    fun toDomain(): Competition {
        return Competition(
            id = id,
            name = name,
            areaName = area.name,
            countryCode = area.countryCode,
            currentSeasonId = currentSeason?.id,
            currentSeasonStartDate = currentSeason?.startDate,
            currentSeasonEndDate = currentSeason?.endDate,
            ensignUrl = area.ensignUrl
        )
    }
}
