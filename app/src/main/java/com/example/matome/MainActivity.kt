package com.example.matome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.matome.navigation.MatomeNavHost
import com.example.matome.ui.theme.MatomeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MatomeTheme {
                MatomeNavHost()
            }
        }
    }
}
