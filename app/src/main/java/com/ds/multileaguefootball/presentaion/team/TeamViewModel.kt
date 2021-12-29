package com.ds.multileaguefootball.presentaion.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ds.multileaguefootball.domain.common.Resource
import com.ds.multileaguefootball.domain.usecases.FetchMatchesUseCase
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
    private val fetchMatchesUseCase: FetchMatchesUseCase
) : ViewModel() {

    private val _viewState: MutableStateFlow<TeamState> =
        MutableStateFlow(TeamState(null, null, false, false))
    val viewState: StateFlow<TeamState> = _viewState

    fun onStart(teamId: Int?) {
        viewModelScope.launch {
            teamId?.let {
                fetchTeamUseCase(it).combine(fetchMatchesUseCase(it)) { team, matches ->
                    _viewState.value = when (team) {
                        is Resource.Error -> viewState.value.copy(
                            error = true,
                            loading = false,
                            teamData = null
                        )
                        is Resource.Loading -> viewState.value.copy(
                            loading = true,
                            error = false,
                            teamData = null
                        )
                        is Resource.Success -> _viewState.value.copy(
                            teamData = team.data,
                            error = false,
                            loading = false
                        )
                    }
                    _viewState.value = when (matches) {
                        is Resource.Error -> viewState.value.copy(
                            error = true,
                            loading = false,
                            matchesData = null
                        )
                        is Resource.Loading -> viewState.value.copy(
                            loading = true,
                            error = false,
                            matchesData = null
                        )
                        is Resource.Success -> _viewState.value.copy(
                            matchesData = matches.data,
                            error = false,
                            loading = false
                        )
                    }
                }.collect {}
            }
        }
    }
}
