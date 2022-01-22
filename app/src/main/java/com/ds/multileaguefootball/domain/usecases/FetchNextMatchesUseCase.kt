package com.ds.multileaguefootball.domain.usecases

import com.ds.multileaguefootball.domain.common.Resource
import com.ds.multileaguefootball.domain.common.Resource.Error
import com.ds.multileaguefootball.domain.model.Matches
import com.ds.multileaguefootball.domain.repo.Repo
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class FetchNextMatchesUseCase @Inject constructor(
    private val repo: Repo
) : BaseUseCase<Int, Matches> {
    override suspend fun invoke(teamId: Int) = flow {

        val current = LocalDateTime.now()
        val oneMonthAhead = current.plusMonths(1)

        val currentFormatted = current.format(DateTimeFormatter.ofPattern(FORMAT))
        val oneMonthAheadFormatted = oneMonthAhead.format(DateTimeFormatter.ofPattern(FORMAT))

        try {
            emit(Resource.Loading())
            val result = repo.fetchMatches(
                teamId = teamId,
                dateFrom = currentFormatted,
                dateTo = oneMonthAheadFormatted
            )?.let {
                it.copy(
                    matches = it.matches.take(3)
                )
            }
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Error(e.localizedMessage))
        }
    }

    companion object {
        private const val FORMAT = "yyyy-MM-dd"
    }
}
