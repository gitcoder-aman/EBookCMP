@file:OptIn(FlowPreview::class)

package org.tech.ebookcmp.book.presentation.book_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ebookcmp.composeapp.generated.resources.Res
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.tech.ebookcmp.book.data.repository.DefaultBookRepository
import org.tech.ebookcmp.book.domain.Book
import org.tech.ebookcmp.book.domain.BookRepository
import org.tech.ebookcmp.core.domain.onError
import org.tech.ebookcmp.core.domain.onSuccess
import org.tech.ebookcmp.core.presentation.toUiText

//Presentation -> Domain <- Data
class BookListViewModel(
    private val bookRepository: BookRepository
) : ViewModel() {

    private var cachedBooks = emptyList<Book>()
    private var searchJob: Job? = null
    private var observeFavoriteJob: Job? = null
    private val _state = MutableStateFlow(BookListState())
    val state = _state
        .onStart {
            if(cachedBooks.isEmpty()){
                observeSearchQuery()
            }
            observeFavoriteBooks()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: BookListAction) {
        when (action) {
            is BookListAction.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = action.query)
                }
            }

            is BookListAction.OnTabSelected -> {
                _state.update {
                    it.copy(selectedTabIndex = action.index)
                }
            }

            is BookListAction.OnBookClick -> {

            }
        }
    }

    private fun observeFavoriteBooks(){
        observeFavoriteJob?.cancel()
        observeFavoriteJob = bookRepository
            .getFavoriteBooks()
            .onEach { favoriteBooks->
                _state.update { it.copy(
                    favoriteBooks = favoriteBooks
                ) }
            }.launchIn(viewModelScope)
    }
    private fun observeSearchQuery() {
        state.map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->
                when {
                    query.isBlank() -> {
                        _state.update {
                            it.copy(
                                errorMessage = null,
                                searchResults = cachedBooks
                            )
                        }
                    }

                    query.length >= 2 -> {
                        searchJob?.cancel()
                        searchJob = searchBooks(query)
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun searchBooks(query: String) = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
        bookRepository.searchBooks(query)
            .onSuccess { searchResult ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = null,
                        searchResults = searchResult
                    )
                }
            }.onError { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = error.toUiText()
                    )
                }
            }
    }
}