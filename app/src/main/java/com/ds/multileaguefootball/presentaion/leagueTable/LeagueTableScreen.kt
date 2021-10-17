package com.ds.multileaguefootball.presentaion.leagueTable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
        LazyColumn {
            items(it.standings[0].table!!) { tableItem ->

                Text(
                    text = tableItem.name,
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
        }
    }
}
