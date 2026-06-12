# LinkDict Architecture

LinkDict is designed as a feature-first, modular Android app. The main rule is that UI modules do not depend on storage, network, or MDX implementation details.

## Dependency Direction

```text
app -> feature -> domain -> data -> core
core:dictionary-mdict -> core:dictionary-api
```

## Layers

### App

The `app` module wires dependencies and hosts navigation. It should not contain dictionary parsing or persistence logic.

### Feature

Feature modules own screens, ViewModels, UI state, user actions, and one-off UI effects.

Current feature modules:

- `feature:search`

Planned feature modules:

- `feature:dict-manager`
- `feature:wordbook`
- `feature:quick-search`
- `feature:settings`

### Domain

Domain modules define app-specific contracts and use cases. They depend on models, not implementations.

Current domain modules:

- `domain:dictionary`

### Data

Data modules implement domain repositories. They coordinate local storage, settings, network, and dictionary engines.

Current data modules:

- `data:dictionary`

### Core

Core modules contain reusable primitives and platform wrappers.

- `core:model`: shared domain models
- `core:dictionary-api`: dictionary engine contract
- `core:dictionary-mdict`: future MDict adapter
- `core:database`: Room/SQLCipher infrastructure
- `core:datastore`: DataStore preferences
- `core:network`: OkHttp setup
- `core:designsystem`: Material 3 theme
- `core:ui`: reusable Compose widgets

## Dictionary Engine Boundary

Dictionary lookup must go through `DictionaryEngine`. MDX/MDict types should not leak into feature or domain modules.

```kotlin
interface DictionaryEngine {
    suspend fun lookup(query: String): List<DictionaryEntry>
    suspend fun suggest(prefix: String, limit: Int): List<String>
}
```

## State Model

Each screen should expose a single immutable `UiState` via `StateFlow`, and receive user input as actions.

```text
Screen -> Action -> ViewModel -> UseCase -> Repository -> Engine/DataSource
```
