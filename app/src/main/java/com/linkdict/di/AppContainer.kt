package com.linkdict.di

import com.linkdict.data.dictionary.InMemoryDictionaryManagementRepository
import com.linkdict.data.dictionary.InMemoryDictionaryRepository
import com.linkdict.domain.dictionary.DictionaryManagementRepository
import com.linkdict.domain.dictionary.DictionaryRepository
import com.linkdict.domain.dictionary.ImportDictionaryUseCase
import com.linkdict.domain.dictionary.ObserveDictionariesUseCase
import com.linkdict.domain.dictionary.ObserveSearchSuggestionsUseCase
import com.linkdict.domain.dictionary.ReorderDictionariesUseCase
import com.linkdict.domain.dictionary.ScanDictionaryDirectoryUseCase
import com.linkdict.domain.dictionary.SearchWordUseCase
import com.linkdict.domain.dictionary.SetDictionaryEnabledUseCase

class AppContainer {
    private val dictionaryRepository: DictionaryRepository = InMemoryDictionaryRepository()
    private val dictionaryManagementRepository: DictionaryManagementRepository =
        InMemoryDictionaryManagementRepository()

    val searchWordUseCase: SearchWordUseCase = SearchWordUseCase(dictionaryRepository)
    val observeSearchSuggestionsUseCase: ObserveSearchSuggestionsUseCase =
        ObserveSearchSuggestionsUseCase(dictionaryRepository)

    val observeDictionariesUseCase: ObserveDictionariesUseCase =
        ObserveDictionariesUseCase(dictionaryManagementRepository)
    val scanDictionaryDirectoryUseCase: ScanDictionaryDirectoryUseCase =
        ScanDictionaryDirectoryUseCase(dictionaryManagementRepository)
    val importDictionaryUseCase: ImportDictionaryUseCase =
        ImportDictionaryUseCase(dictionaryManagementRepository)
    val setDictionaryEnabledUseCase: SetDictionaryEnabledUseCase =
        SetDictionaryEnabledUseCase(dictionaryManagementRepository)
    val reorderDictionariesUseCase: ReorderDictionariesUseCase =
        ReorderDictionariesUseCase(dictionaryManagementRepository)
}
