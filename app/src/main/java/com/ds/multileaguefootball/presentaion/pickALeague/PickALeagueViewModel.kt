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

    private val _navigateTo: MutableStateFlow<Int> =
        MutableStateFlow(-1)

    val navigateTo: StateFlow<Int> = _navigateTo

    init {
        getStoredLeague()
    }

    fun getStoredLeague() {
        viewModelScope.launch {
            savedLeagueUseCase.getStoredLeagueId().collect {
                _navigateTo.value = it ?: 0
            }
        }
    }

    fun storeLeagueId(leagueId: Int) {
        viewModelScope.launch {
            savedLeagueUseCase.storeLeagueId(leagueId = leagueId)
        }
    }

    fun fetchLeagues() {
        viewModelScope.launch {
            val result = fetchLeaguesUseCase(Unit)
            result.collect {
                _viewState.value = when (it) {
                    is Resource.Error -> PickALeagueState(error = true)
                    is Resource.Loading -> PickALeagueState(loading = true)
                    is Resource.Success -> {
                        PickALeagueState(
                            data = it.data,
                            error = false
                        )
                    }
                }
            }
        }
    }
}
