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
import androidx.navigation.NavController
import ug.ac.ndejje.ndejjenest.navigation.Screen

data class OnboardingPage(
    val title: String,
    val subtitle: String,
    val image: Int
)

@Composable
fun OnboardingScreen(navController: NavController) {
    val pages = listOf(
        OnboardingPage(
            title = "Find Your Perfect Stay",
            subtitle = "Discover affordable hostels and rental rooms near Ndejje University.",
            image = 0 // Placeholder
        ),
        OnboardingPage(
            title = "Easy Booking Process",
            subtitle = "Book your preferred hostel in just a few clicks with secure payments.",
            image = 0 // Placeholder
        ),
        OnboardingPage(
            title = "Connect with Roommates",
            subtitle = "Find compatible roommates to share your university journey with.",
            image = 0 // Placeholder
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
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(0.8f)
            ) { pageIndex ->
                OnboardingPagerItem(page = pages[pageIndex])
            }
            
            // Placeholder for bottom section (Feature 4, 5, 6)
            Column(
                modifier = Modifier
                    .weight(0.2f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                // We'll add the Page Indicator and Buttons here in the next steps
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun OnboardingPagerItem(page: OnboardingPage) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        // Headlines and Illustrations will be added in Feature 2 and 3
        Spacer(modifier = Modifier.height(60.dp))
        Text(text = page.title, style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = page.subtitle, style = MaterialTheme.typography.bodyLarge)
    }
}
