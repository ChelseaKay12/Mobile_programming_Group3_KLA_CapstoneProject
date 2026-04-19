package ug.ac.ndejje.ndejjenest.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ug.ac.ndejje.ndejjenest.R
import ug.ac.ndejje.ndejjenest.ui.theme.NdejjeNestTheme
import ug.ac.ndejje.ndejjenest.ui.theme.PrimaryDarkBlue
import ug.ac.ndejje.ndejjenest.ui.theme.PrimaryYellow

@Composable
fun SplashScreen(onSplashFinished: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = PrimaryDarkBlue
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LogoSection()
            Spacer(modifier = Modifier.height(16.dp))
            TitleSection()
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

@Composable
fun TitleSection() {
    Row {
        Text(
            text = "Ndejje",
            style = MaterialTheme.typography.displayLarge,
            color = Color.White
        )
        Text(
            text = "Nest",
            style = MaterialTheme.typography.displayLarge,
            color = PrimaryYellow
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    NdejjeNestTheme {
        SplashScreen(onSplashFinished = {})
    }
}
