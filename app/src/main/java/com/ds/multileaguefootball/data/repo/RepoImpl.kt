package com.ds.multileaguefootball.data.repo

import com.ds.multileaguefootball.data.InMemoryCache
import com.ds.multileaguefootball.data.httpclient.ApiService
import com.ds.multileaguefootball.domain.model.Competition
import com.ds.multileaguefootball.domain.model.Standings
import com.ds.multileaguefootball.domain.repo.Repo
import timber.log.Timber
import javax.inject.Inject

class RepoImpl @Inject constructor(
    private val apiService: ApiService,
    private val inMemoryCache: InMemoryCache
) : Repo {
    override suspend fun fetchLeagues(): List<Competition> {

        return inMemoryCache.competitions?.also {
            Timber.i("fetching leagues from cache")
        } ?: run {
            Timber.i("fetching leagues from remote")

            apiService.getLeagues().toDomain().also {
                inMemoryCache.competitions = it
            }
        }
    }

    override suspend fun fetchStandings(leagueId: Int): Standings {
        return inMemoryCache.standings[leagueId]?.also {
            Timber.i("fetching standings from cache")
        } ?: run {
            Timber.i("fetching standings from remote")
            apiService.getStandings(leagueId).toDomain().also {
                inMemoryCache.standings.put(leagueId, it)
            }
        }
    }
}
