package com.ds.multileaguefootball.presentaion.pickALeague

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import timber.log.Timber

@Composable
fun PickALeagueScreen(
    navController: NavHostController,
    pickALeagueViewModel: PickALeagueViewModel = hiltViewModel()
) {
    val viewState = pickALeagueViewModel.viewState.collectAsState().value

    viewState.data?.also {
        Timber.i("dsds dataupdate")
        Text(text = it.size.toString())
    }
}
