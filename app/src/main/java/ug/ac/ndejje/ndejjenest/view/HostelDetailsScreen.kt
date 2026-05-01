package ug.ac.ndejje.ndejjenest.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ug.ac.ndejje.ndejjenest.R
import ug.ac.ndejje.ndejjenest.navigation.Screen
import ug.ac.ndejje.ndejjenest.ui.theme.Outfit
import ug.ac.ndejje.ndejjenest.ui.theme.PrimaryDarkBlue
import ug.ac.ndejje.ndejjenest.ui.theme.PrimaryGreen
import ug.ac.ndejje.ndejjenest.ui.theme.PrimaryYellow
import ug.ac.ndejje.ndejjenest.viewmodel.HostelDetailsViewModel

@Composable
fun HostelDetailsScreen(
    navController: NavController,
    hostelId: String?,
    viewModel: HostelDetailsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val hostel by viewModel.hostel.collectAsState()
    val isSaved by viewModel.isSaved.collectAsState()

    LaunchedEffect(hostelId) {
        hostelId?.let { viewModel.getHostel(it) }
    }

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = padding.calculateBottomPadding())
        ) {
            if (hostel == null) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                val item = hostel!!

                BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                    val isLandscape = maxWidth > maxHeight

                    if (isLandscape) {
                        // ─── LANDSCAPE: Image left | Content right ───
                        Row(modifier = Modifier.fillMaxSize()) {

                            // Left: Image panel (fixed width)
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(0.45f)
                            ) {
                                coil.compose.AsyncImage(
                                    model = item.imageUrl,
                                    contentDescription = item.name,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                                // Gradient overlay
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            Brush.verticalGradient(
                                                colors = listOf(
                                                    Color.Black.copy(alpha = 0.35f),
                                                    Color.Transparent
                                                )
                                            )
                                        )
                                )
                                // Toolbar buttons
                                ToolbarButtons(
                                    isSaved = isSaved,
                                    onBack = { navController.popBackStack() },
                                    onSave = { viewModel.toggleSave() }
                                )
                            }

                            // Right: Scrollable content
                            Column(
                                modifier = Modifier
                                    .weight(0.55f)
                                    .fillMaxHeight()
                                    .verticalScroll(rememberScrollState())
                                    .padding(20.dp)
                            ) {
                                HostelContent(
                                    item = item,
                                    navController = navController
                                )
                            }
                        }
                    } else {
                        // ─── PORTRAIT: Image top | Content scrolls below ───
                        Column(modifier = Modifier.fillMaxSize()) {

                            // Top Image
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(280.dp)
                            ) {
                                coil.compose.AsyncImage(
                                    model = item.imageUrl,
                                    contentDescription = item.name,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(100.dp)
                                        .background(
                                            Brush.verticalGradient(
                                                colors = listOf(
                                                    Color.Black.copy(alpha = 0.4f),
                                                    Color.Transparent
                                                )
                                            )
                                        )
                                )
                                ToolbarButtons(
                                    isSaved = isSaved,
                                    onBack = { navController.popBackStack() },
                                    onSave = { viewModel.toggleSave() }
                                )
                            }

                            // Scrollable content below image
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .verticalScroll(rememberScrollState())
                                    .padding(20.dp)
                            ) {
                                HostelContent(
                                    item = item,
                                    navController = navController
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// ─── Toolbar: Back + Heart ───
@Composable
private fun ToolbarButtons(
    isSaved: Boolean,
    onBack: () -> Unit,
    onSave: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBack,
            modifier = Modifier
                .size(40.dp)
                .background(MaterialTheme.colorScheme.surface, CircleShape)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        IconButton(
            onClick = onSave,
            modifier = Modifier
                .size(40.dp)
                .background(MaterialTheme.colorScheme.surface, CircleShape)
        ) {
            Icon(
                imageVector = if (isSaved) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Save",
                tint = if (isSaved) Color.Red else MaterialTheme.colorScheme.primary
            )
        }
    }
}

// ─── Main scrollable content block ───
@Composable
private fun HostelContent(
    item: ug.ac.ndejje.ndejjenest.model.Hostel,
    navController: NavController
) {
    // Name + Rating row
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.name,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = Outfit
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = item.location,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontFamily = Outfit
                )
            }
        }

        // Rating badge
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
                .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = item.rating.toString(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = Outfit
            )
        }
    }

    Spacer(modifier = Modifier.height(20.dp))

    // Price
    Row(verticalAlignment = Alignment.Bottom) {
        Column {
            Text(
                text = "Price per Semester",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontFamily = Outfit
            )
            Text(
                text = item.price,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary,
                fontFamily = Outfit
            )
        }
    }

    Spacer(modifier = Modifier.height(24.dp))
    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
    Spacer(modifier = Modifier.height(24.dp))

    // Amenities
    Text(
        text = "Amenities",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground,
        fontFamily = Outfit
    )
    Spacer(modifier = Modifier.height(12.dp))
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(end = 4.dp)
    ) {
        items(item.amenities) { amenity ->
            AmenityItem(amenity = amenity)
        }
    }

    Spacer(modifier = Modifier.height(24.dp))
    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
    Spacer(modifier = Modifier.height(24.dp))

    // About
    Text(
        text = "About Hostel",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground,
        fontFamily = Outfit
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = item.description,
        fontSize = 14.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        lineHeight = 22.sp,
        fontFamily = Outfit
    )

    Spacer(modifier = Modifier.height(24.dp))
    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
    Spacer(modifier = Modifier.height(24.dp))

    // Contact
    Text(
        text = "Contact Landlord",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground,
        fontFamily = Outfit
    )
    Spacer(modifier = Modifier.height(12.dp))

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "Call",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Phone",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontFamily = Outfit
                    )
                    Text(
                        text = item.phoneNumber,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontFamily = Outfit
                    )
                }
            }

            // WhatsApp icon
            IconButton(onClick = { /* Handle WhatsApp */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.whatsapp),
                    contentDescription = "WhatsApp",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(24.dp))

    // Map Button
    Button(
        onClick = { navController.navigate(Screen.Map.createRoute(item.id)) },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
        shape = RoundedCornerShape(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Map,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "View on Map",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary,
            fontFamily = Outfit
        )
    }

    Spacer(modifier = Modifier.height(24.dp))
}

// ─── Amenity chip ───
@Composable
fun AmenityItem(amenity: String) {
    val icon = when (amenity.lowercase()) {
        "wifi", "high-speed wifi" -> Icons.Default.Wifi
        "water", "constant water" -> Icons.Default.WaterDrop
        "cctv", "security", "24/7 guard", "gated", "high security" -> Icons.Default.Security
        "electricity", "backup power" -> Icons.Default.Bolt
        "gym" -> Icons.Default.FitnessCenter
        "pool" -> Icons.Default.Pool
        "laundry" -> Icons.Default.LocalLaundryService
        "study hall" -> Icons.Default.School
        else -> Icons.Default.CheckCircle
    }

    Column(
        modifier = Modifier
            .width(80.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp))
            .padding(vertical = 12.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = amenity,
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.primary,
            fontFamily = Outfit,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
