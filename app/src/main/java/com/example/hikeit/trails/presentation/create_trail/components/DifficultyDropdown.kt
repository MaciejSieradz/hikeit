package com.example.hikeit.trails.presentation.create_trail.components

import androidx.annotation.StringRes
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import com.example.hikeit.trails.domain.Difficulty

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DifficultyDropdown(
    selectedDifficulty: String,
    isError: Boolean,
    @StringRes label: Int,
    @StringRes supportingText: Int,
    validateDifficulty: () -> Unit,
    onSelectedDifficulty: (String) -> Unit,
) {

    var difficultyExpanded by rememberSaveable { mutableStateOf(false) }

    var difficultyHasFocus by rememberSaveable { mutableStateOf(false) }

    val colors = TextFieldDefaults.colors(
        focusedContainerColor = MaterialTheme.colorScheme.background,
        unfocusedContainerColor = MaterialTheme.colorScheme.background,
        focusedTextColor = MaterialTheme.colorScheme.primary,
        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
        focusedLabelColor = MaterialTheme.colorScheme.primary,
        unfocusedLabelColor = MaterialTheme.colorScheme.onSurface,
        cursorColor = MaterialTheme.colorScheme.primary,
        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
        unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface,
        disabledContainerColor = MaterialTheme.colorScheme.background,
        disabledTextColor = MaterialTheme.colorScheme.onSurface,
        disabledLabelColor = MaterialTheme.colorScheme.onSurface,
        disabledIndicatorColor = MaterialTheme.colorScheme.onSurface
    )

    ExposedDropdownMenuBox(
        expanded = difficultyExpanded,
        onExpandedChange = { difficultyExpanded = !difficultyExpanded },
    ) {
        OutlinedTextField(
            value = selectedDifficulty,
            onValueChange = {},
            readOnly = true,
            isError = isError,
            supportingText = {
                if (isError) {
                    Text(text = stringResource(supportingText))
                }
            },
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryNotEditable, true)
                .onFocusChanged {
                    if (difficultyHasFocus && !it.isFocused) {
                        validateDifficulty()
                    }
                    difficultyHasFocus = it.isFocused
                },
            label = { Text(stringResource(label)) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = difficultyExpanded) },
            colors = colors
        )

        ExposedDropdownMenu(
            expanded = difficultyExpanded,
            onDismissRequest = { difficultyExpanded = false }
        ) {
            Difficulty.entries.forEach { difficulty ->
                DropdownMenuItem(
                    text = {
                        Text(text = difficulty.difficultyName)
                    },
                    onClick = {
                        onSelectedDifficulty(difficulty.difficultyName)
                        difficultyExpanded = false
                    })
            }
        }
    }
}