package com.ds.multileaguefootball.presentaion.pickALeague

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.decode.SvgDecoder
import com.ds.multileaguefootball.domain.model.Competition
import com.ds.multileaguefootball.presentaion.common.ErrorScreen
import com.ds.multileaguefootball.presentaion.common.FootballImage
import com.ds.multileaguefootball.presentaion.common.LoadingScreen
import com.ds.multileaguefootball.presentaion.util.Screen
import com.ds.multileaguefootball.ui.theme.MultiLeagueFootballTheme

@Composable
fun PickALeagueScreen(
    navController: NavHostController,
    userAction: Boolean,
    pickALeagueViewModel: PickALeagueViewModel = hiltViewModel()
) {
    if (userAction) {
        fetchLeagues(pickALeagueViewModel, navController)
    } else {
        val navTo = pickALeagueViewModel.navigateTo.collectAsState().value
        LaunchedEffect(key1 = true) {
            pickALeagueViewModel.getStoredLeague()
        }

        when (navTo) {
            0 -> {
                fetchLeagues(pickALeagueViewModel, navController)
            }
            else -> {
                LaunchedEffect(key1 = true) {
                    navController.navigate(Screen.LeagueTable.route)
                }
            }
        }
    }
}

@Composable
private fun fetchLeagues(
    pickALeagueViewModel: PickALeagueViewModel,
    navController: NavHostController
) {
    val viewState = pickALeagueViewModel.viewState.collectAsState().value
    pickALeagueViewModel.fetchLeagues()
    when {
        viewState.loading -> {
            LoadingScreen()
        }
        viewState.error -> {
            ErrorScreen()
        }
        else -> {
            viewState.data?.let { competition ->
                LazyColumn {
                    items(competition) { item ->
                        LeagueItem(item) { leagueId ->
                            pickALeagueViewModel.storeLeagueId(leagueId)
                            navController.navigate(Screen.LeagueTable.route)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LeagueItem(competition: Competition, onclick: (Int) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onclick(competition.id) }
    ) {

        Spacer(modifier = Modifier.width(16.dp))

        FootballImage(
            modifier = Modifier
                .size(60.dp),
            context = LocalContext.current, url = competition.ensignUrl!!
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = competition.name,
            style = MaterialTheme.typography.h2
        )
    }
}

@Preview(
    name = "Night Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Day Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
@Suppress("UnusedPrivateMember")
private fun PrimaryButtonPreview() {
    val context = LocalContext.current
    val imageLoader = remember {
        ImageLoader.Builder(context)
            .componentRegistry {
                add(SvgDecoder(context))
            }
            .build()
    }
    val competition = Competition(
        id = 1,
        name = "English Football League",
        "UK",
        "Eng",
        4,
        "12:09:2020",
        "1:7:2021",
        "https://upload.wikimedia.org/wikipedia/commons/4/41/Flag_of_Austria.svg"
    )
    MultiLeagueFootballTheme {
        LeagueItem(
            competition = competition
        ) {
        }
    }
}
