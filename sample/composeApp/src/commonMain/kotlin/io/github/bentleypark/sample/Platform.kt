package io.github.bentleypark.sample

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform