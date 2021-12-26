package com.ds.multileaguefootball.presentaion.pickALeague

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ds.multileaguefootball.domain.common.Resource
import com.ds.multileaguefootball.domain.usecases.FetchLeaguesUseCase
import com.ds.multileaguefootball.domain.usecases.SavedLeagueUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PickALeagueViewModel @Inject constructor(
    private val fetchLeaguesUseCase: FetchLeaguesUseCase,
    private val savedLeagueUseCase: SavedLeagueUseCase
) :
    ViewModel() {

    private val _viewState: MutableStateFlow<PickALeagueState> =
        MutableStateFlow(PickALeagueState(null, false))
    val viewState: StateFlow<PickALeagueState> = _viewState

    init {
        fetchLeagues()
    }

    fun onLeagueItemClicked(leagueId: Int) {
        resetSelectedItem()
        viewModelScope.launch {
            savedLeagueUseCase.storeLeagueId(leagueId = leagueId)
        }
    }

    private fun resetSelectedItem() {
        _viewState.value.data?.find { it.selected }?.selected = false
    }

    private fun fetchLeagues() {
        viewModelScope.launch {
            val result = fetchLeaguesUseCase(Unit)
            result.collect { leaguesData ->
                _viewState.value = when (leaguesData) {
                    is Resource.Error -> PickALeagueState(error = true)
                    is Resource.Loading -> PickALeagueState(loading = true)
                    is Resource.Success -> {
                        PickALeagueState(
                            data = leaguesData.data,
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
                    }
                }
            }
        }
    }
}
