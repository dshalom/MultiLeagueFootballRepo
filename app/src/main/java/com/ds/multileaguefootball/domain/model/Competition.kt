package com.ds.multileaguefootball.domain.model

data class Competition(
    val id: Int,
    val name: String,
    val areaName: String,
    val countryCode: String,
    val currentSeasonId: Int?,
    val currentSeasonStartDate: String?,
    val currentSeasonEndDate: String?,
    val ensignUrl: String?
)
