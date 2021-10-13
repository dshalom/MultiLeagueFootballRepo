package com.ds.multileaguefootball.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AreaDto(
    val countryCode: String = "",
    val ensignUrl: String? = "",
    val id: Int,
    val name: String
)
