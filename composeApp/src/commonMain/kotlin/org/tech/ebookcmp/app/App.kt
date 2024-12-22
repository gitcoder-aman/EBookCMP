package org.tech.ebookcmp.app

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.tech.ebookcmp.book.presentation.SelectedBookViewModel
import org.tech.ebookcmp.book.presentation.book_detail.BookDetailAction
import org.tech.ebookcmp.book.presentation.book_detail.BookDetailScreenRoot
import org.tech.ebookcmp.book.presentation.book_detail.BookDetailViewModel
import org.tech.ebookcmp.book.presentation.book_list.BookListScreenRoot
import org.tech.ebookcmp.book.presentation.book_list.BookListViewModel

@Composable
@Preview
fun App() {

    MaterialTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Routes.BookGraph
        ) {
            navigation<Routes.BookGraph>(
                startDestination = Routes.BookList
            ) {
                composable<Routes.BookList>(
                    exitTransition = { slideOutHorizontally() },
                    popEnterTransition = { slideInHorizontally() }
                ) {
                    val viewModel = koinViewModel<BookListViewModel>()
                    val selectedBookViewModel =
                        it.sharedKoinViewModel<SelectedBookViewModel>(navController)

                    LaunchedEffect(true) {
                        selectedBookViewModel.onSelectBook(null)
                    }
                    BookListScreenRoot(
                        viewModel = viewModel,
                        onBookClick = { book ->
                            selectedBookViewModel.onSelectBook(book)
                            navController.navigate(Routes.BookDetail(book.id))
                        }
                    )
                }
                composable<Routes.BookDetail>(
                    enterTransition = {
                        slideInHorizontally { initialOffset ->
                            initialOffset
                        }
                    },
                    exitTransition = {
                        slideOutHorizontally { initialOffset ->
                            initialOffset
                        }
                    }
                ) {
                    val selectedBookViewModel =
                        it.sharedKoinViewModel<SelectedBookViewModel>(navController)
                    val selectedBook by
                    selectedBookViewModel.selectedBook.collectAsStateWithLifecycle()
                    val viewModel = koinViewModel<BookDetailViewModel>()

                    LaunchedEffect(selectedBook) {
                        selectedBook?.let {
                            viewModel.onAction(BookDetailAction.OnSelectedBookChange(selectedBook!!))
                        }
                    }
                    BookDetailScreenRoot(
                        viewModel = viewModel,
                        onBackClick = {
                            navController.navigateUp()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private inline fun <reified T : ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavController
): T {
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return koinViewModel(
        viewModelStoreOwner = parentEntry
    )
}