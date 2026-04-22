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
        containerColor = Color.White // Keeping the background clean and white
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // --- HEADER SECTION ---
            // This part stays fixed (intact) at the top of the screen
            Column(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.screen_margin_large))
                    .padding(top = 60.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // buildAnnotatedString allows us to color specific words (Feature 2)
                val annotatedTitle = buildAnnotatedString {
                    append("Find Your\n")
                    withStyle(style = SpanStyle(color = PrimaryYellow)) {
                        append("Perfect ")
                    }
                    withStyle(style = SpanStyle(color = PrimaryGreen)) {
                        append("Stay")
                    }
                }

                Text(
                    text = annotatedTitle,
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontFamily = Outfit,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryDarkBlue,
                        lineHeight = 44.sp
                    )
                )
                
                // Space between headline and subtitle
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))
                
                Text(
                    text = "Discover affordable hostels and rental rooms near Ndejje University.",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = Outfit,
                        color = Color.Gray
                    )
                )
            }

            // Space between text and the image section
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_extra_large)))

            // --- IMAGE SECTION ---
            // HorizontalPager handles the swiping logic for the images
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp) // Large height for "full" images
            ) { pageIndex ->
                // This function draws the image for the current page
                IllustrationSection(page = pages[pageIndex])
            }
            
            // Flexible spacer to push the buttons to the bottom of the screen
            Spacer(modifier = Modifier.weight(1f))

            // --- BOTTOM SECTION (Indicators & Buttons) ---
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                // Page Indicator (The 3 dots - Feature 4)
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
                                .width(if (isActive) 24.dp else 8.dp) // Active dot is wider
                                .clip(CircleShape)
                                .background(
                                    if (isActive) PrimaryDarkBlue else Color(0xFFE0E0E0)
                                )
                        )
                    }
                }
                
                // Space between dots and main button
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_extra_large)))

                // --- MAIN ACTION BUTTON (Feature 5) ---
                // Setup for the "hover/press" effect
                val interactionSource = remember { MutableInteractionSource() }
                val isPressed by interactionSource.collectIsPressedAsState()
                val animatedButtonColor by animateColorAsState(
                    targetValue = if (isPressed) PrimaryDarkBlue.copy(alpha = 0.85f) else PrimaryDarkBlue,
                    label = "buttonPressAnimation"
                )

                Button(
                    onClick = { 
                        // Go to Login Screen
                        navController.navigate(Screen.Login.route) 
                    },
                    interactionSource = interactionSource,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = dimensionResource(id = R.dimen.screen_margin_large))
                        .height(dimensionResource(id = R.dimen.button_height)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = animatedButtonColor
                    ),
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

                // Small space between buttons
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))

                // --- SECONDARY ACTION BUTTON (Feature 6) ---
                TextButton(
                    onClick = { 
                        navController.navigate(Screen.Login.route) 
                    }
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
