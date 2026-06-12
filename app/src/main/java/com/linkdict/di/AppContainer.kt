package com.linkdict.di

import com.linkdict.data.dictionary.InMemoryDictionaryRepository
import com.linkdict.domain.dictionary.DictionaryRepository
import com.linkdict.domain.dictionary.ObserveSearchSuggestionsUseCase
import com.linkdict.domain.dictionary.SearchWordUseCase

class AppContainer {
    private val dictionaryRepository: DictionaryRepository = InMemoryDictionaryRepository()

    val searchWordUseCase: SearchWordUseCase = SearchWordUseCase(dictionaryRepository)
    val observeSearchSuggestionsUseCase: ObserveSearchSuggestionsUseCase =
        ObserveSearchSuggestionsUseCase(dictionaryRepository)
}
