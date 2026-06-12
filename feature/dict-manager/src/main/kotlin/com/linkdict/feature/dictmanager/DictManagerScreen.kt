package com.linkdict.feature.dictmanager

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.linkdict.core.model.DictionaryConfig
import com.linkdict.core.ui.EmptyState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DictManagerScreen(
    uiState: DictManagerUiState,
    onAction: (DictManagerAction) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Dictionaries") },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("Back")
                    }
                },
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                ImportCard(
                    uiState = uiState,
                    onAction = onAction,
                )
            }

            item {
                ScanCard(
                    uiState = uiState,
                    onAction = onAction,
                )
            }

            if (uiState.isLoading) {
                item { CircularProgressIndicator() }
            }

            uiState.message?.let { message ->
                item {
                    MessageCard(
                        message = message,
                        onDismiss = { onAction(DictManagerAction.ClearMessage) },
                    )
                }
            }

            item {
                Text(
                    text = "Enabled dictionaries",
                    style = MaterialTheme.typography.titleMedium,
                )
            }

            if (uiState.dictionaries.isEmpty()) {
                item {
                    EmptyState(
                        title = "No dictionaries yet",
                        description = "Import a sample path above to test the management flow.",
                    )
                }
            } else {
                itemsIndexed(uiState.dictionaries, key = { _, item -> item.id }) { index, item ->
                    DictionaryItem(
                        config = item,
                        isFirst = index == 0,
                        isLast = index == uiState.dictionaries.lastIndex,
                        onAction = onAction,
                    )
                }
            }
        }
    }
}

@Composable
private fun ImportCard(
    uiState: DictManagerUiState,
    onAction: (DictManagerAction) -> Unit,
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "Import dictionary",
                style = MaterialTheme.typography.titleMedium,
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.importName,
                onValueChange = { onAction(DictManagerAction.ImportNameChanged(it)) },
                label = { Text("Display name") },
                singleLine = true,
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.importPath,
                onValueChange = { onAction(DictManagerAction.ImportPathChanged(it)) },
                label = { Text("MDX file path") },
                singleLine = true,
            )
            Button(onClick = { onAction(DictManagerAction.ImportClicked) }) {
                Text("Import")
            }
        }
    }
}

@Composable
private fun ScanCard(
    uiState: DictManagerUiState,
    onAction: (DictManagerAction) -> Unit,
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "Scan folder",
                style = MaterialTheme.typography.titleMedium,
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.scanPath,
                onValueChange = { onAction(DictManagerAction.ScanPathChanged(it)) },
                label = { Text("Folder path") },
                singleLine = true,
            )
            Button(onClick = { onAction(DictManagerAction.ScanClicked) }) {
                Text("Scan")
            }
        }
    }
}

@Composable
private fun MessageCard(
    message: String,
    onDismiss: () -> Unit,
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = message,
            )
            TextButton(onClick = onDismiss) {
                Text("Dismiss")
            }
        }
    }
}

@Composable
private fun DictionaryItem(
    config: DictionaryConfig,
    isFirst: Boolean,
    isLast: Boolean,
    onAction: (DictManagerAction) -> Unit,
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = config.displayName,
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        text = config.filePath,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                Switch(
                    checked = config.enabled,
                    onCheckedChange = { enabled ->
                        onAction(DictManagerAction.ToggleEnabled(config.id, enabled))
                    },
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    enabled = !isFirst,
                    onClick = { onAction(DictManagerAction.MoveUp(config.id)) },
                ) {
                    Text("Up")
                }
                Button(
                    enabled = !isLast,
                    onClick = { onAction(DictManagerAction.MoveDown(config.id)) },
                ) {
                    Text("Down")
                }
            }
        }
    }
}
