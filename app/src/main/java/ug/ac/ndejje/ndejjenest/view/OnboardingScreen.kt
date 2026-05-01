package ug.ac.ndejje.ndejjenest.view

import androidx.compose.foundation.layout.*
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
    // 1. Define the list of pages we want to show
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

    // 2. State management for the pager (keeps track of which page we are on)
    val pagerState = rememberPagerState(pageCount = { pages.size })

    // 3. Main layout container
    Scaffold(
        containerColor = Color.White
    ) { padding ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            val isLandscape = maxWidth > maxHeight

            if (isLandscape) {
                // --- LANDSCAPE LAYOUT: Split Screen ---
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Left Side: Image Pager
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier.fillMaxSize()
                        ) { pageIndex ->
                            IllustrationSection(page = pages[pageIndex])
                        }
                        
                        // Page Indicator on top of image
                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 24.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            repeat(pages.size) { index ->
                                val isActive = pagerState.currentPage == index
                                Box(
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp)
                                        .height(8.dp)
                                        .width(if (isActive) 24.dp else 8.dp)
                                        .clip(CircleShape)
                                        .background(if (isActive) PrimaryDarkBlue else Color(0xFFE0E0E0))
                                )
                            }
                        }
                    }

                    // Right Side: Text & Buttons
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .verticalScroll(androidx.compose.foundation.rememberScrollState())
                            .padding(24.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = buildAnnotatedString {
                                append("Find Your\n")
                                withStyle(style = SpanStyle(color = PrimaryGreen)) {
                                    append("Perfect ")
                                }
                                append("Stay")
                            },
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontFamily = Outfit,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryDarkBlue,
                                lineHeight = 40.sp
                            )
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text(
                            text = "Discover affordable hostels and rental rooms near Ndejje University.",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontFamily = Outfit,
                                color = Color.Gray
                            )
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        OnboardingButtons(navController = navController)
                    }
                }
            } else {
                // --- PORTRAIT LAYOUT: Stacked Section ---
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(androidx.compose.foundation.rememberScrollState())
                ) {
                    // Header Section
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .padding(top = 60.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = buildAnnotatedString {
                                append("Find Your\n")
                                withStyle(style = SpanStyle(color = PrimaryGreen)) {
                                    append("Perfect ")
                                }
                                append("Stay")
                            },
                            style = MaterialTheme.typography.displayLarge.copy(
                                fontFamily = Outfit,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryDarkBlue,
                                lineHeight = 44.sp
                            )
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text(
                            text = "Discover affordable hostels and rental rooms near Ndejje University.",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontFamily = Outfit,
                                color = Color.Gray
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp)
                    ) { pageIndex ->
                        IllustrationSection(page = pages[pageIndex])
                    }
                    
                    Spacer(modifier = Modifier.weight(1f))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 48.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Indicators
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            repeat(pages.size) { index ->
                                val isActive = pagerState.currentPage == index
                                Box(
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp)
                                        .height(8.dp)
                                        .width(if (isActive) 24.dp else 8.dp)
                                        .clip(CircleShape)
                                        .background(if (isActive) PrimaryDarkBlue else Color(0xFFE0E0E0))
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(32.dp))

                        OnboardingButtons(navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun OnboardingButtons(navController: NavController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = { navController.navigate(Screen.Login.route) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryDarkBlue),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Get Started",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = Outfit,
                    fontWeight = FontWeight.Bold
                ),
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = { navController.navigate(Screen.Login.route) }
        ) {
            Text(
                text = "Log In",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = Outfit,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryDarkBlue
                )
            )
        }
    }
}

/**
 * Helper function to draw the onboarding illustrations.
 * Uses ContentScale.Crop to make the images look "full".
 */
@Composable
fun IllustrationSection(page: OnboardingPage) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp),
        contentAlignment = Alignment.Center
    ) {
        // Safe resource loading to prevent crashes if images are missing
        val imageRes = try {
            if (page.image == 0) R.drawable.ic_launcher_foreground else page.image
        } catch (e: Exception) {
            R.drawable.ic_launcher_foreground
        }

        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "Onboarding Illustration",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
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
