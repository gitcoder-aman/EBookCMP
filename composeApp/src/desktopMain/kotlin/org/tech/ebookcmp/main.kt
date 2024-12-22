package org.tech.ebookcmp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.tech.ebookcmp.app.App
import org.tech.ebookcmp.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "EBookCmp",
        ) {
            App()
        }
    }
}