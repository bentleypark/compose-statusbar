# Compose StatusBar

A Kotlin Multiplatform library for managing status bar appearance in Compose applications. Supports both Android and iOS platforms.

[![Maven Central](https://img.shields.io/maven-central/v/io.github.bentleypark/compose-statusbar.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/io.github.bentleypark/compose-statusbar)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## Features

- Easy status bar color configuration
- Platform-specific implementations (Android & iOS)
- Compose Multiplatform support
- Safe area handling

## Installation

Add the dependency to your project:

```kotlin
dependencies {
    implementation("io.github.bentleypark:compose-statusbar:1.0.1")
}
```

## Usage

Basic usage example:

```kotlin
@Composable
fun MyScreen() {
    ConfigureStatusBar(
        color = Color.Green,
        onDispose = { /* Optional cleanup */ }
    )

    // Your screen content
}
```

## Sample App

Check out the sample app in the `sample` directory for comprehensive examples demonstrating various features and implementation patterns.

## License

MIT License

Copyright (c) 2024 Bentley Park

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.