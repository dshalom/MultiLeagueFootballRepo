package com.ds.multileaguefootball.presentaion.leagueTable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ds.multileaguefootball.domain.common.Resource
import com.ds.multileaguefootball.domain.usecases.FetchLeaguesUseCase
import com.ds.multileaguefootball.domain.usecases.FetchStandingsUseCase
import com.ds.multileaguefootball.domain.usecases.FetchTeamUseCase
import com.ds.multileaguefootball.domain.usecases.SavedLeagueUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LeagueTableViewModel @Inject constructor(
    private val fetchStandingsUseCase: FetchStandingsUseCase,
    private val savedLeagueUseCase: SavedLeagueUseCase,
    private val fetchTeamUseCase: FetchTeamUseCase,
    private val fetchLeaguesUseCase: FetchLeaguesUseCase
) : ViewModel() {

    private val _viewState: MutableStateFlow<LeagueTableState> =
        MutableStateFlow(
            LeagueTableState(
                standings = null,
                leagues = null,
                error = false,
                loading = false
            )
        )
    val viewState: StateFlow<LeagueTableState> = _viewState

    init {
        fetchLeagues()
    }

    fun onLeagueItemClicked(teamId: Int) {
        viewModelScope.launch {
            fetchTeamUseCase(teamId).collect {

                it.data?.squadMembers?.forEach { teamMember ->
                    Timber.i("dsds  ${teamMember.name}")
                }
            }
        }
    }

    fun onMenuItemClicked(leagueId: Int) {
        resetSelectedItem()
        viewModelScope.launch {
            savedLeagueUseCase.storeLeagueId(leagueId = leagueId)
        }
    }

    private fun resetSelectedItem() {
        _viewState.value.leagues?.find { it.selected }?.selected = false
    }

    private fun fetchLeagues() {
        viewModelScope.launch {
            val result = fetchLeaguesUseCase(Unit)
            result.collect { leaguesData ->

                _viewState.value = when (leaguesData) {
                    is Resource.Error -> viewState.value.copy(error = true)
                    is Resource.Loading -> viewState.value.copy(loading = true)
                    is Resource.Success -> {

                        viewState.value.copy(
                            leagues = leaguesData.data,
                            error = false
                        )
                    }
                }

                if (leaguesData is Resource.Success) {

                    savedLeagueUseCase.getStoredLeagueId().collect { storedId ->
                        storedId?.let {
                            leaguesData.data?.find { it.id == storedId }?.selected = true
                        } ?: kotlin.run {
                            savedLeagueUseCase.storeLeagueId(leaguesData.data?.get(0)?.id ?: 0)
                        }
                        fetchLeague()
                    }
                }
            }
        }
    }

    private fun fetchLeague() {
        viewModelScope.launch {
            savedLeagueUseCase.getStoredLeagueId()
                .collect { leagueId ->
                    leagueId?.let {
                        val result = fetchStandingsUseCase(leagueId = leagueId)
                        result.collect { resource ->
                            _viewState.value = when (resource) {
                                is Resource.Error -> viewState.value.copy(
                                    error = true,
                                    loading = false,
                                    standings = null
                                )
                                is Resource.Loading -> viewState.value.copy(
                                    loading = true,
                                    error = false,
                                    standings = null
                                )
                                is Resource.Success -> _viewState.value.copy(
                                    standings = resource.data,
                                    error = false,
                                    loading = false
                                )
                            }
                        }
                    } ?: kotlin.run {
                        _viewState.value = viewState.value.copy(
                            error = true,
                            loading = false,
                            standings = null
                        )
                    }
                }
        }
    }
}
