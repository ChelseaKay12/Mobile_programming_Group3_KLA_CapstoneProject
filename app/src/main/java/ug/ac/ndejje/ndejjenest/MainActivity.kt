package ug.ac.ndejje.ndejjenest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ug.ac.ndejje.ndejjenest.ui.theme.NdejjeNestTheme
import ug.ac.ndejje.ndejjenest.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NdejjeNestTheme {
                AppNavigation()
            }
        }
    }
}