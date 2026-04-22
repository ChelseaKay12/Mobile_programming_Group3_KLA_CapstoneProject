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

data class OnboardingPage(
    val title: String,
    val subtitle: String,
    val image: Int
)

@Composable
fun OnboardingScreen(navController: NavController) {
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
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Static Header Section (Stays Intact)
            Column(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.screen_margin_large))
                    .padding(top = 60.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // Feature 2: Static Headline (Requested to stay the same for all images)
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
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))
                Text(
                    text = "Discover affordable hostels and rental rooms near Ndejje University.",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = Outfit,
                        color = Color.Gray
                    )
                )
            }

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_extra_large)))

            // Scrollable Image Section
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
            ) { pageIndex ->
                IllustrationSection(page = pages[pageIndex])
            }
            
            Spacer(modifier = Modifier.weight(1f))

            // Bottom section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                // Feature 4: Page Indicator (The 3 dots)
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // repeat() is a simple loop that runs once for each page we have
                    repeat(pages.size) { index ->
                        // Check if this specific dot is the one the user is looking at
                        val isActive = pagerState.currentPage == index
                        
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 4.dp) // Horizontal space between dots
                                .height(8.dp)               // Height for all dots
                                .width(if (isActive) 24.dp else 8.dp) // Active dot is elongated (wider)
                                .clip(CircleShape)          // Makes the dots rounded like circles
                                .background(
                                    if (isActive) PrimaryDarkBlue else Color(0xFFE0E0E0) // Blue if active, Light Gray if not
                                )
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_extra_large)))

                // Feature 5: Primary Action Button ("Get Started")
                Button(
                    onClick = { 
                        // This tells the app: "When clicked, go to the Login screen"
                        navController.navigate(Screen.Login.route) 
                    },
                    modifier = Modifier
                        .fillMaxWidth()        // Make it stretch across the width
                        .padding(horizontal = dimensionResource(id = R.dimen.screen_margin_large)) // Use large margin from dimens.xml
                        .height(dimensionResource(id = R.dimen.button_height)),        // Use button height from dimens.xml
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryDarkBlue // Use our custom dark blue
                    ),
                    shape = RoundedCornerShape(16.dp) // Give it rounded corners
                ) {
                    Text(
                        text = "Get Started",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontFamily = Outfit,
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color.White // Make the text white so it stands out
                    )
                }

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))
            }
        }
    }
}

@Composable
fun IllustrationSection(page: OnboardingPage) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp),
        contentAlignment = Alignment.Center
    ) {
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

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    ug.ac.ndejje.ndejjenest.ui.theme.NdejjeNestTheme {
        OnboardingScreen(navController = androidx.navigation.compose.rememberNavController())
    }
}
