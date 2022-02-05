package com.ds.multileaguefootball.presentaion.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ds.multileaguefootball.domain.common.Resource
import com.ds.multileaguefootball.domain.usecases.FetchLastMatchUseCase
import com.ds.multileaguefootball.domain.usecases.FetchNextMatchesUseCase
import com.ds.multileaguefootball.domain.usecases.FetchTeamUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamViewModel @Inject constructor(
    private val fetchTeamUseCase: FetchTeamUseCase,
    private val fetchNextMatchesUseCase: FetchNextMatchesUseCase,
    private val fetchLastMatchUseCase: FetchLastMatchUseCase
) : ViewModel() {

    private val _viewState: MutableStateFlow<TeamState> =
        MutableStateFlow(TeamState(null, null, null, false, false))
    val viewState: StateFlow<TeamState> = _viewState

    fun onStart(teamId: Int?) {
        viewModelScope.launch {
            teamId?.let {
                fetchTeamUseCase(it).combine(fetchNextMatchesUseCase(it)) { team, matches ->
                    _viewState.value = when (team) {
                        is Resource.Error -> _viewState.value.copy(
                            error = true,
                            loading = false,
                        )
                        is Resource.Loading -> _viewState.value.copy(
                            loading = true,
                            error = false,
                        )
                        is Resource.Success -> _viewState.value.copy(
                            teamData = team.data,
                            error = false,
                            loading = false
                        )
                    }
                    _viewState.value = when (matches) {
                        is Resource.Error -> _viewState.value.copy(
                            error = true,
                            loading = false,
                        )
                        is Resource.Loading -> _viewState.value.copy(
                            loading = true,
                            error = false,
                        )
                        is Resource.Success -> _viewState.value.copy(
                            nextMatchesData = matches.data,
                            error = false,
                            loading = false
                        )
                    }
                }.collect {}

                fetchLastMatchUseCase(it).collect { match ->
                    _viewState.value = when (match) {
                        is Resource.Error -> _viewState.value.copy(
                            error = true,
                            loading = false,
                        )
                        is Resource.Loading -> _viewState.value.copy(
                            loading = true,
                            error = false
                        )
                        is Resource.Success -> _viewState.value.copy(
                            lastMatchData = match.data,
                            error = false,
                            loading = false
                        )
                    }
                }
            }
        }
    }
}
