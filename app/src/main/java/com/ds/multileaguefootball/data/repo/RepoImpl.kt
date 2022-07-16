package com.ds.multileaguefootball.data.repo

import com.ds.multileaguefootball.data.InMemoryCache
import com.ds.multileaguefootball.data.httpclient.ApiService
import com.ds.multileaguefootball.domain.model.Competition
import com.ds.multileaguefootball.domain.model.Matches
import com.ds.multileaguefootball.domain.model.Standings
import com.ds.multileaguefootball.domain.model.Team
import com.ds.multileaguefootball.domain.repo.Repo
import timber.log.Timber
import javax.inject.Inject

data class FetchMatchParams(
    val teamId: Int,
    val status: String,
    val dateFrom: String,
    val dateTo: String
)

class RepoImpl @Inject constructor(
    private val apiService: ApiService,
    private val inMemoryCache: InMemoryCache
) : Repo {
    override suspend fun fetchCompetitions(): List<Competition>? {
        return inMemoryCache.competitions?.also {
            Timber.i("fetching leagues from cache")
        } ?: run {
            Timber.i("fetching leagues from remote")

            apiService.fetchCompetitions()?.toDomain().also {
                inMemoryCache.competitions = it
            }
        }
    }

    override suspend fun fetchStandings(leagueId: Int): Standings? {
        return inMemoryCache.standings[leagueId]?.also {
            Timber.i("fetching standings from cache")
        } ?: run {
            Timber.i("fetching standings from remote")
            apiService.fetchStandings(leagueId)?.toDomain()?.also {
                inMemoryCache.standings[leagueId] = it
            }
        }
    }

    override suspend fun fetchTeam(teamId: Int): Team? {
        return inMemoryCache.teams[teamId]?.also {
            Timber.i("fetching team from cache")
        } ?: run {
            Timber.i("fetching team from remote")
            return apiService.fetchTeam(teamId)?.toDomain()?.also {
                inMemoryCache.teams[teamId] = it
            }
        }
    }

    override suspend fun fetchMatches(
        teamId: Int,
        status: String,
        dateFrom: String,
        dateTo: String
    ): Matches? {
        val fetchMatchParams = FetchMatchParams(
            teamId = teamId,
            status = status,
            dateFrom = dateFrom,
            dateTo = dateTo
        )

        return inMemoryCache.matches[fetchMatchParams]?.also {
            Timber.i("fetching matches from cache")
        } ?: run {
            Timber.i("fetching matches from remote")
            return apiService.fetchMatches(teamId, status, dateFrom, dateTo)?.toDomain()
                ?.also {
                    inMemoryCache.matches[fetchMatchParams] = it
                }
        }
    }
}
