# LinkDict

LinkDict is a new dictionary app built from scratch with a clean, modular Android architecture.

## Architecture

```text
:app
:core:common
:core:model
:core:ui
:core:designsystem
:core:database
:core:datastore
:core:network
:core:dictionary-api
:core:dictionary-mdict
:domain:dictionary
:data:dictionary
:feature:search
```

Dependency direction:

```text
app -> feature -> domain -> data -> core
core:dictionary-mdict -> core:dictionary-api
```

## Initial Goals

- Jetpack Compose UI
- StateFlow based screen state
- Repository and use-case boundaries
- Dictionary engine abstraction
- Room/DataStore/Network modules prepared for future work
- MDX/MDict support isolated behind `DictionaryEngine`
