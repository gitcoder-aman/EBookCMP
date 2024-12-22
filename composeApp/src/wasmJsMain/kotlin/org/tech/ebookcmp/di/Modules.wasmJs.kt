package org.tech.ebookcmp.di

import io.ktor.client.engine.HttpClientEngine
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single { io.ktor.client.engine.js.Js.create() }
    }