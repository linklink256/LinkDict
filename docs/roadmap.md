# Roadmap

## Phase 1: Foundation

- Compose app shell
- Search screen skeleton
- Dictionary engine API
- In-memory sample dictionary
- CI build workflow

## Phase 2: Real Dictionary Search

- Port MDX parser into `core:dictionary-mdict`
- Add `MdictDictionaryEngine`
- Add dictionary engine pool with lazy loading
- Add search result ranking and merging

## Phase 3: Persistence

- Add Room database schema
- Add SQLCipher support if encryption is required
- Persist search history
- Persist dictionary configs and ordering
- Persist wordbook entries

## Phase 4: Dictionary Manager

- Import `.mdx` and `.mdd`
- Scan folders through Android SAF
- Enable, disable, rename, and reorder dictionaries
- Show scan/import status

## Phase 5: Product Features

- Quick search overlay
- Widgets
- Daily sentence
- Wordbook and word list learning
- Settings and theme customization
