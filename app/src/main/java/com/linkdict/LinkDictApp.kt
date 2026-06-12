package com.linkdict

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.linkdict.di.AppContainer
import com.linkdict.feature.dictmanager.DictManagerRoute
import com.linkdict.feature.dictmanager.DictManagerViewModel
import com.linkdict.feature.search.SearchRoute
import com.linkdict.feature.search.SearchViewModel

private enum class LinkDictScreen {
    Search,
    DictionaryManager,
}

@Composable
fun LinkDictApp(
    appContainer: AppContainer,
    modifier: Modifier = Modifier,
) {
    var currentScreen by rememberSaveable { mutableStateOf(LinkDictScreen.Search) }

    val searchViewModel: SearchViewModel = viewModel(
        factory = SearchViewModel.Factory(
            searchWordUseCase = appContainer.searchWordUseCase,
            observeSearchSuggestionsUseCase = appContainer.observeSearchSuggestionsUseCase,
        ),
    )
    val dictManagerViewModel: DictManagerViewModel = viewModel(
        factory = DictManagerViewModel.Factory(
            observeDictionariesUseCase = appContainer.observeDictionariesUseCase,
            scanDictionaryDirectoryUseCase = appContainer.scanDictionaryDirectoryUseCase,
            importDictionaryUseCase = appContainer.importDictionaryUseCase,
            setDictionaryEnabledUseCase = appContainer.setDictionaryEnabledUseCase,
            reorderDictionariesUseCase = appContainer.reorderDictionariesUseCase,
        ),
    )

    Surface(modifier = modifier.fillMaxSize()) {
        when (currentScreen) {
            LinkDictScreen.Search -> SearchRoute(
                viewModel = searchViewModel,
                onOpenDictionaryManager = { currentScreen = LinkDictScreen.DictionaryManager },
            )

            LinkDictScreen.DictionaryManager -> DictManagerRoute(
                viewModel = dictManagerViewModel,
                onBack = { currentScreen = LinkDictScreen.Search },
            )
        }
    }
}
