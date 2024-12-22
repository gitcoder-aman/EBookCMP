package org.tech.ebookcmp.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.core.module.single
import org.koin.dsl.module
import org.tech.ebookcmp.book.data.database.DatabaseFactory

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { Darwin.create() }
        single{DatabaseFactory()}
    }