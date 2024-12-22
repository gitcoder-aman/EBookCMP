package org.tech.ebookcmp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import org.tech.ebookcmp.book.domain.Book
import org.tech.ebookcmp.book.presentation.book_list.BookListScreen
import org.tech.ebookcmp.book.presentation.book_list.BookListState
import org.tech.ebookcmp.book.presentation.book_list.components.BookSearchBar

@Preview
@Composable
fun BookSearchBarPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        BookSearchBar(
            searchQuery = "Kotlin",
            onSearchQueryChange = {},
            onImeSearch = {},
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        )
    }
}

private val books = (1..100).map {
    Book(
        id = it.toString(),
        title = "Book $it",
        authors = listOf("Aman Kumar"),
        imageUrl = "https://picsum.photos/200/300?random=$it",
        languages = emptyList(),
        averageRating = 4.6784,
        firstPublishYear = null,
        ratingCount = 5,
        numPages = 100,
        numEditions = 3,
        description = "Description of Book $it"
    )
}
@Preview
@Composable
private fun BookListScreenPreview() {
    BookListScreen(
        state = BookListState(
            searchResults = books
        ),
        onAction = {}
    )

}