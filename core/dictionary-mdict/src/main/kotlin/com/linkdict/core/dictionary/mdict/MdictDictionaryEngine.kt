package com.linkdict.core.dictionary.mdict

import com.linkdict.core.dictionary.DictionaryEngine
import com.linkdict.core.model.DictionaryEntry

class MdictDictionaryEngine : DictionaryEngine {
    override val id: String = "mdict"
    override val displayName: String = "MDict"

    override suspend fun lookup(query: String): List<DictionaryEntry> {
        return emptyList()
    }

    override suspend fun suggest(prefix: String, limit: Int): List<String> {
        return emptyList()
    }
}
