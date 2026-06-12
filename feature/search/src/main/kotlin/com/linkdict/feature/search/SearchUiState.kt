package com.linkdict.feature.search

import com.linkdict.core.model.SearchResult

data class SearchUiState(
    val query: String = "",
    val isLoading: Boolean = false,
    val result: SearchResult? = null,
    val suggestions: List<String> = emptyList(),
    val errorMessage: String? = null,
)

sealed interface SearchAction {
    data class QueryChanged(val query: String) : SearchAction
    data object SubmitSearch : SearchAction
    data class SuggestionClicked(val suggestion: String) : SearchAction
}
