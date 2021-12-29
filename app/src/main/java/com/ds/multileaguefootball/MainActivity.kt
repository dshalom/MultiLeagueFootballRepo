package com.ds.multileaguefootball

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ds.multileaguefootball.presentaion.leagueTable.LeagueTableScreen
import com.ds.multileaguefootball.presentaion.team.TeamScreen
import com.ds.multileaguefootball.presentaion.util.LeagueTableRoute
import com.ds.multileaguefootball.presentaion.util.TeamId
import com.ds.multileaguefootball.presentaion.util.TeamRoute
import com.ds.multileaguefootball.ui.theme.MultiLeagueFootballTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MultiLeagueFootballTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = LeagueTableRoute
                    ) {
                        composable(LeagueTableRoute) {
                            LeagueTableScreen(
                                navController
                            )
                        }

                        composable(
                            "$TeamRoute/{$TeamId}",
                            arguments = listOf(
                                navArgument(TeamId) {
                                    type = NavType.IntType
                                }
                            )
                        ) { backStackEntry ->
                            TeamScreen(
                                navController,
                                backStackEntry.arguments?.getInt(TeamId)
                            )
                        }
                    }
                }
            }
        }
    }
}
