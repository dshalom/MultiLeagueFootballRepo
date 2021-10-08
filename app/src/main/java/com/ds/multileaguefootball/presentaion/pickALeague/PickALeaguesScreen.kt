package com.ds.multileaguefootball.presentaion.pickALeague

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import timber.log.Timber

@Composable
fun PickALeagueScreen(
    navController: NavHostController,
    pickALeagueViewModel: PickALeagueViewModel = hiltViewModel()
) {

    val vs = pickALeagueViewModel.viewState.value
    Timber.i("dsds re")

    vs.data?.also {
        Timber.i("dsds dataupdate")
        Text(text = vs.data.size.toString())
    }
}
