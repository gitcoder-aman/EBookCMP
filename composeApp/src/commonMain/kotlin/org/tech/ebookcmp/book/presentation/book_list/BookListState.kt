package org.tech.ebookcmp.book.presentation.book_list

import org.tech.ebookcmp.book.domain.Book
import org.tech.ebookcmp.core.presentation.UiText

data class BookListState(
    val searchQuery : String = "Harry Potter",
    val searchResults : List<Book> = emptyList(),
    val favoriteBooks : List<Book> = emptyList(),
    val isLoading : Boolean = false,
    val selectedTabIndex : Int = 0,
    val errorMessage : UiText?= null
)

