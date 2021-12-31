package com.ds.multileaguefootball.data.model

import com.ds.multileaguefootball.domain.model.Matches
import kotlinx.serialization.Serializable

@Serializable
data class MatchesDto(
    val matches: List<MatchDto>
) {
    fun toDomain(): Matches = Matches(matches.map { it.toDomain() })
}
