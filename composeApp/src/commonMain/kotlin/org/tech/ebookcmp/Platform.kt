package org.tech.ebookcmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform