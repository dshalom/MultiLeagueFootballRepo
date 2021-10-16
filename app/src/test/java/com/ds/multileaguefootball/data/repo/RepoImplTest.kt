package com.ds.multileaguefootball.data.repo

import com.ds.multileaguefootball.data.InMemoryCache
import com.ds.multileaguefootball.data.httpclient.ApiService
import com.ds.multileaguefootball.data.model.CompetitionsDto
import com.ds.multileaguefootball.data.model.StandingsDto
import com.ds.multileaguefootball.domain.model.Competition
import com.ds.multileaguefootball.domain.model.Standings
import com.ds.multileaguefootball.domain.repo.Repo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class RepoImplTest {

    @MockK
    private lateinit var apiService: ApiService

    @MockK
    private lateinit var competitionsDto: CompetitionsDto

    @MockK
    private lateinit var competitions: List<Competition>

    @MockK
    private lateinit var standings: Standings

    @MockK
    private lateinit var standingsDto: StandingsDto

    private lateinit var cut: Repo

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `when fetchLeagues called and cache exists then return cache`() = runBlocking {

        val inMemoryCache = InMemoryCache()
        inMemoryCache.competitions = competitions

        cut = RepoImpl(apiService, inMemoryCache)

        assertEquals(competitions, cut.fetchLeagues())
        coVerify(exactly = 0) { apiService.getLeagues() }
    }

    @Test
    fun `when fetchLeagues called and cache does not exists then return from remote and set cache`() =
        runBlocking {

            val inMemoryCache = InMemoryCache()

            coEvery {
                apiService.getLeagues()
            } returns competitionsDto

            every {
                competitionsDto.toDomain()
            } returns competitions

            cut = RepoImpl(apiService, inMemoryCache)

            assertEquals(competitions, cut.fetchLeagues())

            coVerify(exactly = 1) { apiService.getLeagues() }
            assertEquals(competitions, inMemoryCache.competitions)
        }

    @Test
    fun `when fetchStandings called and cache exists then return cache`() = runBlocking {
        val leagueId = 101
        val inMemoryCache = InMemoryCache()
        inMemoryCache.standings[leagueId] = standings

        cut = RepoImpl(apiService, inMemoryCache)

        coVerify(exactly = 0) { apiService.getStandings(leagueId) }

        assertEquals(standings, cut.fetchStandings(leagueId))
    }

    @Test
    fun `when fetchStandings called and cache does not exists then return from remote and set cache`() =
        runBlocking {
            val leagueId = 101
            val inMemoryCache = InMemoryCache()
            coEvery {
                apiService.getStandings(leagueId)
            } returns standingsDto

            every {
                standingsDto.toDomain()
            } returns standings

            cut = RepoImpl(apiService, inMemoryCache)

            assertEquals(standings, cut.fetchStandings(leagueId))
            assertEquals(standings, inMemoryCache.standings[leagueId])

            coVerify(exactly = 1) { apiService.getStandings(leagueId) }
        }
}
