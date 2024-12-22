package org.tech.ebookcmp.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module
import org.tech.ebookcmp.book.data.database.DatabaseFactory

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }
        single{ DatabaseFactory(androidApplication()) }
    }