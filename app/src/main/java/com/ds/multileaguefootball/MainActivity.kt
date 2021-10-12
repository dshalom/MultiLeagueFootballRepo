package com.ds.multileaguefootball

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ds.multileaguefootball.presentaion.leagueTable.LeagueTableScreen
import com.ds.multileaguefootball.presentaion.pickALeague.PickALeagueScreen
import com.ds.multileaguefootball.presentaion.util.Screen
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

                    val items = listOf(
                        Screen.LeagueTable,
                        Screen.PickALeague,
                    )

                    Scaffold(
                        bottomBar = {
                            BottomNavigation {
                                val navBackStackEntry by navController.currentBackStackEntryAsState()
                                val currentDestination = navBackStackEntry?.destination
                                items.forEach { screen ->
                                    BottomNavigationItem(
                                        icon = { Icon(screen.icon, contentDescription = null) },
                                        label = { Text(stringResource(screen.resourceId)) },
                                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                        onClick = {
                                            navController.navigate(screen.route) {
                                                restoreState = true
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    ) { innerPadding ->
                        NavHost(
                            navController,
                            startDestination = Screen.PickALeague.route,
                            Modifier.padding(innerPadding)
                        ) {
                            composable(
                                Screen.LeagueTable.route + "?leagueId={leagueId}",
                                arguments = listOf(
                                    navArgument(name = "leagueId") {
                                        type = NavType.IntType
                                        defaultValue = 0
                                    }
                                )
                            ) { entry ->
                                LeagueTableScreen(
                                    navController,
                                    entry.arguments?.getInt("leagueId") ?: 0
                                )
                            }

                            composable(Screen.PickALeague.route) {
                                PickALeagueScreen(
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
