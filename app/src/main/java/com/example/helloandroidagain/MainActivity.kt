package com.example.helloandroidagain

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.helloandroidagain.ui.navigation.NavigationAppScaffold
import com.example.helloandroidagain.ui.theme.HelloAndroidAgainTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HelloAndroidAgainTheme {
//                BasicAppScaffold()
                NavigationAppScaffold()
            }
        }
    }
}
