package org.tech.ebookcmp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.tech.ebookcmp.book.presentation.book_list.BookListScreenRoot
import org.tech.ebookcmp.book.presentation.book_list.BookListViewModel

@Composable
@Preview
fun App() {

    BookListScreenRoot(
        viewModel = remember { BookListViewModel() },
        onBookClick = {

        }
    )
}