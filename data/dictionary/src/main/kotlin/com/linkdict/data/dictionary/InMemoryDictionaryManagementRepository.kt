package com.linkdict.data.dictionary

import com.linkdict.core.model.DictionaryConfig
import com.linkdict.core.model.DictionaryImportRequest
import com.linkdict.core.model.DictionaryScanResult
import com.linkdict.core.model.DictionarySourceType
import com.linkdict.domain.dictionary.DictionaryManagementRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InMemoryDictionaryManagementRepository : DictionaryManagementRepository {
    private val dictionaries = MutableStateFlow<List<DictionaryConfig>>(emptyList())

    override fun observeDictionaries(): Flow<List<DictionaryConfig>> = dictionaries.asStateFlow()

    override suspend fun scanDirectory(path: String): DictionaryScanResult {
        return DictionaryScanResult(rootPath = path, candidates = emptyList())
    }

    override suspend fun importDictionary(request: DictionaryImportRequest): DictionaryConfig {
        val config = DictionaryConfig(
            id = request.filePath,
            displayName = request.displayName,
            filePath = request.filePath,
            sourceType = DictionarySourceType.ImportedFile,
            order = dictionaries.value.size,
        )
        dictionaries.update { current ->
            (current.filterNot { it.id == config.id } + config).sortedBy { it.order }
        }
        return config
    }

    override suspend fun setDictionaryEnabled(id: String, enabled: Boolean) {
        dictionaries.update { current ->
            current.map { config ->
                if (config.id == id) config.copy(enabled = enabled) else config
            }
        }
    }

    override suspend fun reorderDictionaries(idsInOrder: List<String>) {
        val orderMap = idsInOrder.withIndex().associate { it.value to it.index }
        dictionaries.update { current ->
            current
                .map { config -> config.copy(order = orderMap[config.id] ?: config.order) }
                .sortedBy { it.order }
        }
    }
}
