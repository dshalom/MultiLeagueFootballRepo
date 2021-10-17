package com.ds.multileaguefootball.domain.usecases

import com.ds.multileaguefootball.domain.common.Resource
import com.ds.multileaguefootball.domain.model.Standings
import com.ds.multileaguefootball.domain.repo.Repo
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchStandingsUseCase @Inject constructor(
    private val repo: Repo
) : BaseUseCase<Int, Standings> {
    override suspend fun invoke(leagueId: Int) = flow {
        try {
            emit(Resource.Loading())
            val result = repo.fetchStandings(leagueId)

            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }
}
