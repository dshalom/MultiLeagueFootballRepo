package com.ds.multileaguefootball.domain.model

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

data class Standings(
    val competition: Competition,
    val standings: List<Standing>
)

data class Standing(
    val table: List<Table>? = null
)

data class Table(
    val draw: Int,
    val goalDifference: Int,
    val goalsAgainst: Int,
    val goalsFor: Int,
    val lost: Int,
    val playedGames: Int,
    val points: Int,
    val position: Int,
    val team: Team,
    val won: Int
)

data class Team(
    val crestUrl: String,
    val id: Int,
    val name: String
)
