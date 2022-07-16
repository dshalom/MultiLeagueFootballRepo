package com.ds.multileaguefootball.data.httpclient

import com.ds.multileaguefootball.data.model.CompetitionsDto
import com.ds.multileaguefootball.data.model.MatchesDto
import com.ds.multileaguefootball.data.model.StandingsDto
import com.ds.multileaguefootball.data.model.TeamDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import javax.inject.Inject

interface ApiService {
    suspend fun fetchCompetitions(): CompetitionsDto?
    suspend fun fetchStandings(leagueId: Int): StandingsDto?
    suspend fun fetchTeam(teamId: Int): TeamDto?
    suspend fun fetchMatches(
        teamId: Int,
        status: String,
        dateFrom: String,
        dateTo: String
    ): MatchesDto?
}

class ApiServiceImpl @Inject constructor(
    private val client: HttpClient,
    private val baseAddress: String
) : ApiService {
    override suspend fun fetchCompetitions(): CompetitionsDto? =
        client.get("${baseAddress}competitions")

    override suspend fun fetchStandings(leagueId: Int): StandingsDto? =
        client.get("${baseAddress}competitions/$leagueId/standings")

    override suspend fun fetchTeam(teamId: Int): TeamDto? =
        client.get("${baseAddress}teams/$teamId")

    override suspend fun fetchMatches(
        teamId: Int,
        status: String,
        dateFrom: String,
        dateTo: String
    ): MatchesDto? =
        client.get("${baseAddress}teams/$teamId/matches?status=$status&dateFrom=$dateFrom&dateTo=$dateTo")
}
