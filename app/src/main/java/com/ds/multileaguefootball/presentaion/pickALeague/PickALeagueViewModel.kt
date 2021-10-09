package com.ds.multileaguefootball.presentaion.pickALeague

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ds.multileaguefootball.domain.common.Resource
import com.ds.multileaguefootball.domain.usecases.FetchLeaguesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PickALeagueViewModel @Inject constructor(private val fetchLeaguesUseCase: FetchLeaguesUseCase) :
    ViewModel() {

    private val _viewState: MutableStateFlow<PickALeagueState> =
        MutableStateFlow(PickALeagueState(null, false))
    val viewState: StateFlow<PickALeagueState> = _viewState

    init {
        fetchLeagues()
    }

    private fun fetchLeagues() {
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