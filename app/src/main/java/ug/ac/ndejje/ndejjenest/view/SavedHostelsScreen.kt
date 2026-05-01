package ug.ac.ndejje.ndejjenest.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import ug.ac.ndejje.ndejjenest.navigation.Screen
import ug.ac.ndejje.ndejjenest.ui.theme.Outfit
import ug.ac.ndejje.ndejjenest.ui.theme.PrimaryDarkBlue
import ug.ac.ndejje.ndejjenest.ui.theme.PrimaryGreen
import ug.ac.ndejje.ndejjenest.view.components.BottomNavigationBar
import ug.ac.ndejje.ndejjenest.view.components.HostelCardHorizontal
import ug.ac.ndejje.ndejjenest.viewmodel.SavedHostelsViewModel

@Composable
fun SavedHostelsScreen(
    navController: NavController,
    viewModel: SavedHostelsViewModel = viewModel()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val savedHostels by viewModel.savedHostels.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                currentRoute = currentRoute,
                savedCount = savedHostels.size
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = padding.calculateBottomPadding())
        ) {
            val isLandscape = maxWidth > maxHeight

            Column(modifier = Modifier.fillMaxSize()) {

                // ─── Header (adaptive) ───
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                ) {
                    if (isLandscape) {
                        // LANDSCAPE: compact, horizontally centered, no top padding, flush to top
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Bookmark,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Saved Hostels",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontFamily = Outfit
                            )
                            if (!isLoading && savedHostels.isNotEmpty()) {
                                Spacer(modifier = Modifier.width(10.dp))
                                Surface(
                                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f),
                                    shape = RoundedCornerShape(20.dp)
                                ) {
                                    Text(
                                        text = "${savedHostels.size}",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        fontFamily = Outfit,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
                                    )
                                }
                            }
                        }
                    } else {
                        // PORTRAIT: full header with icon badge on the right
                        Row(
                            modifier = Modifier
                                .statusBarsPadding()
                                .padding(horizontal = 24.dp, vertical = 20.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = "Saved Hostels",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontFamily = Outfit
                                )
                                if (!isLoading && savedHostels.isNotEmpty()) {
                                    Text(
                                        text = "${savedHostels.size} hostel${if (savedHostels.size == 1) "" else "s"} bookmarked",
                                        fontSize = 13.sp,
                                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                                        fontFamily = Outfit
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.15f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Bookmark,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                    }
                }

                // ─── Body ───
                when {
                    isLoading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                        }
                    }

                    savedHostels.isEmpty() -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(48.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(100.dp)
                                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.08f), CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.FavoriteBorder,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                                        modifier = Modifier.size(48.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(24.dp))
                                Text(
                                    text = "No saved hostels yet",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontFamily = Outfit,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Tap the heart icon on any hostel\nto bookmark it for later.",
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontFamily = Outfit,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 21.sp
                                )
                                Spacer(modifier = Modifier.height(32.dp))
                                Button(
                                    onClick = { navController.navigate(Screen.Home.route) },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.height(48.dp)
                                ) {
                                    Text(
                                        text = "Browse Hostels",
                                        fontFamily = Outfit,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                            }
                        }
                    }

                    isLandscape -> {
                        // Landscape: 2-column grid
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 8.dp),
                            contentPadding = PaddingValues(bottom = 16.dp)
                        ) {
                            items(savedHostels) { hostel ->
                                HostelCardHorizontal(
                                    hostel = hostel,
                                    onClick = {
                                        navController.navigate(Screen.HostelDetails.createRoute(hostel.id))
                                    },
                                    isSaved = true,
                                    onHeartClick = { viewModel.toggleSave(hostel.id) }
                                )
                            }
                        }
                    }

                    else -> {
                        // Portrait: single column
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(top = 8.dp, bottom = 16.dp)
                        ) {
                            items(savedHostels) { hostel ->
                                HostelCardHorizontal(
                                    hostel = hostel,
                                    onClick = {
                                        navController.navigate(Screen.HostelDetails.createRoute(hostel.id))
                                    },
                                    isSaved = true,
                                    onHeartClick = { viewModel.toggleSave(hostel.id) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
