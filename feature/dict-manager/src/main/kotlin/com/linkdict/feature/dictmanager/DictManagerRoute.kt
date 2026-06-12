package com.linkdict.feature.dictmanager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun DictManagerRoute(
    viewModel: DictManagerViewModel,
    onBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DictManagerScreen(
        uiState = uiState,
        onAction = viewModel::onAction,
        onBack = onBack,
    )
}
