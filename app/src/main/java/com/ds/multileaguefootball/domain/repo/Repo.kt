package com.ds.multileaguefootball.domain.repo

import com.ds.multileaguefootball.domain.model.Competition
import com.ds.multileaguefootball.domain.model.Standings

interface Repo {
    suspend fun fetchLeagues(): List<Competition>
    suspend fun fetchStandings(leagueId: Int): Standings
}
