package com.ds.multileaguefootball.domain.usecases

import com.ds.multileaguefootball.domain.common.Resource
import com.ds.multileaguefootball.domain.model.Match
import com.ds.multileaguefootball.domain.repo.Repo
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class FetchLastMatchUseCase @Inject constructor(
    private val repo: Repo
) : BaseUseCase<Int, Match> {
    override suspend fun invoke(teamId: Int) = flow {

        val current = LocalDateTime.now()
        val oneMonthBack = current.minusMonths(1)

        val currentDateTimeFormatted =
            current.format(DateTimeFormatter.ofPattern(FORMAT))
        val oneMonthAheadFormatted =
            oneMonthBack.format(DateTimeFormatter.ofPattern(FORMAT))

        try {
            emit(Resource.Loading())
            val result = repo.fetchMatches(
                teamId = teamId,
                status = STATUS,
                dateFrom = oneMonthAheadFormatted,
                dateTo = currentDateTimeFormatted
            )
            result?.matches?.last()?.let {
                emit(Resource.Success(it))
            } ?: kotlin.run {
                emit(Resource.Error<Match>("Error fetching last match"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }

    companion object {
        private const val FORMAT = "yyyy-MM-dd"
        private const val STATUS = "FINISHED"
    }
}
