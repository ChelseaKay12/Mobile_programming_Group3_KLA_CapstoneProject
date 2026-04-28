package ug.ac.ndejje.ndejjenest.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ug.ac.ndejje.ndejjenest.ui.theme.PrimaryDarkBlue
import ug.ac.ndejje.ndejjenest.view.components.BrandingHeader
import ug.ac.ndejje.ndejjenest.view.components.CategoryChips
import ug.ac.ndejje.ndejjenest.view.components.HostelCard
import ug.ac.ndejje.ndejjenest.view.components.SearchBarWithFilter
import ug.ac.ndejje.ndejjenest.view.components.SectionHeader
import ug.ac.ndejje.ndejjenest.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    var selectedCategory by remember { mutableStateOf("All") }
    val categories = listOf("All", "Bombo", "Luwero", "Kampala")

    Scaffold(
        bottomBar = {
            // TODO: Implement Bottom Navigation in later feature
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding())
                .verticalScroll(rememberScrollState())
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
                    onCategorySelected = { selectedCategory = it }
                )
            }

            // Main Content Area (White background)
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                val featuredHostels by viewModel.featuredHostels.collectAsState()

                SectionHeader(
                    title = "Featured Hostels",
                    onViewAllClick = { /* Handle view all */ }
                )
                
                // 2-column Grid Layout for Hostels
                featuredHostels.chunked(2).forEach { rowItems ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        rowItems.forEach { hostel ->
                            HostelCard(
                                hostel = hostel,
                                modifier = Modifier.weight(1f),
                                onClick = { /* Handle hostel click */ }
                            )
                        }
                        // Fill empty space if there's only 1 item in the last row
                        if (rowItems.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                
                // TODO: Recommended for you (Feature 4)
            }
        }
    }
}