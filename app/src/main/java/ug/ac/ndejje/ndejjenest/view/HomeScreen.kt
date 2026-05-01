package ug.ac.ndejje.ndejjenest.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import ug.ac.ndejje.ndejjenest.navigation.Screen
import ug.ac.ndejje.ndejjenest.ui.theme.PrimaryDarkBlue
import ug.ac.ndejje.ndejjenest.view.components.BottomNavigationBar
import ug.ac.ndejje.ndejjenest.view.components.BrandingHeader
import ug.ac.ndejje.ndejjenest.view.components.CategoryChips
import ug.ac.ndejje.ndejjenest.view.components.HostelCard
import ug.ac.ndejje.ndejjenest.view.components.HostelCardHorizontal
import ug.ac.ndejje.ndejjenest.view.components.SearchBarWithFilter
import ug.ac.ndejje.ndejjenest.view.components.SectionHeader
import ug.ac.ndejje.ndejjenest.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val savedHostelIds by viewModel.savedHostelIds.collectAsState()
    val featuredHostels by viewModel.featuredHostels.collectAsState()
    val recommendedHostels by viewModel.recommendedHostels.collectAsState()
    val categories = listOf("All", "Bombo", "Luwero", "Kampala")

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                currentRoute = currentRoute,
                savedCount = savedHostelIds.size
            )
        }
    ) { paddingValues ->
        // Single LazyColumn makes ALL content scrollable in both portrait and landscape
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding()),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            // --- Sticky Header (Dark Blue) ---
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = if (androidx.compose.foundation.isSystemInDarkTheme()) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                        )
                        .statusBarsPadding()
                        .padding(bottom = 8.dp)
                ) {
                    BrandingHeader()
                    SearchBarWithFilter(
                        searchQuery = searchQuery,
                        onSearchQueryChange = { viewModel.onSearchQueryChange(it) }
                    )
                    CategoryChips(
                        categories = categories,
                        selectedCategory = selectedCategory,
                        onCategorySelected = { viewModel.onCategorySelected(it) }
                    )
                }
            }

            // --- Featured Hostels Section ---
            item {
                SectionHeader(
                    title = "Featured Hostels",
                    onViewAllClick = { /* Handle view all */ }
                )
            }

            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(featuredHostels) { hostel ->
                        HostelCard(
                            hostel = hostel,
                            onClick = { navController.navigate(Screen.HostelDetails.createRoute(hostel.id)) },
                            isSaved = savedHostelIds.contains(hostel.id),
                            onHeartClick = { viewModel.toggleSave(hostel.id) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            // --- Recommended Hostels Section ---
            item {
                SectionHeader(
                    title = "Recommended for you",
                    onViewAllClick = { /* Handle view all */ }
                )
            }

            items(recommendedHostels) { hostel ->
                HostelCardHorizontal(
                    hostel = hostel,
                    onClick = { navController.navigate(Screen.HostelDetails.createRoute(hostel.id)) },
                    isSaved = savedHostelIds.contains(hostel.id),
                    onHeartClick = { viewModel.toggleSave(hostel.id) }
                )
            }
        }
    }
}