package com.healyks.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.healyks.app.ui.theme.HealyksTheme
import com.healyks.app.view.navigation.HealyksNavigation
import com.healyks.app.view.screens.PostUserBodyScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HealyksTheme {
                HealyksNavigation()
            }
        }
    }
}
