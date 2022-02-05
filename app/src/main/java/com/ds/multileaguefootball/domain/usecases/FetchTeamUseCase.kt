package com.ds.multileaguefootball.domain.usecases

import com.ds.multileaguefootball.domain.common.Resource
import com.ds.multileaguefootball.domain.model.Team
import com.ds.multileaguefootball.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchTeamUseCase @Inject constructor(
    private val repo: Repo
) : BaseUseCase<Int, Team> {
    override suspend fun invoke(teamId: Int): Flow<Resource<Team>> = flow {
        try {
            emit(Resource.Loading())

            repo.fetchTeam(teamId)?.let {
                emit(Resource.Success(it))
            } ?: kotlin.run {
                emit(Resource.Error<Team>("Error fetching team"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }
}
