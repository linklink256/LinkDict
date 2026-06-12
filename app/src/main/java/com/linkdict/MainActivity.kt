package com.linkdict

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.linkdict.core.designsystem.LinkDictTheme
import com.linkdict.di.AppContainer

class MainActivity : ComponentActivity() {
    private val appContainer by lazy { AppContainer() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LinkDictTheme {
                LinkDictApp(appContainer = appContainer)
            }
        }
    }
}
