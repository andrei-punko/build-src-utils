# Separate build configuration from the project

[![Java CI with Gradle](https://github.com/andrei-punko/build-src-utils/actions/workflows/gradle.yml/badge.svg)](https://github.com/andrei-punko/build-src-utils/actions/workflows/gradle.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

Externalization of build configuration by put dependencies and plugins common params into separate Git repository and
reuse it after that (via Git submodule) to avoid without writing repeatable code.

Common name of this approach is `buildSrc`

See example of usage [here](https://github.com/andrei-punko/composite-build-usage-example)

## Prerequisites

- JDK 21
- Gradle (embedded)
