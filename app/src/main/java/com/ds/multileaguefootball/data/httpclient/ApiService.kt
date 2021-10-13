package com.ds.multileaguefootball.data.httpclient

import com.ds.multileaguefootball.data.model.CompetitionsDto
import com.ds.multileaguefootball.data.model.StandingsDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import javax.inject.Inject

class ApiService @Inject constructor(
    private val client: HttpClient,
    private val baseAddress: String
) {
    suspend fun getLeagues(): CompetitionsDto =
        client.get("${baseAddress}competitions")

    suspend fun getStandings(leagueId: Int): StandingsDto =
        client.get("${baseAddress}competitions/$leagueId/standings")
}
