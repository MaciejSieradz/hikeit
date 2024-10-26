package com.example.hikeit.core.presentation.util

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeDialog(
    onDismiss: () -> Unit,
    onTimeSelected: () -> Unit,
    timePickerState: TimePickerState,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = MaterialTheme.shapes.extraLarge,
            modifier = modifier
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Wybierz szacowany czas wycieczki",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                TimePicker(
                    state = timePickerState,
                    layoutType = TimePickerLayoutType.Vertical
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        modifier = Modifier.padding(end = 8.dp),
                        onClick = onDismiss
                    ) {
                        Text("Anuluj")
                    }
                    Button(
                        modifier = Modifier.padding(start = 8.dp),
                        onClick = onTimeSelected
                    ) {
                        Text("OK")
                    }
                }
            }
        }
    }
}