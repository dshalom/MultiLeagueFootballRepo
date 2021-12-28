package com.ds.multileaguefootball.presentaion.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ds.multileaguefootball.domain.usecases.FetchTeamUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TeamViewModel @Inject constructor(
    private val fetchTeamUseCase: FetchTeamUseCase
) : ViewModel() {

    private val _viewState: MutableStateFlow<TeamState> =
        MutableStateFlow(TeamState(null, false))
    val viewState: StateFlow<TeamState> = _viewState

    init {
    }

    fun onLeagueItemClicked(teamId: Int) {
        viewModelScope.launch {
            fetchTeamUseCase(teamId).collect {

                Timber.i("dsds  ${it.data}")

                it.data?.squadMembers?.forEach { teamMember ->
                    Timber.i("dsds  ${teamMember.name}")
                }
            }
        }
    }
}
