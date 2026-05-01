package ug.ac.ndejje.ndejjenest.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.animation.animateColorAsState
import androidx.compose.ui.draw.clip
import androidx.navigation.NavController
import ug.ac.ndejje.ndejjenest.navigation.Screen
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.Image
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.dimensionResource
import ug.ac.ndejje.ndejjenest.R
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ug.ac.ndejje.ndejjenest.ui.theme.PrimaryDarkBlue
import ug.ac.ndejje.ndejjenest.ui.theme.PrimaryYellow
import ug.ac.ndejje.ndejjenest.ui.theme.PrimaryGreen
import ug.ac.ndejje.ndejjenest.ui.theme.Outfit
import androidx.compose.ui.platform.LocalConfiguration
import android.content.res.Configuration
/**
 * Data model for each onboarding page.
 * This is like a "blueprint" that tells us what each slide needs.
 */
data class OnboardingPage(
    val title: String,    // The bold headline
    val subtitle: String, // The descriptive text
    val image: Int        // The image resource (like R.drawable.onboarding1)
)



@Composable
fun OnboardingScreen(navController: NavController) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val pages = listOf(
        OnboardingPage(
            title = "Find Your\nPerfect Stay",
            subtitle = "Discover affordable hostels and rental rooms near Ndejje University.",
            image = R.drawable.onboarding1
        ),
        OnboardingPage(
            title = "Connect with\nRoommates",
            subtitle = "Find compatible roommates to share your university journey with.",
            image = R.drawable.onboarding3
        ),
        OnboardingPage(
            title = "Meet Your\nHost, Mellisa",
            subtitle = "Get direct support from experienced hosts to make your stay comfortable.",
            image = R.drawable.mellisa
        )
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        if (isLandscape) {
            // --- LANDSCAPE LAYOUT: Two Columns ---
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                // Left Column: Text and Buttons (Now Static to match Portrait)
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(32.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Center
                ) {
                    val annotatedTitle = buildAnnotatedString {
                        append("Find Your\n")
                        withStyle(style = SpanStyle(color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else PrimaryYellow)) { append("Perfect ") }
                        withStyle(style = SpanStyle(color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else PrimaryGreen)) { append("Stay") }
                    }

                    Text(
                        text = annotatedTitle,
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontFamily = Outfit,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "Discover affordable hostels and rental rooms near Ndejje University.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = Outfit,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Indicator
                    Row(horizontalArrangement = Arrangement.Start) {
                        repeat(pages.size) { index ->
                            val isActive = pagerState.currentPage == index
                            Box(
                                modifier = Modifier
                                    .padding(end = 6.dp)
                                    .height(6.dp)
                                    .width(if (isActive) 18.dp else 6.dp)
                                    .clip(CircleShape)
                                    .background(if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = { navController.navigate(Screen.Login.route) },
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Get Started", fontFamily = Outfit, fontWeight = FontWeight.Bold)
                    }
                }

                // Right Column: Image
                Box(
                    modifier = Modifier
                        .weight(1.2f)
                        .fillMaxHeight()
                ) {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxSize()
                    ) { pageIndex ->
                        Image(
                            painter = painterResource(id = pages[pageIndex].image),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        } else {
            // --- PORTRAIT LAYOUT: Original Column ---
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(id = R.dimen.screen_margin_large))
                        .padding(top = 60.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    val annotatedTitle = buildAnnotatedString {
                        append("Find Your\n")
                        withStyle(style = SpanStyle(color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else PrimaryYellow)) { append("Perfect ") }
                        withStyle(style = SpanStyle(color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else PrimaryGreen)) { append("Stay") }
                    }

                    Text(
                        text = annotatedTitle,
                        style = MaterialTheme.typography.displayLarge.copy(
                            fontFamily = Outfit,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground,
                            lineHeight = 44.sp
                        )
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "Discover affordable hostels and rental rooms near Ndejje University.",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontFamily = Outfit,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                ) { pageIndex ->
                    IllustrationSection(page = pages[pageIndex])
                }
                
                Spacer(modifier = Modifier.height(48.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 48.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(horizontalArrangement = Arrangement.Center) {
                        repeat(pages.size) { index ->
                            val isActive = pagerState.currentPage == index
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .height(8.dp)
                                    .width(if (isActive) 24.dp else 8.dp)
                                    .clip(CircleShape)
                                    .background(if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant)
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = { navController.navigate(Screen.Login.route) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Get Started", fontFamily = Outfit, fontWeight = FontWeight.Bold)
                    }

                    TextButton(onClick = { navController.navigate(Screen.Login.route) }) {
                        Text("Log In", fontFamily = Outfit, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}

@Composable
fun IllustrationSection(page: OnboardingPage) {
    Image(
        painter = painterResource(id = page.image),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}

/**
 * Preview function to see the design in Android Studio.
 */
@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    ug.ac.ndejje.ndejjenest.ui.theme.NdejjeNestTheme {
        OnboardingScreen(navController = androidx.navigation.compose.rememberNavController())
    }
}
