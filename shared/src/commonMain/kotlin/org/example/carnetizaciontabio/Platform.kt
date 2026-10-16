package org.example.carnetizaciontabio

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform