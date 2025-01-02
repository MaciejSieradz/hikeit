package com.example.hikeit.trails.presentation.create_trail.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.hikeit.R
import com.example.hikeit.core.presentation.util.dashedBorder

@Composable
fun AddImagesButton(
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

    Box(
        modifier = modifier
            .width(256.dp)
            .height(256.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = MaterialTheme.shapes.extraLarge
            )
            .dashedBorder(
                brush = SolidColor(MaterialTheme.colorScheme.primary),
                shape = MaterialTheme.shapes.extraLarge
            )
            .clickable {
                imagePicker.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            }
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                imageVector = Icons.Default.Photo,
                contentDescription = "Add image icon",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant),
                modifier = Modifier
                    .size(64.dp)
            )
            Text(
                text = stringResource(R.string.insert_photos),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

    }
}