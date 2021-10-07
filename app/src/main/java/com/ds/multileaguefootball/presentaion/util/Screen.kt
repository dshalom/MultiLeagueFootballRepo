package com.ds.multileaguefootball.presentaion.util

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.ds.multileaguefootball.R

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object LeagueTable : Screen("leagueTable", R.string.league_table, Icons.Filled.TableRows)
    object PickALeague : Screen("pickALeague", R.string.pick_a_league, Icons.Filled.EmojiEvents)
}
