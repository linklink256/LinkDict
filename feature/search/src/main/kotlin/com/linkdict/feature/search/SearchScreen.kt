package com.linkdict.feature.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.linkdict.core.ui.EmptyState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    uiState: SearchUiState,
    onAction: (SearchAction) -> Unit,
    onOpenDictionaryManager: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("LinkDict") },
                actions = {
                    TextButton(onClick = onOpenDictionaryManager) {
                        Text("Dictionaries")
                    }
                },
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = uiState.query,
                    onValueChange = { onAction(SearchAction.QueryChanged(it)) },
                    label = { Text("Search words") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = { onAction(SearchAction.SubmitSearch) },
                    ),
                )
                Button(onClick = { onAction(SearchAction.SubmitSearch) }) {
                    Text("Search")
                }
            }

            if (uiState.isLoading) {
                CircularProgressIndicator()
            }

            uiState.errorMessage?.let { message ->
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            if (uiState.suggestions.isNotEmpty()) {
                SuggestionList(
                    suggestions = uiState.suggestions,
                    onSuggestionClick = { onAction(SearchAction.SuggestionClicked(it)) },
                )
            }

            val result = uiState.result
            when {
                result == null -> EmptyState(
                    title = "Start with a word",
                    description = "Try searching for link, dictionary, or compose.",
                )

                result.entries.isEmpty() -> EmptyState(
                    title = "No local result",
                    description = "Dictionary engines and MDX import will be added next.",
                )

                else -> LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(result.entries) { entry ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = entry.headword,
                                    style = MaterialTheme.typography.titleLarge,
                                )
                                entry.phonetic?.let {
                                    Text(
                                        text = it,
                                        color = MaterialTheme.colorScheme.primary,
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(text = entry.definition)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = entry.sourceName,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SuggestionList(
    suggestions: List<String>,
    onSuggestionClick: (String) -> Unit,
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            suggestions.forEach { suggestion ->
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSuggestionClick(suggestion) }
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    text = suggestion,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}
