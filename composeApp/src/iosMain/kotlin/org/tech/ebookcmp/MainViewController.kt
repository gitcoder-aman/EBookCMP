package org.tech.ebookcmp

import androidx.compose.ui.window.ComposeUIViewController
import org.tech.ebookcmp.app.App
import org.tech.ebookcmp.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }