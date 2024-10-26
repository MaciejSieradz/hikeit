package com.example.hikeit.trails.presentation.create_trail

import android.icu.util.Calendar
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hikeit.R
import com.example.hikeit.trails.presentation.create_trail.components.AddGpxFile
import com.example.hikeit.trails.presentation.create_trail.components.CreateTrailForm
import com.example.hikeit.trails.presentation.create_trail.components.ImagesRow
import com.example.hikeit.ui.theme.HikeItTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateTrailRoute(
    viewModel: CreateTrailViewModel = koinViewModel(),
    onAction: (CreateTrailAction) -> Unit,
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    CreateTrailScreen(state = uiState, onAction = onAction)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTrailScreen(
    state: CreateTrailState,
    onAction: (CreateTrailAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val currentTime = Calendar.getInstance()

    var gpx by rememberSaveable {
        mutableStateOf<Uri?>(null)
    }

    var photos by rememberSaveable {
        mutableStateOf<List<Uri?>>(emptyList())
    }

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .windowInsetsPadding(WindowInsets.statusBars)
            .verticalScroll(state = rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.create_new_trail),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        CreateTrailForm(
            createTrailState = state,
            onTitleChange = {
                onAction(CreateTrailAction.ValidateTitle(it))
                onAction(CreateTrailAction.TitleChanged(it))
            },
            onSelectedDifficulty = {
                onAction(CreateTrailAction.ValidateDifficulty(it))
                onAction(CreateTrailAction.DifficultyChanged(it))
            },
            onDescriptionChange = { onAction(CreateTrailAction.DescriptionChanged(it)) },
            onTimeSelected = {
                val time = "${timePickerState.hour} godz. ${timePickerState.minute} min."
                onAction(CreateTrailAction.EstimatedTimeChanged(time))
                onAction(CreateTrailAction.ValidateEstimatedTime(time))
            },
            validateTitle = { onAction(CreateTrailAction.ValidateTitle(state.title)) },
            validateDifficulty = { onAction(CreateTrailAction.ValidateDifficulty(state.difficulty)) },
            validateEstimatedTime = { onAction(CreateTrailAction.ValidateEstimatedTime(state.estimatedTime)) },
            validateDescription = { onAction(CreateTrailAction.ValidateDescription(state.description)) },
            timePickerState = timePickerState,
        )

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        AddGpxFile(
            onGpxSelected = { gpx = it }
        )

        Spacer(modifier = Modifier.padding(vertical = 8.dp))


        ImagesRow(
            photos = photos,
            onImagesSelected = { photos = it },
            onDiscardImage = { photos = photos.minus(it) }
        )

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Stwórz nową wycieczkę",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun CreateTrailScreenPreview() {
    HikeItTheme {
        CreateTrailScreen(
            state = CreateTrailState(),
            onAction = {},
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        )
    }
}