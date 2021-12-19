package com.ds.multileaguefootball

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.ds.multileaguefootball.presentaion.leagueTable.LeagueTableScreen
import com.ds.multileaguefootball.ui.theme.MultiLeagueFootballTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MultiLeagueFootballTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()

                    Scaffold(
                        topBar = { TopAppBarSample() },

                    ) {
                        LeagueTableScreen(
                            navController
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TopAppBarSample() {
    Column {
        TopAppBar(
            elevation = 4.dp,
            title = {
                Text("I'm a TopAppBar")
            },
            backgroundColor = MaterialTheme.colors.primarySurface,
            navigationIcon = {
                IconButton(onClick = { /* Do Something*/ }) {
                    Icon(Icons.Filled.ArrowBack, null)
                }
            }, actions = {
            IconButton(onClick = { /* Do Something*/ }) {
                Icon(Icons.Filled.Share, null)
            }
            IconButton(onClick = { /* Do Something*/ }) {
                Icon(Icons.Filled.Settings, null)
            }
        }
        )

        Text("Hello World")
    }
}
