package com.ds.multileaguefootball.domain.model

data class Standings(
    val competition: Competition,
    val table: List<TableEntry>
)

data class TableEntry(
    val crestUrl: String?,
    val id: Int,
    val name: String,
    val draw: Int,
    val goalDifference: Int,
    val goalsAgainst: Int,
    val goalsFor: Int,
    val lost: Int,
    val playedGames: Int,
    val points: Int,
    val position: Int,
    val won: Int
)

data class Competition(
    val id: Int,
    val name: String,
    val areaName: String?,
    val countryCode: String?,
    val currentSeasonId: Int?,
    val currentSeasonStartDate: String?,
    val currentSeasonEndDate: String?,
    val ensignUrl: String?
)
