package com.linkdict.domain.dictionary

import com.linkdict.core.model.SearchResult
import kotlinx.coroutines.flow.Flow

class SearchWordUseCase(
    private val dictionaryRepository: DictionaryRepository,
) {
    suspend operator fun invoke(query: String): SearchResult {
        return dictionaryRepository.search(query.trim())
    }
}

class ObserveSearchSuggestionsUseCase(
    private val dictionaryRepository: DictionaryRepository,
) {
    operator fun invoke(query: String): Flow<List<String>> {
        return dictionaryRepository.observeSuggestions(query.trim())
    }
}
