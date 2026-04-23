package ug.ac.ndejje.ndejjenest.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import ug.ac.ndejje.ndejjenest.ui.theme.Outfit
import ug.ac.ndejje.ndejjenest.viewmodel.RegisterViewModel
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
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

    Scaffold(
        containerColor = Color.White
    ) { padding ->
        // Box lets us layer elements, like placing the back button on top of the main column
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Feature 1: Back Arrow Button
            IconButton(
                onClick = {
                    // Goes back to the Login screen
                    navController.popBackStack()
                },
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.screen_margin_medium))
                    .align(Alignment.TopStart) // Positions it in the top-left corner
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = PrimaryDarkBlue // Using our brand color
                )
            }

            // The remaining features (Branding, Fields, Button, Footer)
            // will be added inside this Column
            Column(
                modifier = Modifier
                    .fillMaxSize()
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
                        color = PrimaryDarkBlue
                    )
                )

                Text(
                    text = "Join NdejjeNest today",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = Outfit,
                        fontStyle = FontStyle.Italic,
                        color = Color.Gray
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
                        Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = PrimaryDarkBlue)
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryDarkBlue,
                        focusedLabelColor = PrimaryDarkBlue
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
                        Icon(imageVector = Icons.Default.Email, contentDescription = null, tint = PrimaryDarkBlue)
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryDarkBlue,
                        focusedLabelColor = PrimaryDarkBlue
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
                        Icon(imageVector = Icons.Default.Phone, contentDescription = null, tint = PrimaryDarkBlue)
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryDarkBlue,
                        focusedLabelColor = PrimaryDarkBlue
                    )
                )

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))

                // Features 4–8 will be added here in upcoming steps
            }
        }
    }
}
