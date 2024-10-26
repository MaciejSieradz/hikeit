package com.example.hikeit.trails.presentation.create_trail.components

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.maps.android.compose.GoogleMap

@Composable
fun AddGpxFile(
    onGpxSelected: (Uri) -> Unit
) {

    val context = LocalContext.current

    var visible by remember {
        mutableStateOf(false)
    }

    val gpxPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {

        it?.let { uri ->

            val contentResolver = context.contentResolver
            val inputStream = contentResolver.openInputStream(uri)

            inputStream?.let { stream ->
                val content = stream.bufferedReader().use { reader -> reader.readText() }
                Log.d("Content of gpx file", content)
            }

            inputStream?.close()

            onGpxSelected(uri)
            visible = true
        }
    }

    Button(
        onClick = {
            gpxPicker.launch("application/gpx+xml")
        }
    ) {
        Text("Wybierz plik z rozszerzeniem GPX")
    }

    AnimatedVisibility(
        visible = visible
    ) {
        GoogleMap(
            modifier = Modifier
                .height(256.dp)
                .padding(16.dp)
        )
    }
}