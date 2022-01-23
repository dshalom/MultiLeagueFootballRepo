package com.ds.multileaguefootball.presentaion.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ds.multileaguefootball.domain.model.Competition

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
                    }
                ) {
                    LeagueMenuItem(it.name, it.ensignUrl ?: "", it.selected)
                }
            }
        }
    }
}

@Composable
fun LeagueMenuItem(title: String, url: String, selected: Boolean) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically

    ) {

        FootballImage(
            modifier = Modifier
                .size(20.dp),
            context = LocalContext.current, url = url
        )

        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title)
        Spacer(modifier = Modifier.width(16.dp))

        if (selected) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(Color.Red)
            )
        }
    }
}
