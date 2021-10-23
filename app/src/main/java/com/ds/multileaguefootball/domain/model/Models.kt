package com.ds.multileaguefootball.domain.model

data class Standings(
    val competition: Competition,
    val table: List<TableEntry>
)

data class TableEntry(
    val crestUrl: String,
    val id: Int,
    val name: String,
    val draw: String,
    val goalDifference: String,
    val goalsAgainst: String,
    val goalsFor: String,
    val lost: String,
    val playedGames: String,
    val points: String,
    val position: String,
    val won: String
)

data class Competition(
    val id: Int,
    val name: String,
    val areaName: String?,
    val countryCode: String?,
    val currentSeasonId: Int?,
    val currentSeasonStartDate: String?,
    val currentSeasonEndDate: String?,
    val ensignUrl: String
)
