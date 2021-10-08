package com.ds.multileaguefootball.presentaion.pickALeague

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun PickALeagueScreen(
    navController: NavHostController,
    hiltViewModel: PickALeagueViewModel = hiltViewModel()
) {

    Text(text = hiltViewModel.doint())
}
