package com.linkdict.domain.dictionary

import com.linkdict.core.model.DictionaryConfig
import com.linkdict.core.model.DictionaryImportRequest
import com.linkdict.core.model.DictionaryScanResult
import kotlinx.coroutines.flow.Flow

class ObserveDictionariesUseCase(
    private val repository: DictionaryManagementRepository,
) {
    operator fun invoke(): Flow<List<DictionaryConfig>> = repository.observeDictionaries()
}

class ScanDictionaryDirectoryUseCase(
    private val repository: DictionaryManagementRepository,
) {
    suspend operator fun invoke(path: String): DictionaryScanResult = repository.scanDirectory(path)
}

class ImportDictionaryUseCase(
    private val repository: DictionaryManagementRepository,
) {
    suspend operator fun invoke(request: DictionaryImportRequest): DictionaryConfig =
        repository.importDictionary(request)
}

class SetDictionaryEnabledUseCase(
    private val repository: DictionaryManagementRepository,
) {
    suspend operator fun invoke(id: String, enabled: Boolean) {
        repository.setDictionaryEnabled(id, enabled)
    }
}

class ReorderDictionariesUseCase(
    private val repository: DictionaryManagementRepository,
) {
    suspend operator fun invoke(idsInOrder: List<String>) {
        repository.reorderDictionaries(idsInOrder)
    }
}
