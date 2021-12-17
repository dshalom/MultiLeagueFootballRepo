package com.ds.multileaguefootball.presentaion.leagueTable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ds.multileaguefootball.domain.model.TableEntry
import com.ds.multileaguefootball.presentaion.common.ErrorScreen
import com.ds.multileaguefootball.presentaion.common.FootballImage
import com.ds.multileaguefootball.presentaion.common.LoadingScreen

@Composable
fun LeagueTableScreen(
    navController: NavHostController,
    leagueTableViewModel: LeagueTableViewModel = hiltViewModel()
) {
    val viewState = leagueTableViewModel.viewState.collectAsState().value

    LaunchedEffect(true) {
        leagueTableViewModel.onStart()
    }

    when {
        viewState.loading -> {
            LoadingScreen()
        }
        viewState.error -> {
            ErrorScreen()
        }
        else -> {
            viewState.data?.table?.let { table ->

                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(4.dp)
                ) {

                    Column(Modifier.fillMaxSize()) {

                        Row(
                            Modifier
                                .height(20.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly

                        ) {
                            Spacer(modifier = Modifier.width(40.dp))
                            Spacer(modifier = Modifier.fillMaxWidth(0.375f))
                            Text(text = "", style = MaterialTheme.typography.h3)
                            Text(text = "P", style = MaterialTheme.typography.h3)
                            Text(text = "W", style = MaterialTheme.typography.h3)
                            Text(text = "D", style = MaterialTheme.typography.h3)
                            Text(text = "L", style = MaterialTheme.typography.h3)
                            Text(text = "G", style = MaterialTheme.typography.h3)
                            Text(text = "P", style = MaterialTheme.typography.h3)
                        }

                        LazyColumn {
                            itemsIndexed(table) { index, tableItem ->
                                LeagueItem(index, tableItem){

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LeagueItem(
    index: Int,
    tableItem: TableEntry,
    leagueItemClicked: (id: Int) -> Unit
) {
    Row(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(top = 6.dp)
            .clickable {
                leagueItemClicked(tableItem.id)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly

    ) {
        Text(
            text = (index + 1).toString(),
            style = MaterialTheme.typography.h3
        )

        FootballImage(
            modifier = Modifier
                .size(40.dp),
            context = LocalContext.current,
            url = tableItem.crestUrl
        )

        Text(
            text = tableItem.name,
            Modifier.fillMaxWidth(0.375f),
            style = MaterialTheme.typography.h3
        )
        Text(
            text = tableItem.playedGames,
            style = MaterialTheme.typography.h3
        )
        Text(text = tableItem.won, style = MaterialTheme.typography.h3)
        Text(text = tableItem.draw, style = MaterialTheme.typography.h3)
        Text(text = tableItem.lost, style = MaterialTheme.typography.h3)
        Text(
            text = tableItem.goalDifference,
            style = MaterialTheme.typography.h3
        )
        Text(
            text = tableItem.points,
            style = MaterialTheme.typography.h3
        )
    }
}
