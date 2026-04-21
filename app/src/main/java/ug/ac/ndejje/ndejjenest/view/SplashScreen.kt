package ug.ac.ndejje.ndejjenest.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ug.ac.ndejje.ndejjenest.R
import ug.ac.ndejje.ndejjenest.ui.theme.NdejjeNestTheme
import ug.ac.ndejje.ndejjenest.ui.theme.PrimaryDarkBlue

@Composable
fun SplashScreen(onSplashFinished: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = PrimaryDarkBlue
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LogoSection()
        }
    }
}

@Composable
fun LogoSection() {
    Image(
        painter = painterResource(id = R.drawable.ndejjenest),
        contentDescription = "App Logo",
        modifier = Modifier.size(200.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    NdejjeNestTheme {
        SplashScreen(onSplashFinished = {})
    }
}
