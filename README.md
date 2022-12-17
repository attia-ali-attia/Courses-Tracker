![](media/FoodiumHeader.png)
# Courses-Tracker

[![GitHub license](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

**Courses Tracker** is an Android application 📱 built to help instructors🧑‍🏫 create & track all of thier courses - progress, students, how much they paid, and many more… 

***You can Install Courses Tracker app from below 👇***

[![Courses Tracker App](https://www.svgrepo.com/show/303139/google-play-badge-logo.svg)](https://github.com/PatilShreyas/Foodium/releases/latest/download/app.apk)


## Built With 🛠
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more..
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) - A cold asynchronous data stream that sequentially emits values and completes normally or with an exception.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes. 
  - [Room](https://developer.android.com/topic/libraries/architecture/room) - SQLite object mapping library.
- [Jetpack Compose](https://developer.android.com/jetpack/compose) - Android’s recommended modern toolkit for building native UI.
    - [Compose Destinations](https://github.com/raamcosta/compose-destinations) - A KSP library that processes annotations and generates code that uses Official Jetpack Compose Navigation under the hood. It hides the complex, non-type-safe and boilerplate code you would have to write otherwise.
- [Dependency Injection](https://developer.android.com/training/dependency-injection) - 
  - [Hilt-Dagger](https://dagger.dev/hilt/) - Standard library to incorporate Dagger dependency injection into an Android application.
  - [Hilt-ViewModel](https://developer.android.com/training/dependency-injection/hilt-jetpack) - DI for injecting `ViewModel`.
- [Material3](https://m3.material.io/) - The latest version of Google’s open-source design system..
- [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html) - For writing Gradle build scripts using Kotlin.


# Package Structure
    
    com.courses.tracker    # Root Package
    .
    ├── data                # For data handling.
    │   ├── local           # Local Persistence Database. Room (SQLite) database
    |   │   └── entity      # Data Access Object for Room   
    |   ├── mapper          # Convert domain model to Room Dao and vise versa
    │   └── repository      # Single source of data.
    ├── di                  # Dependency Injection 
    │
    ├── domain
    |   ├── model           # Model classes
    |   └── repository      # Interface define methods to be implemented in data layer.     
    |
    ├── presentation
    │   ├── course_listings  # Classes related to courses screen
    │   └── student_infos    # Classes related to students screen
    │
    ├── ui/theme                  # Everything about Compose theme
    |
    └── utils               # Utility Classes / Kotlin extensions
    
## Architecture
This app uses [***MVI (Model View Intent)***](https://developer.android.com/topic/architecture) architecture.

![](https://developer.android.com/static/topic/libraries/architecture/images/mad-arch-overview-data.png?width=150)
