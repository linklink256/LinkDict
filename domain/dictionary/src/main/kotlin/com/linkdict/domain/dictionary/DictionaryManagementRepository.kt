package com.linkdict.domain.dictionary

import com.linkdict.core.model.DictionaryConfig
import com.linkdict.core.model.DictionaryImportRequest
import com.linkdict.core.model.DictionaryScanResult
import kotlinx.coroutines.flow.Flow

interface DictionaryManagementRepository {
    fun observeDictionaries(): Flow<List<DictionaryConfig>>

    suspend fun scanDirectory(path: String): DictionaryScanResult

    suspend fun importDictionary(request: DictionaryImportRequest): DictionaryConfig

    suspend fun setDictionaryEnabled(id: String, enabled: Boolean)

    suspend fun reorderDictionaries(idsInOrder: List<String>)
}
