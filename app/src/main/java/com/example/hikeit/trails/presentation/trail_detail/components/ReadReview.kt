package com.example.hikeit.trails.presentation.trail_detail.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.hikeit.R
import com.example.hikeit.trails.presentation.models.UserCommentUi
import java.util.UUID


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadReviewModal(
    comment: UserCommentUi,
    onDiscardButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedImage = rememberSaveable { mutableStateOf<String?>(null) }
    when {
        selectedImage.value != null -> {
            Log.d("Full screen image", selectedImage.value.toString())
            FullScreenImage(selectedImage.value!!) { selectedImage.value = null }
        }
    }
    ModalBottomSheet(onDismissRequest = { onDiscardButton() }) {
        Card(
            modifier = modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 48.dp, horizontal = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        "Recenzja",
                        style = MaterialTheme.typography.titleLarge
                    )
                    IconButton(
                        onClick = onDiscardButton,
                        modifier = Modifier
                            .size(32.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(Color.LightGray),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Anuluj",
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.padding(vertical = 16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(comment.userAvatarUrl)
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(R.drawable.ic_broken_image),
                        contentDescription = "User avatar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                    )
                    Text(text = comment.username, style = MaterialTheme.typography.labelLarge)
                }
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                StarRow(mark = comment.rating)
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                Text(
                    text = comment.comment ?: "",
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.padding(vertical = 16.dp))
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(150.dp),
                    modifier = Modifier
                        .fillMaxHeight()
                        .wrapContentWidth()
                ) {
                    items(
                        items = comment.photosUrl,
                        key = { photo -> UUID.randomUUID().toString() }) { photo ->
                        ReviewPhotoCard(
                            photo,
                            modifier = Modifier
                                .padding(1.dp)
                                .fillMaxWidth()
                                .aspectRatio(1.5f)
                                .clickable {
                                    selectedImage.value = photo
                                }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StarRow(mark: Int, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = if (mark >= 1) Icons.Default.Star else Icons.Default.StarOutline,
            contentDescription = "Star",
            modifier = Modifier
                .size(32.dp),
            tint = MaterialTheme.colorScheme.inversePrimary
        )
        Spacer(modifier = Modifier.padding(horizontal = 8.dp))
        Icon(
            imageVector = if (mark >= 2) Icons.Default.Star else Icons.Default.StarOutline,
            contentDescription = "Star",
            modifier = Modifier
                .size(32.dp),
            tint = MaterialTheme.colorScheme.inversePrimary
        )
        Spacer(modifier = Modifier.padding(horizontal = 8.dp))
        Icon(
            imageVector = if (mark >= 3) Icons.Default.Star else Icons.Default.StarOutline,
            contentDescription = "Star",
            modifier = Modifier
                .size(32.dp),
            tint = MaterialTheme.colorScheme.inversePrimary
        )
        Spacer(modifier = Modifier.padding(horizontal = 8.dp))
        Icon(
            imageVector = if (mark >= 4) Icons.Default.Star else Icons.Default.StarOutline,
            contentDescription = "Star",
            modifier = Modifier
                .size(32.dp),
            tint = MaterialTheme.colorScheme.inversePrimary
        )
        Spacer(modifier = Modifier.padding(horizontal = 8.dp))
        Icon(
            imageVector = if (mark >= 5) Icons.Default.Star else Icons.Default.StarOutline,
            contentDescription = "Star",
            modifier = Modifier
                .size(32.dp),
            tint = MaterialTheme.colorScheme.inversePrimary
        )
    }
}

@Composable
fun ReviewPhotoCard(photoUri: String, modifier: Modifier = Modifier) {
    AsyncImage(
        model = photoUri,
        contentDescription = "Image of trail",
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}

@Composable
fun FullScreenImage(photoUrl: String, onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = { onDismiss() },
        DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.85f))
                .clickable { onDismiss() }
        )
        {
            Image(
                painter = rememberAsyncImagePainter(model = photoUrl),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            )
        }
    }
}

