package com.ds.multileaguefootball.presentaion.pickALeague

import com.ds.multileaguefootball.domain.model.Competition

data class PickALeagueState(
    val data: List<Competition>? = null,
    val error: Boolean = false,
    val loading: Boolean = false
)
