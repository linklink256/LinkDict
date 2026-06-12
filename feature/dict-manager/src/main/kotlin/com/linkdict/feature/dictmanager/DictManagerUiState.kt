package com.linkdict.feature.dictmanager

import com.linkdict.core.model.DictionaryConfig

data class DictManagerUiState(
    val dictionaries: List<DictionaryConfig> = emptyList(),
    val importName: String = "Sample MDX",
    val importPath: String = "/storage/emulated/0/Download/sample.mdx",
    val scanPath: String = "/storage/emulated/0/Download",
    val isLoading: Boolean = false,
    val message: String? = null,
)

sealed interface DictManagerAction {
    data class ImportNameChanged(val value: String) : DictManagerAction
    data class ImportPathChanged(val value: String) : DictManagerAction
    data class ScanPathChanged(val value: String) : DictManagerAction
    data object ImportClicked : DictManagerAction
    data object ScanClicked : DictManagerAction
    data class ToggleEnabled(val id: String, val enabled: Boolean) : DictManagerAction
    data class MoveUp(val id: String) : DictManagerAction
    data class MoveDown(val id: String) : DictManagerAction
    data object ClearMessage : DictManagerAction
}
