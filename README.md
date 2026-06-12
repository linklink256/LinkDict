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
:feature:dict-manager
```

Dependency direction:

```text
app -> feature -> domain -> data -> core
core:dictionary-mdict -> core:dictionary-api
```

## Current Features

- Jetpack Compose app shell
- Search screen with StateFlow UI state
- Dictionary manager screen with import, scan, enable, and reorder flows
- Repository and use-case boundaries
- Dictionary engine abstraction
- In-memory sample dictionary and dictionary management repositories
- Room/DataStore/Network modules prepared for future work
- MDX/MDict support isolated behind `DictionaryEngine`

## Docs

- Architecture: `docs/architecture.md`
- Roadmap: `docs/roadmap.md`
