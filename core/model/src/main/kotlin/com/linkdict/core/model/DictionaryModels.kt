package com.linkdict.core.model

data class DictionaryEntry(
    val headword: String,
    val definition: String,
    val sourceName: String,
    val phonetic: String? = null,
    val htmlContent: String? = null,
)

data class ExternalDictionaryLink(
    val title: String,
    val url: String,
)

data class SearchResult(
    val query: String,
    val entries: List<DictionaryEntry>,
    val externalLinks: List<ExternalDictionaryLink> = emptyList(),
    val isFavorite: Boolean = false,
)

data class DictionaryConfig(
    val id: String,
    val displayName: String,
    val filePath: String,
    val enabled: Boolean = true,
    val order: Int = 0,
    val sourceType: DictionarySourceType = DictionarySourceType.ImportedFile,
    val fileSizeBytes: Long = 0L,
)

enum class DictionarySourceType {
    BuiltIn,
    ImportedFile,
    ScannedPath,
}

data class DictionaryImportRequest(
    val displayName: String,
    val filePath: String,
    val companionResourcePath: String? = null,
)

data class DictionaryScanResult(
    val rootPath: String,
    val candidates: List<DictionaryImportRequest>,
    val skippedCount: Int = 0,
)
