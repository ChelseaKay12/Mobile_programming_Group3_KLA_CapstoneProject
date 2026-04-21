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
            image = 0 // Placeholder
        ),
        OnboardingPage(
            title = "Easy\nBooking Process",
            subtitle = "Book your preferred hostel in just a few clicks with secure payments.",
            image = 0 // Placeholder
        ),
        OnboardingPage(
            title = "Connect with\nRoommates",
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
        
        val annotatedTitle = buildAnnotatedString {
            val lines = page.title.split("\n")
            lines.forEachIndexed { lineIndex, line ->
                val words = line.split(" ")
                words.forEachIndexed { wordIndex, word ->
                    val color = when (word.lowercase().trim()) {
                        "perfect" -> PrimaryYellow
                        "stay", "process", "roommates" -> PrimaryGreen
                        else -> PrimaryDarkBlue
                    }
                    
                    withStyle(style = SpanStyle(color = color)) {
                        append(word)
                    }
                    if (wordIndex < words.size - 1) append(" ")
                }
                if (lineIndex < lines.size - 1) append("\n")
            }
        }

        Text(
            text = annotatedTitle,
            style = MaterialTheme.typography.displayLarge.copy(
                fontFamily = Outfit,
                fontWeight = FontWeight.Bold,
                lineHeight = 44.sp
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = page.subtitle,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontFamily = Outfit,
                color = Color.Gray
            )
        )
    }
}
