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
import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.Path
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.unit.dp
import ug.ac.ndejje.ndejjenest.R
import ug.ac.ndejje.ndejjenest.ui.theme.NdejjeNestTheme
import ug.ac.ndejje.ndejjenest.ui.theme.PrimaryDarkBlue
import ug.ac.ndejje.ndejjenest.ui.theme.PrimaryYellow
import ug.ac.ndejje.ndejjenest.ui.theme.TextGray
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ug.ac.ndejje.ndejjenest.ui.theme.Outfit
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onSplashFinished: () -> Unit) {
    val currentOnSplashFinished by rememberUpdatedState(onSplashFinished)

    LaunchedEffect(Unit) {
        delay(3000) // 3 seconds delay
        currentOnSplashFinished()
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = PrimaryDarkBlue
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                BottomWave()
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                LogoSection()
                Spacer(modifier = Modifier.height(16.dp))
                TitleSection()
                Spacer(modifier = Modifier.height(8.dp))
                SloganSection()
                Spacer(modifier = Modifier.height(48.dp))
                LoadingSection()
            }
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
    Row(verticalAlignment = Alignment.Bottom) {
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

@Composable
fun SloganSection() {
    Text(
        text = "Find. Stay. Thrive.",
        style = MaterialTheme.typography.bodyLarge.copy(
            fontFamily = Outfit,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            letterSpacing = 2.sp
        ),
        color = TextGray
    )
}

@Composable
fun LoadingSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            color = PrimaryYellow,
            strokeWidth = 3.dp,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Loading...",
            style = MaterialTheme.typography.labelSmall,
            color = TextGray
        )
    }
}

@Composable
fun BottomWave() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        val width = size.width
        val height = size.height
        val path = Path().apply {
            moveTo(0f, height) // Bottom-left corner
            lineTo(0f, height * 0.7f) // Left side start (higher than before)
            quadraticBezierTo(
                width * 0.5f, height * 0.65f, // Control point
                width, height * 0.4f // Right side end (much higher)
            )
            lineTo(width, height) // Bottom-right corner
            close()
        }
        drawPath(
            path = path,
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
