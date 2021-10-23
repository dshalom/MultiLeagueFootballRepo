package com.ds.multileaguefootball.presentaion.common

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.ImageLoader
import coil.compose.LocalImageLoader
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder

@Composable
fun FootballImage(modifier: Modifier, context: Context, url: String) {

    val imageLoader = remember {
        ImageLoader.Builder(context)
            .componentRegistry {
                add(SvgDecoder(context))
            }
            .build()
    }

    CompositionLocalProvider(LocalImageLoader provides imageLoader) {
        val painter = rememberImagePainter(
            data = url
        )

        Image(
            painter = painter,
            contentDescription = "SVG Image",
            modifier = modifier,
            contentScale = ContentScale.FillBounds
        )
    }
}
