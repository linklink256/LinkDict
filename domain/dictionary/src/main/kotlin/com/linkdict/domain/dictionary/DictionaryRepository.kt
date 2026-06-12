package com.linkdict.domain.dictionary

import com.linkdict.core.model.SearchResult
import kotlinx.coroutines.flow.Flow

interface DictionaryRepository {
    suspend fun search(query: String): SearchResult

    fun observeSuggestions(query: String): Flow<List<String>>
}
