package com.linkdict.core.datastore

data class UserPreferences(
    val useSystemTheme: Boolean = true,
    val enableSuggestions: Boolean = true,
    val enableQuickSearch: Boolean = false,
)
