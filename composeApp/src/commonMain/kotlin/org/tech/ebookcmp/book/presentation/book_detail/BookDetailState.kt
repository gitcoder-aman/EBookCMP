package org.tech.ebookcmp.book.presentation.book_detail

import org.tech.ebookcmp.book.domain.Book

data class BookDetailState(
    val isLoading : Boolean = true,
    val isFavorite : Boolean = false,
    val book : Book ?= null
)
