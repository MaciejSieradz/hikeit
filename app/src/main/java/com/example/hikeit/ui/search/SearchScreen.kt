package com.example.hikeit.ui.search

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.hikeit.ui.theme.HikeItTheme

@Composable
fun SearchScreen() {
    Text(text = "This is search screen")
}

@Composable
@Preview
fun SearchScreenPreview() {
    HikeItTheme {
        SearchScreen()
    }
}