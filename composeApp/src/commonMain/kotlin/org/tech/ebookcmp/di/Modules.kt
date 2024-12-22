package org.tech.ebookcmp.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.tech.ebookcmp.book.data.database.DatabaseFactory
import org.tech.ebookcmp.book.data.database.FavoriteBookDatabase
import org.tech.ebookcmp.book.data.network.KtorRemoteBookDataSource
import org.tech.ebookcmp.book.data.network.RemoteBookDataSource
import org.tech.ebookcmp.book.data.repository.DefaultBookRepository
import org.tech.ebookcmp.book.domain.BookRepository
import org.tech.ebookcmp.book.presentation.SelectedBookViewModel
import org.tech.ebookcmp.book.presentation.book_detail.BookDetailViewModel
import org.tech.ebookcmp.book.presentation.book_list.BookListViewModel
import org.tech.ebookcmp.core.data.HttpClientFactory

expect val platformModule : Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()
    singleOf(::DefaultBookRepository).bind<BookRepository>()

    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single { get<FavoriteBookDatabase>().favoriteBookDao }

    viewModelOf(::BookListViewModel)
    viewModelOf(::BookDetailViewModel)
    viewModelOf(::SelectedBookViewModel)
}