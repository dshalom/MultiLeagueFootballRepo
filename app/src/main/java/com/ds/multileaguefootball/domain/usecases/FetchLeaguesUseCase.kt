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
        emit(Resource.Loading())
        try {
            val result = repo.fetchLeagues()
                ?.filter {
                    it.ensignUrl != null && availableLeagues.contains(it.id)
                }

            result?.let {
                emit(Resource.Success(result))
            } ?: kotlin.run {
                emit(Resource.Error<List<Competition>>("Error fetching "))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }
}
