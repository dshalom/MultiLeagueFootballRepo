package com.ds.multileaguefootball.presentaion.leagueTable

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun LeagueTableScreen(navController: NavHostController, leagueId: Int) {

    Text(text = "LeagueTableScreen $leagueId")
}
