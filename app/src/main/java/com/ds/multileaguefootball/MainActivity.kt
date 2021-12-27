package com.ds.multileaguefootball

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.ds.multileaguefootball.domain.model.Competition
import com.ds.multileaguefootball.presentaion.common.ErrorScreen
import com.ds.multileaguefootball.presentaion.common.LeaguesMenu
import com.ds.multileaguefootball.presentaion.common.LoadingScreen
import com.ds.multileaguefootball.presentaion.leagueTable.LeagueTableScreen
import com.ds.multileaguefootball.presentaion.pickALeague.PickALeagueViewModel
import com.ds.multileaguefootball.ui.theme.MultiLeagueFootballTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val leagueTableViewModel by viewModels<PickALeagueViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MultiLeagueFootballTheme {
                var appBarTitle by remember { mutableStateOf("MultiLeague Football") }
                var data by remember {
                    mutableStateOf(emptyList<Competition>())
                }

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()

                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(text = appBarTitle)
                                },
                                actions = {
                                    LeaguesMenu(data) {
                                        appBarTitle = it.name
                                        leagueTableViewModel.onLeagueItemClicked(it.id)
                                    }
                                }
                            )
                        }

                    ) {
                        val viewState = leagueTableViewModel.viewState.collectAsState().value
                        when {
                            viewState.loading -> {
                                LoadingScreen()
                            }
                            viewState.error -> {
                                ErrorScreen()
                            }
                            else -> {
                                data = viewState.data ?: emptyList()
                                LeagueTableScreen(
                                    navController
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
