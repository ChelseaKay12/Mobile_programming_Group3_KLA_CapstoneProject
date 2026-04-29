package ug.ac.ndejje.ndejjenest.view

// ---- REWRITTEN: Full Map Screen with Google Maps (Map Screen - Feature 3) ----

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import ug.ac.ndejje.ndejjenest.model.Hostel
import ug.ac.ndejje.ndejjenest.model.HostelRepository
import ug.ac.ndejje.ndejjenest.ui.theme.PrimaryDarkBlue
import ug.ac.ndejje.ndejjenest.ui.theme.PrimaryYellow

@Composable
fun MapScreen(navController: NavController, hostelId: String? = null) {
    // Load all hostels
    val repository = remember { HostelRepository() }
    val allHostels = remember {
        repository.getFeaturedHostels() + repository.getRecommendedHostels()
    }

    // Track the selected hostel (default to the one passed via navigation)
    var selectedHostel by remember {
        mutableStateOf(allHostels.find { it.id == hostelId })
    }

    // Determine initial camera position
    val initialTarget = if (selectedHostel != null) {
        LatLng(selectedHostel!!.latitude, selectedHostel!!.longitude)
    } else {
        // Default: Ndejje University area (Bombo)
        LatLng(0.5840, 32.5330)
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(initialTarget, 14f)
    }

    // When a hostel is selected, animate the camera to it
    LaunchedEffect(selectedHostel) {
        selectedHostel?.let { hostel ->
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngZoom(
                    LatLng(hostel.latitude, hostel.longitude), 15f
                ),
                durationMs = 800
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // ---- Google Map ----
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(
                zoomControlsEnabled = false,
                myLocationButtonEnabled = false
            )
        ) {
            // Place markers for all hostels
            allHostels.forEach { hostel ->
                val position = LatLng(hostel.latitude, hostel.longitude)
                val isSelected = selectedHostel?.id == hostel.id

                Marker(
                    state = MarkerState(position = position),
                    title = hostel.name,
                    snippet = hostel.location,
                    onClick = {
                        selectedHostel = hostel
                        false // Return false to allow default info window behavior
                    }
                )
            }
        }

        // ---- Top Bar Overlay ----
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Back Button
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

            // Title
            Text(
                text = "Location",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryDarkBlue
            )

            // More Options Button
            IconButton(
                onClick = { /* Handle more options */ },
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.White, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More",
                    tint = PrimaryDarkBlue
                )
            }
        }

        // ---- Bottom Info Card (Feature 4 placeholder - shows when a hostel is selected) ----
        if (selectedHostel != null) {
            val hostel = selectedHostel!!
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = hostel.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryDarkBlue
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = hostel.location,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

// ---- END REWRITTEN ----
