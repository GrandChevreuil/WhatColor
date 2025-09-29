package fr.eseo.ld.mm.whatcolor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import fr.eseo.ld.mm.whatcolor.ui.WhatColourApp
import fr.eseo.ld.mm.whatcolor.ui.theme.WhatColorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WhatColorTheme {
                WhatColourApp()
            }
        }
    }
}
