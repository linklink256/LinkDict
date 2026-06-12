package com.linkdict.data.dictionary

import com.linkdict.core.dictionary.DictionaryEngine
import com.linkdict.core.model.DictionaryEntry

class InMemoryDictionaryEngine : DictionaryEngine {
    override val id: String = "sample"
    override val displayName: String = "Sample Dictionary"

    private val entries = listOf(
        DictionaryEntry(
            headword = "link",
            phonetic = "/lɪŋk/",
            definition = "A relationship or connection between things. In LinkDict, it means connecting words, dictionaries, and learning.",
            sourceName = displayName,
        ),
        DictionaryEntry(
            headword = "dictionary",
            phonetic = "/ˈdɪkʃəneri/",
            definition = "A reference source that explains words, pronunciations, and meanings.",
            sourceName = displayName,
        ),
        DictionaryEntry(
            headword = "compose",
            phonetic = "/kəmˈpoʊz/",
            definition = "A modern declarative UI toolkit for Android.",
            sourceName = displayName,
        ),
    )

    override suspend fun lookup(query: String): List<DictionaryEntry> {
        if (query.isBlank()) return emptyList()
        return entries.filter { entry ->
            entry.headword.equals(query, ignoreCase = true) ||
                entry.headword.contains(query, ignoreCase = true)
        }
    }

    override suspend fun suggest(prefix: String, limit: Int): List<String> {
        if (prefix.isBlank()) return emptyList()
        return entries
            .asSequence()
            .map { it.headword }
            .filter { it.startsWith(prefix, ignoreCase = true) }
            .take(limit)
            .toList()
    }
}
