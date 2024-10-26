package com.example.hikeit.core.presentation.util

import androidx.annotation.StringRes
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow

@Composable
fun ClickableTextField(
    value: String,
    isError: Boolean,
    @StringRes label: Int,
    @StringRes supportingText: Int,
    onClick: () -> Unit,
) {

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

    OutlinedTextField(
        value = value,
        isError = isError,
        onValueChange = {},
        supportingText = {
            if (isError) {
                Text(text = stringResource(supportingText))
            }
        },
        readOnly = true,
        label = { Text(stringResource(label)) },
        colors = colors,
        interactionSource = remember {
            object : MutableInteractionSource {
                override val interactions = MutableSharedFlow<Interaction>(
                    extraBufferCapacity = 16,
                    onBufferOverflow = BufferOverflow.DROP_OLDEST,
                )

                override suspend fun emit(interaction: Interaction) {
                    if (interaction is PressInteraction.Release) {
                        onClick()
                    }

                    interactions.emit(interaction)
                }

                override fun tryEmit(interaction: Interaction): Boolean {
                    return interactions.tryEmit(interaction)
                }
            }
        }
    )
}