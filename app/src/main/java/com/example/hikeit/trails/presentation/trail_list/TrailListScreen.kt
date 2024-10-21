package com.example.hikeit.trails.presentation.trail_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hikeit.trails.presentation.models.TrailUi
import com.example.hikeit.trails.presentation.trail_list.components.TrailCard
import com.example.hikeit.core.presentation.util.LoadingWheel
import com.example.hikeit.ui.theme.HikeItTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun TrailListRoute(
    viewModel: TrailListViewModel = koinViewModel<TrailListViewModel>(),
    onTrailClick: (String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    TrailListScreen(state, onTrailClick)
}

@Composable
fun TrailListScreen(
    uiState: TrailListState,
    navigateToTrail: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .semantics { contentDescription = "Search Screen" },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (uiState.isLoading) {
            LoadingWheel(
                modifier = modifier,
                contentDesc = "Loading data"
            )
        } else {
            TrailSearchBar()

            LazyColumn(
                modifier = modifier
                    .padding(horizontal = 16.dp)
            ) {
                items(items = uiState.trails, key = { photo -> photo.title }) { photo ->
                    TrailCard(
                        trailUi = photo,
                        modifier =
                        Modifier
                            .padding(vertical = 8.dp)
                            .semantics { contentDescription = "TrailCard" },
                        navigateToTrail
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrailSearchBar(modifier: Modifier = Modifier) {

    var text by rememberSaveable {
        mutableStateOf("")
    }

    var active by rememberSaveable {
        mutableStateOf(false)
    }

    val countriesList = listOf("Polska", "Czechy", "Niemcy")

    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = text,
                onQueryChange = { text = it },
                onSearch = {
                    active = false
                },
                expanded = active,
                onExpandedChange = { active = it },
                enabled = true,
                placeholder = { Text(text = "Wyszukaj Szlaki") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "search"
                    )
                },
                trailingIcon = null,
                interactionSource = null,
            )
        },
        expanded = active,
        onExpandedChange = { active = it },
        modifier = modifier,
        shape = SearchBarDefaults.inputFieldShape,
        tonalElevation = SearchBarDefaults.TonalElevation,
        shadowElevation = SearchBarDefaults.ShadowElevation,
        windowInsets = SearchBarDefaults.windowInsets,
        content = {
            LazyColumn {
                items(countriesList) { country ->
                    Text(
                        text = country,
                        modifier = Modifier.padding(
                            start = 8.dp,
                            top = 4.dp,
                            end = 8.dp,
                            bottom = 4.dp
                        )
                    )
                }
            }
        }
    )
}

@PreviewLightDark
@Composable
private fun TrailListScreenPreview() {
    HikeItTheme {
        TrailListScreen(
            uiState = TrailListState(
                isLoading = false,
                trails = previewTrailUis
            ),
            navigateToTrail = {},
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
        )
    }
}

@PreviewLightDark
@Composable
fun TrailSearchBarPreview() {
    HikeItTheme {
        TrailSearchBar()
    }
}

internal val previewTrailUis = listOf(
    TrailUi(
        "",
        "https://tatromaniak.pl/wp-content/uploads/2015/07/szpiglasowy_rafal_ociepka_360_kopia2.jpg",
        "Szpiglasowy Wierch do Doliny Pięciu Stawów",
        "Tatry",
        "4.8 - Zaawansowany - 24.0km - Szac. 8 godz. 14 min"
    ),
    TrailUi(
        "",
        "https://www.e-wczasy.pl/blog/wp-content/uploads/2020/10/szlak-tatry.jpg",
        "Wołowiec od Zawoi",
        "Tatry Zachodnie",
        "5.0 - Umiarkowany - 12.0km - Szac. 5 godz. 3 min"
    ),
    TrailUi(
        "",
        "https://www.e-horyzont.pl/media/mageplaza/blog/post/r/y/rysy-01.jpg",
        "Rysy od polskiej strony",
        "Tatry",
        "4.8 - Zaawansowany - 18.0km - Szac. 6 godz. 40 min"
    )
)