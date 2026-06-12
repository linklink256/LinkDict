package com.linkdict.core.database

interface DatabaseTransactionRunner {
    suspend fun <T> runInTransaction(block: suspend () -> T): T
}
