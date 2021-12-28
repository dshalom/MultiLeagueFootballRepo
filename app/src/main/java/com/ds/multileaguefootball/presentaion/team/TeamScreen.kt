package com.ds.multileaguefootball.presentaion.team

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun TeamScreen(navController: NavController, teamId: Int?) {

    Column(Modifier.fillMaxSize()) {

//        FootballImage(
//            modifier = Modifier
//                .size(40.dp),
//            context = LocalContext.current,
//            url = tableItem.crestUrl
//        )

        Text(text = teamId.toString(), style = MaterialTheme.typography.h3)
    }
}
