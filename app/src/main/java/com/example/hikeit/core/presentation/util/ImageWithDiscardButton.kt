package com.example.hikeit.core.presentation.util

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage

@Composable
fun ImageWithDiscardButton(
    onDiscard: () -> Unit,
    image: Uri?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(256.dp)
    ) {
        Surface(
            color = Color(red = 0f, green = 0f, blue = 0f, alpha = 0.5f),
            shape = CircleShape,
            modifier = Modifier
                .size(32.dp)
                .clickable(onClick = onDiscard)
                .padding(top = 8.dp, end = 8.dp)
                .align(Alignment.TopEnd)
                .zIndex(2f),
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Discard image",
                    modifier = Modifier
                        .size(16.dp),
                    tint = Color(
                        red = 255f,
                        green = 255f,
                        blue = 255f,
                        alpha = 0.7f
                    ),
                )
            }
        }
        AsyncImage(
            model = image,
            contentDescription = "Image of trail",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(256.dp)
        )
    }
}