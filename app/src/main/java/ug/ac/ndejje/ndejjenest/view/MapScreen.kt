package ug.ac.ndejje.ndejjenest.view

// ---- REWRITTEN: Full Map Screen with Google Maps (Map Screen - Feature 3) ----

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch
import ug.ac.ndejje.ndejjenest.model.Hostel
import ug.ac.ndejje.ndejjenest.model.HostelRepository
import ug.ac.ndejje.ndejjenest.ui.theme.PrimaryDarkBlue
import ug.ac.ndejje.ndejjenest.ui.theme.PrimaryYellow

@Composable
fun MapScreen(
    navController: NavController, 
    hostelId: String? = null,
    viewModel: ug.ac.ndejje.ndejjenest.viewmodel.HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    // ---- ADDED: Context for launching external intents (Map Screen - Feature 5) ----
    val context = LocalContext.current
    // ---- END ADDED ----

    // Live data from HomeViewModel (which now fetches from Firestore)
    val featuredHostels by viewModel.featuredHostels.collectAsState()
    val recommendedHostels by viewModel.recommendedHostels.collectAsState()
    val allHostels = featuredHostels + recommendedHostels

    // Track the selected hostel (Only pre-select if a specific ID was passed)
    var selectedHostel by remember {
        mutableStateOf<ug.ac.ndejje.ndejjenest.model.Hostel?>(null)
    }

    // Effect to update selectedHostel when data loads or hostelId changes
    LaunchedEffect(allHostels, hostelId) {
        if (hostelId != null && selectedHostel == null) {
            selectedHostel = allHostels.find { it.id == hostelId }
        }
    }

    // Default center: Ndejje University area
    val defaultLocation = LatLng(0.6060, 32.5320)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultLocation, 14f)
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

        // ---- ENHANCED: Bottom Info Card (Map Screen - Feature 4) ----
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
                    // Hostel info row with image and details
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Live Image from Firestore
                        coil.compose.AsyncImage(
                            model = hostel.imageUrl,
                            contentDescription = hostel.name,
                            modifier = Modifier
                                .size(72.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = androidx.compose.ui.layout.ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        // Hostel Details
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = hostel.name,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryDarkBlue,
                                fontFamily = ug.ac.ndejje.ndejjenest.ui.theme.Outfit
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${hostel.location}, Near Ndejje University",
                                fontSize = 12.sp,
                                color = Color.Gray,
                                fontFamily = ug.ac.ndejje.ndejjenest.ui.theme.Outfit
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            // Distance indicator
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(Color(0xFF4CAF50), CircleShape)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "5 mins (1.3 km) away",
                                    fontSize = 12.sp,
                                    color = Color(0xFF4CAF50),
                                    fontFamily = ug.ac.ndejje.ndejjenest.ui.theme.Outfit
                                )
                            }
                        }

                        // Navigate to details chevron
                        IconButton(
                            onClick = {
                                navController.navigate("hostel_details/${hostel.id}")
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "View Details",
                                tint = PrimaryDarkBlue
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // ---- ADDED: Get Directions & My Location (Map Screen - Feature 5) ----
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Get Directions Button
                        Button(
                            onClick = {
                                // Opens Google Maps with navigation to hostel
                                // Falls back to browser if Google Maps not installed
                                try {
                                    val uri = Uri.parse(
                                        "google.navigation:q=${hostel.latitude},${hostel.longitude}&mode=w"
                                    )
                                    val intent = Intent(Intent.ACTION_VIEW, uri)
                                    intent.setPackage("com.google.android.apps.maps")
                                    context.startActivity(intent)
                                } catch (e: Exception) {
                                    // Fallback: open in browser
                                    val webUri = Uri.parse(
                                        "https://www.google.com/maps/dir/?api=1&destination=${hostel.latitude},${hostel.longitude}&travelmode=walking"
                                    )
                                    context.startActivity(Intent(Intent.ACTION_VIEW, webUri))
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryDarkBlue),
                            shape = RoundedCornerShape(24.dp)
                        ) {
                            Text(
                                text = "Get Directions",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        // My Location Button
                        IconButton(
                            onClick = {
                                // Opens Google Maps centered on hostel location
                                // Falls back to browser if Google Maps not installed
                                try {
                                    val uri = Uri.parse(
                                        "geo:${hostel.latitude},${hostel.longitude}?q=${hostel.latitude},${hostel.longitude}(${hostel.name})"
                                    )
                                    val intent = Intent(Intent.ACTION_VIEW, uri)
                                    intent.setPackage("com.google.android.apps.maps")
                                    context.startActivity(intent)
                                } catch (e: Exception) {
                                    // Fallback: open in browser
                                    val webUri = Uri.parse(
                                        "https://www.google.com/maps/search/?api=1&query=${hostel.latitude},${hostel.longitude}"
                                    )
                                    context.startActivity(Intent(Intent.ACTION_VIEW, webUri))
                                }
                            },
                            modifier = Modifier
                                .size(48.dp)
                                .background(PrimaryDarkBlue, RoundedCornerShape(24.dp))
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "My Location",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    // ---- END ADDED ----
                }
            }
        }
        // ---- END ENHANCED ----

        // ---- ADDED: Floating My Location Button (Map Screen - Feature 5) ----
        FloatingActionButton(
            onClick = {
                // Reset camera to default Ndejje University area
                kotlinx.coroutines.MainScope().launch {
                    cameraPositionState.animate(
                        update = CameraUpdateFactory.newLatLngZoom(
                            LatLng(0.5840, 32.5330), 14f
                        ),
                        durationMs = 800
                    )
                }
            },
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp),
            containerColor = Color.White,
            shape = CircleShape
        ) {
            Icon(
                imageVector = Icons.Default.MyLocation,
                contentDescription = "Center Map",
                tint = PrimaryDarkBlue
            )
        }
        // ---- END ADDED ----
    }
}

// ---- END REWRITTEN ----
