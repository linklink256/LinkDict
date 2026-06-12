package com.linkdict.core.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatch AppDispatchers {
 val io: CoroutineDispatcher = Dispatchers.IO
    override val default: CoroutineDispatcher = Dispatchers.Default
    override val main: CoroutineDispatcher = Dispatchers.Main
}
