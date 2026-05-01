package ug.ac.ndejje.ndejjenest.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import ug.ac.ndejje.ndejjenest.R
import ug.ac.ndejje.ndejjenest.ui.theme.PrimaryDarkBlue
import ug.ac.ndejje.ndejjenest.ui.theme.PrimaryYellow
import ug.ac.ndejje.ndejjenest.ui.theme.Outfit
import ug.ac.ndejje.ndejjenest.viewmodel.RegisterViewModel
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import ug.ac.ndejje.ndejjenest.navigation.Screen
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = viewModel() // Automatically creates the ViewModel
) {
    // --- State Observation ---
    val fullName by viewModel.fullName.collectAsState()
    val email by viewModel.email.collectAsState()
    val phoneNumber by viewModel.phoneNumber.collectAsState()
    val password by viewModel.password.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()
    val isPasswordVisible by viewModel.isPasswordVisible.collectAsState()
    val isConfirmPasswordVisible by viewModel.isConfirmPasswordVisible.collectAsState()
    val isTermsAccepted by viewModel.isTermsAccepted.collectAsState()
    
    // --- New States for Firebase ---
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isSuccess by viewModel.isSuccess.collectAsState()

    val context = LocalContext.current

    // Handle Success: Navigate to Home
    LaunchedEffect(isSuccess) {
        if (isSuccess) {
            Toast.makeText(context, "Account Created Successfully!", Toast.LENGTH_SHORT).show()
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Register.route) { inclusive = true }
            }
        }
    }

    // Handle Error: Show Toast
    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            viewModel.clearErrorMessage() // Reset error after showing
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        // Box lets us layer elements, like placing the back button on top of the main column
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // The remaining features (Branding, Fields, Button, Footer)
            // will be added inside this Column
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(androidx.compose.foundation.rememberScrollState())
                    .padding(horizontal = dimensionResource(id = R.dimen.screen_margin_large)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                // Spacer to push content below the back button
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_extra_large)))

                // Feature 2: Header Section
                Text(
                    text = "Create Account",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontFamily = Outfit,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )

                Text(
                    text = "Join NdejjeNest today",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = Outfit,
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_large)))

                // Feature 3: Basic Input Fields
                OutlinedTextField(
                    value = fullName,
                    onValueChange = { viewModel.onFullNameChanged(it) },
                    label = { Text("Full Name", fontFamily = Outfit) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary
                    )
                )

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))

                OutlinedTextField(
                    value = email,
                    onValueChange = { viewModel.onEmailChanged(it) },
                    label = { Text("Email", fontFamily = Outfit) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Email, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary
                    )
                )

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))

                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { viewModel.onPhoneNumberChanged(it) },
                    label = { Text("Phone Number", fontFamily = Outfit) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Phone, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary
                    )
                )

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))

                // Feature 4: Password Fields
                OutlinedTextField(
                    value = password,
                    onValueChange = { viewModel.onPasswordChanged(it) },
                    label = { Text("Password", fontFamily = Outfit) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    },
                    trailingIcon = {
                        IconButton(onClick = { viewModel.togglePasswordVisibility() }) {
                            val icon = if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility
                            Icon(imageVector = icon, contentDescription = "Toggle Visibility", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    },
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary
                    )
                )

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { viewModel.onConfirmPasswordChanged(it) },
                    label = { Text("Confirm Password", fontFamily = Outfit) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    },
                    trailingIcon = {
                        IconButton(onClick = { viewModel.toggleConfirmPasswordVisibility() }) {
                            val icon = if (isConfirmPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility
                            Icon(imageVector = icon, contentDescription = "Toggle Visibility", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    },
                    visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary
                    )
                )

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))

                // Feature 5: Terms & Conditions
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isTermsAccepted,
                        onCheckedChange = { viewModel.onTermsCheckedChanged(it) },
                        colors = CheckboxDefaults.colors(
                            checkedColor = MaterialTheme.colorScheme.primary
                        )
                    )
                    Text(
                        text = "I agree to the Terms & Conditions",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = Outfit,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_large)))

                // Feature 6: Sign Up Button
                Button(
                    onClick = { viewModel.onRegisterClicked() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    enabled = isTermsAccepted
                ) {
                    Text(
                        text = "Sign Up",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontFamily = Outfit,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Spacer(modifier = Modifier.weight(1f)) // Pushes the footer to the bottom

                // Feature 7: Bottom Navigation
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = dimensionResource(id = R.dimen.spacing_large)),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Already have an account?",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = Outfit,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                    TextButton(onClick = { 
                        viewModel.onLoginClicked()
                        navController.popBackStack() 
                    }) {
                        Text(
                            text = "Log In",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontFamily = Outfit,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }

            // Feature 1: Back Arrow Button (Moved to top of Z-stack)
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.screen_margin_medium))
                    .align(Alignment.TopStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            // Feature 8: Loading Overlay
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    // Transparent dark background to dim the screen
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = Color.Black.copy(alpha = 0.3f)
                    ) {}
                    
                    // The spinner
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}
