package com.ds.multileaguefootball.data.model

import com.ds.multileaguefootball.domain.model.TableEntry
import kotlinx.serialization.Serializable

@Serializable
data class StandingDto(
    val table: List<TableDto>
) {

    fun toDomain(): List<TableEntry> {
        return table.map { it.toDomain() }
    }
}
