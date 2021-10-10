package com.ds.multileaguefootball.presentaion.leagueTable

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun LeagueTableScreen(leagueId: Int, navController: NavHostController) {

    Text(text = "LeagueTableScreen $leagueId")
}
