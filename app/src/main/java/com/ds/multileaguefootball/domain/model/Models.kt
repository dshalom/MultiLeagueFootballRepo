package com.ds.multileaguefootball.domain.model

data class Standings(
    val competition: Competition,
    val table: List<TableEntry>
)

data class TableEntry(
    val crestUrl: String?,
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
    val ensignUrl: String,
    var selected: Boolean = false
)

data class Team(
    val id: Int,
    val name: String,
    val shortName: String? = null,
    val squadMembers: List<SquadMember>? = null,
    val venue: String? = null,
    val website: String? = null,
    val activeCompetitions: List<String?>? = null
)

data class SquadMember(
    val dateOfBirth: String? = null,
    val id: Int,
    val name: String? = null,
    val position: String? = null,
    val role: String? = null,
    val shirtNumber: String? = null
)
