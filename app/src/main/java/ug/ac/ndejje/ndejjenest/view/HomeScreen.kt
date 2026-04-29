package ug.ac.ndejje.ndejjenest.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
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
    val categories = listOf("All", "Bombo", "Luwero", "Kampala")

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController, currentRoute = currentRoute)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            // Top Section with Dark Blue Background
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = PrimaryDarkBlue,
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

            val featuredHostels by viewModel.featuredHostels.collectAsState()

            SectionHeader(
                title = "Featured Hostels",
                onViewAllClick = { /* Handle view all */ }
            )
            
            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(featuredHostels) { hostel ->
                    HostelCard(
                        hostel = hostel,
                        modifier = Modifier.width(180.dp),
                        onClick = { /* Handle hostel click */ }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            val recommendedHostels by viewModel.recommendedHostels.collectAsState()

            SectionHeader(
                title = "Recommended for you",
                onViewAllClick = { /* Handle view all */ }
            )

            // Main Content Area (White background) - Scrollable Cards
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                recommendedHostels.forEach { hostel ->
                    HostelCardHorizontal(
                        hostel = hostel,
                        onClick = { /* Handle hostel click */ }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}