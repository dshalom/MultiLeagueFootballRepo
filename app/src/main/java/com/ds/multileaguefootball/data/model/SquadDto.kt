package com.ds.multileaguefootball.data.model

import com.ds.multileaguefootball.domain.model.SquadMember
import kotlinx.serialization.Serializable

@Serializable
data class SquadDto(
    val dateOfBirth: String? = null,
    val id: Int,
    val name: String? = null,
    val nationality: String? = null,
    val position: String? = null,
    val role: String? = null,
    val shirtNumber: String? = null
) {
    fun toDomain(): SquadMember {
        return SquadMember(
            dateOfBirth = dateOfBirth,
            id = id,
            name = name,
            position = position,
            role = role,
            shirtNumber = shirtNumber
        )
    }
}
