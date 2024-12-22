package org.tech.ebookcmp.book.presentation.book_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.tech.ebookcmp.book.domain.Book

@Composable
fun BookList(
    books : List<Book>,
    onBookClick : (Book) -> Unit,
    modifier : Modifier = Modifier,
    scrollState : LazyListState = rememberLazyListState()
) {
    LazyColumn(
        state = scrollState,
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            items = books,
            key = { it.id }
        ){ book->
            BookListItem(
                book = book,
                modifier = Modifier.widthIn(max = 700.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = {
                    onBookClick(book)
                }
            )
        }
    }
}