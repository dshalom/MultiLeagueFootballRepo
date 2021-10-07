package com.ds.multileaguefootball.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Area(
    val countryCode: String,
    val ensignUrl: String?,
    val id: Int,
    val name: String
)
