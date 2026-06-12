package com.linkdict.core.dictionary

import com.linkdict.core.model.DictionaryEntry

interface DictionaryEngine {
    val id: String
    val displayName: String

    suspend fun lookup(query: String): List<DictionaryEntry>

    suspend fun suggest(prefix: String, limit: Int): List<String>
}
