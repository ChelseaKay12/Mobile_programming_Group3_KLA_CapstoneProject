package ug.ac.ndejje.ndejjenest.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
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

            // Main Content
            if (hostel == null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = PrimaryYellow)
                }
            } else {
                val item = hostel!!
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    // Feature 4: Core Hostel Information
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = item.name,
                                fontSize = 24.sp,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                color = PrimaryDarkBlue
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = null,
                                    tint = Color.Gray,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = item.location,
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            }
                        }

                        // Rating
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .background(PrimaryYellow.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = PrimaryYellow,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = item.rating.toString(),
                                fontSize = 14.sp,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                color = PrimaryDarkBlue
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Price Section
                    Text(
                        text = "Price",
                        fontSize = 16.sp,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold,
                        color = PrimaryDarkBlue
                    )
                    Text(
                        text = item.price,
                        fontSize = 20.sp,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        color = PrimaryYellow
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Text(
                        text = "Feature 5, 6, 7 details will go here",
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }
            }
        }
    }
}
