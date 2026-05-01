package ug.ac.ndejje.ndejjenest.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ug.ac.ndejje.ndejjenest.ui.theme.Outfit
import ug.ac.ndejje.ndejjenest.ui.theme.PrimaryDarkBlue
import ug.ac.ndejje.ndejjenest.ui.theme.PrimaryGreen
import ug.ac.ndejje.ndejjenest.viewmodel.EditProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    navController: NavController,
    viewModel: EditProfileViewModel = viewModel()
) {
    val fullName by viewModel.fullName.collectAsState()
    val phoneNumber by viewModel.phoneNumber.collectAsState()
    val email by viewModel.email.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isSuccess by viewModel.isSuccess.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    val context = LocalContext.current

    // Handle Success
    LaunchedEffect(isSuccess) {
        if (isSuccess) {
            Toast.makeText(context, "Profile updated successfully!", Toast.LENGTH_SHORT).show()
            viewModel.resetSuccess()
            navController.popBackStack()
        }
    }

    // Handle Error
    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            viewModel.clearError()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Edit Profile",
                        fontFamily = Outfit,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PrimaryDarkBlue)
            )
        },
        containerColor = Color(0xFFF5F5F5)
    ) { padding ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            val isLandscape = maxWidth > maxHeight

            if (isLandscape) {
                // --- LANDSCAPE: Side-by-side layout ---
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalArrangement = Arrangement.spacedBy(32.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Left: Avatar
                    Column(
                        modifier = Modifier.weight(0.4f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        AvatarSection()
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = fullName.ifBlank { "Your Name" },
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryDarkBlue,
                            fontFamily = Outfit
                        )
                        Text(
                            text = email,
                            fontSize = 13.sp,
                            color = Color.Gray,
                            fontFamily = Outfit
                        )
                    }

                    // Right: Form fields + Button
                    Column(
                        modifier = Modifier
                            .weight(0.6f)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        EditFormFields(
                            fullName = fullName,
                            phoneNumber = phoneNumber,
                            email = email,
                            onFullNameChanged = { viewModel.onFullNameChanged(it) },
                            onPhoneNumberChanged = { viewModel.onPhoneNumberChanged(it) }
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        SaveButton(isLoading = isLoading, onClick = { viewModel.updateProfile() })
                    }
                }
            } else {
                // --- PORTRAIT: Stacked layout ---
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(32.dp))

                    AvatarSection()

                    Spacer(modifier = Modifier.height(32.dp))

                    EditFormFields(
                        fullName = fullName,
                        phoneNumber = phoneNumber,
                        email = email,
                        onFullNameChanged = { viewModel.onFullNameChanged(it) },
                        onPhoneNumberChanged = { viewModel.onPhoneNumberChanged(it) }
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    SaveButton(isLoading = isLoading, onClick = { viewModel.updateProfile() })

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

@Composable
private fun AvatarSection() {
    Box(
        modifier = Modifier.size(110.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(3.dp, PrimaryGreen, CircleShape)
                .clip(CircleShape)
                .background(Color.Gray.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.size(55.dp),
                tint = Color.Gray
            )
        }

        // Camera badge
        Surface(
            modifier = Modifier.size(34.dp),
            shape = CircleShape,
            color = PrimaryDarkBlue,
            shadowElevation = 4.dp
        ) {
            Icon(
                imageVector = Icons.Default.CameraAlt,
                contentDescription = "Change Picture",
                tint = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
private fun EditFormFields(
    fullName: String,
    phoneNumber: String,
    email: String,
    onFullNameChanged: (String) -> Unit,
    onPhoneNumberChanged: (String) -> Unit
) {
    // Full Name
    OutlinedTextField(
        value = fullName,
        onValueChange = onFullNameChanged,
        label = { Text("Full Name", fontFamily = Outfit) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        leadingIcon = {
            Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = PrimaryDarkBlue)
        },
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedBorderColor = PrimaryDarkBlue,
            focusedLabelColor = PrimaryDarkBlue
        )
    )

    Spacer(modifier = Modifier.height(4.dp))

    // Phone Number
    OutlinedTextField(
        value = phoneNumber,
        onValueChange = onPhoneNumberChanged,
        label = { Text("Phone Number", fontFamily = Outfit) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        leadingIcon = {
            Icon(imageVector = Icons.Default.Phone, contentDescription = null, tint = PrimaryDarkBlue)
        },
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedBorderColor = PrimaryDarkBlue,
            focusedLabelColor = PrimaryDarkBlue
        )
    )

    Spacer(modifier = Modifier.height(4.dp))

    // Email (read-only)
    OutlinedTextField(
        value = email,
        onValueChange = { },
        label = { Text("Email (cannot be changed)", fontFamily = Outfit) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        leadingIcon = {
            Icon(imageVector = Icons.Default.Email, contentDescription = null, tint = Color.Gray)
        },
        enabled = false,
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            disabledContainerColor = Color.White,
            disabledBorderColor = Color.LightGray,
            disabledLabelColor = Color.Gray,
            disabledTextColor = Color.Gray
        )
    )
}

@Composable
private fun SaveButton(isLoading: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryDarkBlue,
            contentColor = Color.White
        ),
        enabled = !isLoading
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.size(24.dp),
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = "Save Changes",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = Outfit,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}
