package com.example.hikeit.trails.presentation.create_trail.components

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hikeit.core.presentation.util.ImageWithDiscardButton

@Composable
fun ImagesRow(
    photos: List<Uri>,
    onImagesSelected: (List<Uri>) -> Unit,
    onDiscardImage: (Uri) -> Unit,
    modifier: Modifier = Modifier
) {

    val listState = rememberLazyListState()

    LazyRow(
        state = listState,
        modifier = modifier
            .padding(vertical = 16.dp)
            .height(256.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        item {
            AddImagesButton(
                onImagesSelected = onImagesSelected
            )
        }
        items(photos, key = { it.toString() }) { item ->
            var visible by remember {
                mutableStateOf(true)
            }
            AnimatedVisibility(
                visible = visible,
                exit = fadeOut() + shrinkHorizontally(),
                modifier = Modifier
                    .animateItem()
            ) {
                ImageWithDiscardButton(
                    onDiscard = {
                        visible = false
                        onDiscardImage(item)
                    },
                    image = item
                )
            }
        }
    }
}