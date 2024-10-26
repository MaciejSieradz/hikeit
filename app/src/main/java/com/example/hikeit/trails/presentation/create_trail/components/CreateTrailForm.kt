package com.example.hikeit.trails.presentation.create_trail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hikeit.R
import com.example.hikeit.core.presentation.util.ClickableTextField
import com.example.hikeit.core.presentation.util.TimeDialog
import com.example.hikeit.core.presentation.util.ValidatedOutlinedTextField
import com.example.hikeit.trails.presentation.create_trail.CreateTrailState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTrailForm(
    createTrailState: CreateTrailState,
    timePickerState: TimePickerState,
    onTitleChange: (String) -> Unit,
    onSelectedDifficulty: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onTimeSelected: () -> Unit,
    validateTitle: () -> Unit,
    validateDifficulty: () -> Unit,
    validateEstimatedTime: () -> Unit,
    validateDescription: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
    ) {
        ValidatedOutlinedTextField(
            value = createTrailState.title,
            onValueChange = onTitleChange,
            label = R.string.trail_form_title,
            supportingText = R.string.required,
            isError = createTrailState.titleError,
            validate = validateTitle
        )

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        DifficultyDropdown(
            selectedDifficulty = createTrailState.difficulty,
            isError = createTrailState.difficultyError,
            label = R.string.trail_form_difficulty,
            supportingText = R.string.required,
            validateDifficulty = validateDifficulty,
            onSelectedDifficulty = onSelectedDifficulty
        )

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        ClickableTextField(
            value = createTrailState.estimatedTime,
            isError = createTrailState.estimatedTimeError,
            label = R.string.trail_form_time,
            supportingText = R.string.required,
            onClick = { showDialog = true }
        )

        if (showDialog) {
            TimeDialog(
                onDismiss = {
                    validateEstimatedTime()
                    showDialog = false
                },
                timePickerState = timePickerState,
                onTimeSelected = {
                    onTimeSelected()
                    showDialog = false
                }
            )
        }

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        ValidatedOutlinedTextField(
            value = createTrailState.description,
            onValueChange = onDescriptionChange,
            label = R.string.trail_form_description,
            supportingText = R.string.required,
            isError = createTrailState.descriptionError,
            validate = validateDescription,
            minLines = 5
        )
    }
}
