package com.ds.multileaguefootball.presentaion.leagueTable

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun LeagueTableScreen(
    navController: NavHostController,
    leagueId: String?,
    leagueTableViewModel: LeagueTableViewModel = hiltViewModel()
) {
    val viewState = leagueTableViewModel.viewState.collectAsState().value

//    LaunchedEffect(true) {
//        leagueTableViewModel.fetchStandings(leagueId = leagueId)
//    }

//    viewState.data?.also {
//
//        Column(modifier = Modifier.fillMaxSize()) {
//
//            Text(text = "stangings ${it.competition.name}")
//            Text(text = "stangings ${it.standings.get(0).table?.get(0)?.team?.name}")
//        }
//    }

    Text(text = "stangings $leagueId")
}
