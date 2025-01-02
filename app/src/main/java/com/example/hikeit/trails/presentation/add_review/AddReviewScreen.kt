package com.example.hikeit.trails.presentation.add_review

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.example.hikeit.ui.theme.HikeItTheme
import java.util.UUID

@Composable
fun AddReviewRoute(
    viewModel: AddReviewViewModel,
    onAction: (AddReviewAction) -> Unit,
    onDiscardButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()
    AddReviewScreen(
        addReviewState = state,
        onAction = onAction,
        onDiscardButton = onDiscardButton,
        modifier = modifier
    )
}

@Composable
fun AddReviewScreen(
    addReviewState: AddReviewState,
    onAction: (AddReviewAction) -> Unit,
    onDiscardButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        ExtendedFloatingActionButton(
            onClick = { onAction(AddReviewAction.AddReview) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .zIndex(2f),
            icon = { Icon(Icons.Default.Create, "Create Button") },
            text = { Text("Opublikuj", style = MaterialTheme.typography.titleSmall) }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(vertical = 48.dp, horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    "jeden dwa trzy cztery",
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
            StarRow(
                mark = addReviewState.mark,
                onStarClicked = { onAction(AddReviewAction.MarkChanged(it)) })
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            OutlinedTextField(
                value = addReviewState.description ?: "",
                onValueChange = { onAction(AddReviewAction.DescriptionChanged(it)) },
                placeholder = { Text("Podziel się swoimi opiniami na temat wycieczki.") },
                minLines = 5,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(vertical = 16.dp))
            AddReviewImagesButton(
                onImagesSelected = { uri, photos ->
                    onAction(
                        AddReviewAction.PhotosSelected(
                            uri,
                            photos
                        )
                    )
                }
            )

            LazyVerticalGrid(
                columns = GridCells.Adaptive(150.dp),
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .fillMaxHeight()
            ) {
                items(
                    items = addReviewState.photosUri,
                    key = { photo -> UUID.randomUUID().toString() }) { photo ->
                    ReviewPhotoCard(
                        photo,
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                            .aspectRatio(1.5f)
                    )
                }
            }
        }
    }
}

@Composable
fun AddReviewImagesButton(
    onImagesSelected: (List<Uri>, List<ByteArray>) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
    ) {
        val contentResolver = context.contentResolver
        val photos = it.map {
            val inputStream = contentResolver.openInputStream(it)

            lateinit var contentBytes: ByteArray

            inputStream?.let { stream ->
                contentBytes = stream.readBytes()
            }

            inputStream?.close()
            contentBytes
        }
        onImagesSelected(it, photos)
    }

    FilledTonalButton(onClick = {
        imagePicker.launch(
            PickVisualMediaRequest(
                ActivityResultContracts.PickVisualMedia.ImageOnly
            )
        )
    }) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.AddAPhoto,
                contentDescription = "Add photo",
                modifier = Modifier.size(16.dp)
            )
            Text("Dodaj zdjęcia")

        }
    }

}

@Composable
fun StarRow(mark: Int, onStarClicked: (Int) -> Unit, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = if (mark >= 1) Icons.Default.Star else Icons.Default.StarOutline,
            contentDescription = "Star",
            modifier = Modifier
                .size(32.dp)
                .clickable { onStarClicked(1) },
            tint = MaterialTheme.colorScheme.inversePrimary
        )
        Spacer(modifier = Modifier.padding(horizontal = 8.dp))
        Icon(
            imageVector = if (mark >= 2) Icons.Default.Star else Icons.Default.StarOutline,
            contentDescription = "Star",
            modifier = Modifier
                .size(32.dp)
                .clickable { onStarClicked(2) },
            tint = MaterialTheme.colorScheme.inversePrimary
        )
        Spacer(modifier = Modifier.padding(horizontal = 8.dp))
        Icon(
            imageVector = if (mark >= 3) Icons.Default.Star else Icons.Default.StarOutline,
            contentDescription = "Star",
            modifier = Modifier
                .size(32.dp)
                .clickable { onStarClicked(3) },
            tint = MaterialTheme.colorScheme.inversePrimary
        )
        Spacer(modifier = Modifier.padding(horizontal = 8.dp))
        Icon(
            imageVector = if (mark >= 4) Icons.Default.Star else Icons.Default.StarOutline,
            contentDescription = "Star",
            modifier = Modifier
                .size(32.dp)
                .clickable { onStarClicked(4) },
            tint = MaterialTheme.colorScheme.inversePrimary
        )
        Spacer(modifier = Modifier.padding(horizontal = 8.dp))
        Icon(
            imageVector = if (mark >= 5) Icons.Default.Star else Icons.Default.StarOutline,
            contentDescription = "Star",
            modifier = Modifier
                .size(32.dp)
                .clickable { onStarClicked(5) },
            tint = MaterialTheme.colorScheme.inversePrimary
        )
    }
}

@Composable
fun ReviewPhotoCard(photoUri: Uri, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        AsyncImage(
            model = photoUri,
            contentDescription = "Image of trail",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun AddReviewScreenPreview() {
    HikeItTheme {
        AddReviewScreen(AddReviewState(3), {}, {})
    }
}