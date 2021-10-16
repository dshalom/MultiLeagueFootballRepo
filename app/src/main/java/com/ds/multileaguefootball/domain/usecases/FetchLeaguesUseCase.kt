package com.ds.multileaguefootball.domain.usecases

import com.ds.multileaguefootball.domain.common.Resource
import com.ds.multileaguefootball.domain.model.Competition
import com.ds.multileaguefootball.domain.repo.Repo
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchLeaguesUseCase @Inject constructor(
    private val repo: Repo,
    private val availableLeagues: List<Int>
) : BaseUseCase<Unit, List<Competition>> {

    override suspend fun invoke(other: Unit) = flow {
        try {
            val result = repo.fetchLeagues()
                .filter {
                    it.ensignUrl != null && availableLeagues.contains(it.id)
                }
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }
}
