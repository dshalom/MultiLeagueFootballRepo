package com.ds.multileaguefootball

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.ds.multileaguefootball.domain.model.Competition
import com.ds.multileaguefootball.presentaion.common.ErrorScreen
import com.ds.multileaguefootball.presentaion.common.FootballImage
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
                                        leagueTableViewModel.storeLeagueId(it.id)
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

@Composable
fun LeaguesMenu(data: List<Competition>, onClick: (Competition) -> Unit) {
    val expanded = remember { mutableStateOf(false) }

    Box(
        Modifier
            .wrapContentSize(Alignment.TopStart)
    ) {
        IconButton(onClick = {
            expanded.value = true
        }) {
            Icon(
                Icons.Filled.MoreVert,
                contentDescription = "More Menu"
            )
        }

        DropdownMenu(expanded = expanded.value, onDismissRequest = { expanded.value = false }) {

            data.forEach {
                DropdownMenuItem(
                    onClick = {
                        onClick(it)
                        expanded.value = false
                    },
                    modifier = Modifier.background(
                        if (it.selected) MaterialTheme.colors.primary
                        else Color.Transparent
                    )
                ) {
                    LeagueMenuItem(it.name, it.ensignUrl, it.selected)
                }
                Divider()
            }
        }
    }
}

@Composable
fun LeagueMenuItem(title: String, url: String, selected: Boolean) {
    Row(
        Modifier.fillMaxWidth()

    ) {

        FootballImage(
            modifier = Modifier
                .size(20.dp),
            context = LocalContext.current, url = url
        )

        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title)
    }
}
