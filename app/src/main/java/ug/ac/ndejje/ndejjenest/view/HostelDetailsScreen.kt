package ug.ac.ndejje.ndejjenest.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ug.ac.ndejje.ndejjenest.navigation.Screen
import ug.ac.ndejje.ndejjenest.ui.theme.PrimaryDarkBlue
import ug.ac.ndejje.ndejjenest.ui.theme.PrimaryYellow
import ug.ac.ndejje.ndejjenest.viewmodel.HostelDetailsViewModel

@Composable
fun HostelDetailsScreen(
    navController: NavController,
    hostelId: String?,
    viewModel: HostelDetailsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val hostel by viewModel.hostel.collectAsState()

    LaunchedEffect(hostelId) {
        hostelId?.let { viewModel.getHostel(it) }
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = padding.calculateBottomPadding())
        ) {
            // Feature 3: Image Header & Overlay Toolbar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                // Image Placeholder
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.LightGray)
                ) {
                    Text(
                        text = "Image Placeholder",
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.DarkGray
                    )
                }

                // Toolbar Icons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color.White, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = PrimaryDarkBlue
                        )
                    }

                    IconButton(
                        onClick = { /* Handle favorite */ },
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color.White, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = PrimaryDarkBlue
                        )
                    }
                }
            }

            // Rest of the UI will follow in next features
            if (hostel == null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = PrimaryYellow)
                }
            } else {
                Text(
                    text = "Feature 4, 5, 6, 7 details for ${hostel?.name} will go here",
                    modifier = Modifier.padding(20.dp)
                )
            }
        }
    }
}
