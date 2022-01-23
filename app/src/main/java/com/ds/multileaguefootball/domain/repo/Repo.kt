package com.ds.multileaguefootball.domain.repo

import com.ds.multileaguefootball.domain.model.Competition
import com.ds.multileaguefootball.domain.model.Matches
import com.ds.multileaguefootball.domain.model.Standings
import com.ds.multileaguefootball.domain.model.Team

interface Repo {
    suspend fun fetchLeagues(): List<Competition>?
    suspend fun fetchStandings(leagueId: Int): Standings?
    suspend fun fetchTeam(teamId: Int): Team?
    suspend fun fetchMatches(teamId: Int, dateFrom: String, dateTo: String): Matches?
}
