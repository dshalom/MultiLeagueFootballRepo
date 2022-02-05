package com.ds.multileaguefootball.presentaion.team

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ds.multileaguefootball.R
import com.ds.multileaguefootball.domain.model.Match
import com.ds.multileaguefootball.domain.model.Matches
import com.ds.multileaguefootball.domain.model.SquadMember
import com.ds.multileaguefootball.domain.model.Team
import com.ds.multileaguefootball.presentaion.common.ErrorScreen
import com.ds.multileaguefootball.presentaion.common.FootballImage
import com.ds.multileaguefootball.presentaion.common.LoadingScreen

@Composable
fun TeamScreen(navController: NavController, teamId: Int?) {

    val teamViewModel: TeamViewModel = hiltViewModel()
    val viewState = teamViewModel.viewState.collectAsState().value

    var initialApiCalled by rememberSaveable { mutableStateOf(false) }

    if (!initialApiCalled) {
        LaunchedEffect(Unit) {
            teamViewModel.onStart(teamId)
            initialApiCalled = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                elevation = 4.dp,
                title = {
                    Text(text = viewState.teamData?.name ?: "")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                }
            )
        }

    ) {
        when {
            viewState.loading -> {
                LoadingScreen()
            }
            viewState.error -> {
                ErrorScreen()
            }
            else -> {
                TeamInfo(
                    team = viewState.teamData,
                    nextMatches = viewState.nextMatchesData,
                    lastMatch = viewState.lastMatchData,
                    liveMatch = viewState.liveMatchData
                )
            }
        }
    }
}

@Composable
fun TeamInfo(team: Team?, nextMatches: Matches?, lastMatch: Match?, liveMatch: Match?) {

    val scrollState = rememberScrollState()
    val uriHandler = LocalUriHandler.current

    Column(
        Modifier
            .fillMaxSize()
            .padding(12.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        team?.crestUrl?.let {
            FootballImage(
                modifier = Modifier
                    .size(120.dp),
                context = LocalContext.current,
                url = it
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        team?.venue?.also {
            Text(
                text = it, style = MaterialTheme.typography.h3,
                fontWeight = FontWeight.Bold
            )
        }

        team?.website?.let { link ->
            val annotatedLinkString = buildAnnotatedString {
                append(link)
                addStyle(
                    style = SpanStyle(
                        color = Color(0xff64B5F6),
                        textDecoration = TextDecoration.Underline
                    ),
                    start = 0, end = link.length
                )
            }

            ClickableText(
                text = annotatedLinkString,
                onClick = {
                    uriHandler.openUri(annotatedLinkString.text)
                }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))
        LiveMatch(liveMatch)
        Spacer(modifier = Modifier.height(12.dp))
        LastMatch(lastMatch)
        Spacer(modifier = Modifier.height(12.dp))
        UpcomingMatches(nextMatches)
        Spacer(modifier = Modifier.height(12.dp))
        SquadMembers(team?.squadMembers)
    }
}

@Composable
fun LiveMatch(lastMatch: Match?) {
    lastMatch?.let {
        Text(
            text = stringResource(R.string.live_match),
            style = MaterialTheme.typography.h2
        )

        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = CenterHorizontally
        ) {
            Text(
                text = "${lastMatch.homeTeam}  ${lastMatch.homeTeamScore}",
                color = Color.Red,
                style = MaterialTheme.typography.h3,
            )
            Text(
                text = stringResource(R.string.vs),
                color = Color.Red,
                style = MaterialTheme.typography.subtitle1
            )
            Text(
                text = "${lastMatch.awayTeam}  ${lastMatch.awayTeamScore}",
                color = Color.Red,
                style = MaterialTheme.typography.h3
            )
        }
    }
}

@Composable
fun LastMatch(lastMatch: Match?) {
    lastMatch?.let {
        Text(
            text = stringResource(R.string.last_match),
            style = MaterialTheme.typography.h2
        )

        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = CenterHorizontally
        ) {
            Text(
                text = "${lastMatch.homeTeam}  ${lastMatch.homeTeamScore}",
                style = MaterialTheme.typography.h3
            )
            Text(
                text = stringResource(R.string.vs),
                style = MaterialTheme.typography.subtitle1
            )

            Text(
                text = "${lastMatch.awayTeam}  ${lastMatch.awayTeamScore}",
                style = MaterialTheme.typography.h3
            )
        }
    }
}

@Composable
fun UpcomingMatches(matches: Matches?) {

    matches?.let {
        Text(
            text = stringResource(R.string.upcoming_matches),
            style = MaterialTheme.typography.h2
        )

        matches.matches.forEach { match ->
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = CenterHorizontally
            ) {
                Text(
                    text = match.homeTeam,
                    style = MaterialTheme.typography.h3
                )
                Text(
                    text = stringResource(R.string.vs),
                    style = MaterialTheme.typography.subtitle1
                )

                Text(
                    text = match.awayTeam,
                    style = MaterialTheme.typography.h3
                )
                Text(
                    text = match.dateTime,
                    style = MaterialTheme.typography.h3
                )
            }
        }
    }
}

@Composable
fun SquadMembers(squadMembers: List<SquadMember>?) {
    squadMembers?.let {
        Text(
            text = stringResource(R.string.squad),
            style = MaterialTheme.typography.h2
        )

        squadMembers.forEach { squadMember ->
            SquadMemberItem(squadMember)
        }
    }
}

@Composable
fun SquadMemberItem(squadMember: SquadMember) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = squadMember.name ?: "",
            style = MaterialTheme.typography.h3
        )

        Text(
            text = squadMember.position ?: "",
            style = MaterialTheme.typography.h3
        )
    }
}
