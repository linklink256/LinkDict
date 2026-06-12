package com.linkdict.feature.dictmanager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.linkdict.core.model.DictionaryImportRequest
import com.linkdict.domain.dictionary.ImportDictionaryUseCase
import com.linkdict.domain.dictionary.ObserveDictionariesUseCase
import com.linkdict.domain.dictionary.ReorderDictionariesUseCase
import com.linkdict.domain.dictionary.ScanDictionaryDirectoryUseCase
import com.linkdict.domain.dictionary.SetDictionaryEnabledUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DictManagerViewModel(
    private val observeDictionariesUseCase: ObserveDictionariesUseCase,
    private val scanDictionaryDirectoryUseCase: ScanDictionaryDirectoryUseCase,
    private val importDictionaryUseCase: ImportDictionaryUseCase,
    private val setDictionaryEnabledUseCase: SetDictionaryEnabledUseCase,
    private val reorderDictionariesUseCase: ReorderDictionariesUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(DictManagerUiState())
    val uiState: StateFlow<DictManagerUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            observeDictionariesUseCase().collect { dictionaries ->
                _uiState.update { state -> state.copy(dictionaries = dictionaries) }
            }
        }
    }

    fun onAction(action: DictManagerAction) {
        when (action) {
            is DictManagerAction.ImportNameChanged -> _uiState.update { it.copy(importName = action.value) }
            is DictManagerAction.ImportPathChanged -> _uiState.update { it.copy(importPath = action.value) }
            is DictManagerAction.ScanPathChanged -> _uiState.update { it.copy(scanPath = action.value) }
            DictManagerAction.ImportClicked -> importDictionary()
            DictManagerAction.ScanClicked -> scanDirectory()
            is DictManagerAction.ToggleEnabled -> setEnabled(action.id, action.enabled)
            is DictManagerAction.MoveUp -> move(action.id, offset = -1)
            is DictManagerAction.MoveDown -> move(action.id, offset = 1)
            DictManagerAction.ClearMessage -> _uiState.update { it.copy(message = null) }
        }
    }

    private fun importDictionary() {
        val state = uiState.value
        if (state.importName.isBlank() || state.importPath.isBlank()) {
            _uiState.update { it.copy(message = "Name and path are required") }
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, message = null) }
            runCatching {
                importDictionaryUseCase(
                    DictionaryImportRequest(
                        displayName = state.importName.trim(),
                        filePath = state.importPath.trim(),
                    ),
                )
            }.onSuccess { config ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        message = "Imported ${config.displayName}",
                    )
                }
            }.onFailure { throwable ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        message = throwable.message ?: "Import failed",
                    )
                }
            }
        }
    }

    private fun scanDirectory() {
        val path = uiState.value.scanPath.trim()
        if (path.isBlank()) {
            _uiState.update { it.copy(message = "Scan path is required") }
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, message = null) }
            runCatching { scanDictionaryDirectoryUseCase(path) }
                .onSuccess { result ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            message = "Found ${result.candidates.size} candidate(s)",
                        )
                    }
                }
                .onFailure { throwable ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            message = throwable.message ?: "Scan failed",
                        )
                    }
                }
        }
    }

    private fun setEnabled(id: String, enabled: Boolean) {
        viewModelScope.launch {
            setDictionaryEnabledUseCase(id, enabled)
        }
    }

    private fun move(id: String, offset: Int) {
        val dictionaries = uiState.value.dictionaries
        val fromIndex = dictionaries.indexOfFirst { it.id == id }
        val toIndex = fromIndex + offset
        if (fromIndex !in dictionaries.indices || toIndex !in dictionaries.indices) return

        val reordered = dictionaries.toMutableList().apply {
            add(toIndex, removeAt(fromIndex))
        }
        viewModelScope.launch {
            reorderDictionariesUseCase(reordered.map { it.id })
        }
    }

    companion object {
        fun Factory(
            observeDictionariesUseCase: ObserveDictionariesUseCase,
            scanDictionaryDirectoryUseCase: ScanDictionaryDirectoryUseCase,
            importDictionaryUseCase: ImportDictionaryUseCase,
            setDictionaryEnabledUseCase: SetDictionaryEnabledUseCase,
            reorderDictionariesUseCase: ReorderDictionariesUseCase,
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                DictManagerViewModel(
                    observeDictionariesUseCase = observeDictionariesUseCase,
                    scanDictionaryDirectoryUseCase = scanDictionaryDirectoryUseCase,
                    importDictionaryUseCase = importDictionaryUseCase,
                    setDictionaryEnabledUseCase = setDictionaryEnabledUseCase,
                    reorderDictionariesUseCase = reorderDictionariesUseCase,
                )
            }
        }
    }
}
