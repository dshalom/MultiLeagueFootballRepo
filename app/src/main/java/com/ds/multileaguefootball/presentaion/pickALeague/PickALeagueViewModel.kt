package com.ds.multileaguefootball.presentaion.pickALeague

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PickALeagueViewModel @Inject constructor() : ViewModel() {
    fun doint() = "pick a league"
}
