package ug.ac.ndejje.ndejjenest.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ug.ac.ndejje.ndejjenest.ui.theme.NdejjeNestTheme
import ug.ac.ndejje.ndejjenest.ui.theme.PrimaryDarkBlue

@Composable
fun SplashScreen(onSplashFinished: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = PrimaryDarkBlue
    ) {
        // Content will be added in subsequent features
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    NdejjeNestTheme {
        SplashScreen(onSplashFinished = {})
    }
}
