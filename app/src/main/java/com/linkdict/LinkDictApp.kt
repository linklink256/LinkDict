package com.linkdict

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.linkdict.di.AppContainer
import com.linkdict.feature.search.SearchRoute
import com.linkdict.feature.search.SearchViewModel

@Composable
fun LinkDictApp(
    appContainer: AppContainer,
    modifier: Modifier = Modifier,
) {
    val searchViewModel: SearchViewModel = viewModel(
        factory = SearchViewModel.Factory(
            searchWordUseCase = appContainer.searchWordUseCase,
            observeSearchSuggestionsUseCase = appContainer.observeSearchSuggestionsUseCase,
        ),
    )

    Surface(modifier = modifier.fillMaxSize()) {
        SearchRoute(viewModel = searchViewModel)
    }
}
