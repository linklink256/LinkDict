package com.linkdict.data.dictionary

import com.linkdict.core.dictionary.DictionaryEngine
import com.linkdict.core.model.ExternalDictionaryLink
import com.linkdict.core.model.SearchResult
import com.linkdict.domain.dictionary.DictionaryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class InMemoryDictionaryRepository(
    private val engines: List<DictionaryEngine> = listOf(InMemoryDictionaryEngine()),
) : DictionaryRepository {
    override suspend fun search(query: String): SearchResult {
        if (query.isBlank()) {
            return SearchResult(query = query, entries = emptyList())
        }

        val entries = engines.flatMap { engine -> engine.lookup(query) }
        return SearchResult(
            query = query,
            entries = entries,
            externalLinks = buildExternalLinks(query),
        )
    }

    override fun observeSuggestions(query: String): Flow<List<String>> = flow {
        if (query.isBlank()) {
            emit(emptyList())
        } else {
            emit(
                engines
                    .flatMap { engine -> engine.suggest(query, limit = 8) }
                    .distinct()
                    .take(12),
            )
        }
    }

    private fun buildExternalLinks(query: String): List<ExternalDictionaryLink> = listOf(
        ExternalDictionaryLink("Cambridge", "https://dictionary.cambridge.org/dictionary/english/$query"),
        ExternalDictionaryLink("Merriam-Webster", "https://www.merriam-webster.com/dictionary/$query"),
        ExternalDictionaryLink("Oxford Learner's", "https://www.oxfordlearnersdictionaries.com/definition/english/$query"),
    )
}
