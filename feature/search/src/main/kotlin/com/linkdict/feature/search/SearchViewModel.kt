package com.linkdict.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.linkdict.domain.dictionary.ObserveSearchSuggestionsUseCase
import com.linkdict.domain.dictionary.SearchWordUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchWordUseCase: SearchWordUseCase,
    private val observeSearchSuggestionsUseCase: ObserveSearchSuggestionsUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private var suggestionsJob: Job? = null

    fun onAction(action: SearchAction) {
        when (action) {
            is SearchAction.QueryChanged -> onQueryChanged(action.query)
            SearchAction.SubmitSearch -> search()
            is SearchAction.SuggestionClicked -> {
                onQueryChanged(action.suggestion)
                search()
            }
        }
    }

    private fun onQueryChanged(query: String) {
        _uiState.update { state ->
            state.copy(query = query, errorMessage = null)
        }
        observeSuggestions(query)
    }

    private fun observeSuggestions(query: String) {
        suggestionsJob?.cancel()
        if (query.isBlank()) {
            _uiState.update { it.copy(suggestions = emptyList()) }
            return
        }
        suggestionsJob = viewModelScope.launch {
            observeSearchSuggestionsUseCase(query)
                .catch { _uiState.update { state -> state.copy(suggestions = emptyList()) } }
                .collect { suggestions ->
                    _uiState.update { state -> state.copy(suggestions = suggestions) }
                }
        }
    }

    private fun search() {
        val query = uiState.value.query.trim()
        if (query.isBlank()) return

        viewModelScope.launch {
            _uiState.update { state -> state.copy(isLoading = true, errorMessage = null) }
            runCatching { searchWordUseCase(query) }
                .onSuccess { result ->
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            result = result,
                            suggestions = emptyList(),
                        )
                    }
                }
                .onFailure { throwable ->
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            errorMessage = throwable.message ?: "Search failed",
                        )
                    }
                }
        }
    }

    companion object {
        fun Factory(
            searchWordUseCase: SearchWordUseCase,
            observeSearchSuggestionsUseCase: ObserveSearchSuggestionsUseCase,
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SearchViewModel(
                    searchWordUseCase = searchWordUseCase,
                    observeSearchSuggestionsUseCase = observeSearchSuggestionsUseCase,
                )
            }
        }
    }
}
