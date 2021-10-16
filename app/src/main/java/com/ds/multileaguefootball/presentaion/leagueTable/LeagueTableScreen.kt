package com.ds.multileaguefootball.presentaion.leagueTable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun LeagueTableScreen(
    navController: NavHostController,
    leagueId: Int,
    leagueTableViewModel: LeagueTableViewModel = hiltViewModel()
) {
    val viewState = leagueTableViewModel.viewState.collectAsState().value

    if (leagueId != 0) {
        LaunchedEffect(true) {
            leagueTableViewModel.fetchStandings(leagueId = leagueId)
        }
    }

    viewState.data?.also {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = "stangings ${it.competition.name}")
            Text(text = "stangings ${it.standings.get(0).table?.get(0)?.team?.name}")
        }
    }
}
