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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ug.ac.ndejje.ndejjenest.R
import ug.ac.ndejje.ndejjenest.ui.theme.PrimaryDarkBlue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff

import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import ug.ac.ndejje.ndejjenest.ui.theme.PrimaryYellow
import ug.ac.ndejje.ndejjenest.ui.theme.Outfit
import androidx.lifecycle.viewmodel.compose.viewModel
import ug.ac.ndejje.ndejjenest.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel() // This creates the ViewModel automatically
) {
    // --- State Observation ---
    // We observe the "Live" data from our ViewModel here
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val isPasswordVisible by viewModel.isPasswordVisible.collectAsState()

    Scaffold(
        containerColor = Color.White
    ) { padding ->
        // Box allows us to overlap elements, like putting the back button on top of the column
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Feature 1: Back Arrow Button
            IconButton(
                onClick = { 
                    // This command tells the app to "go back" to the previous screen
                    navController.popBackStack() 
                },
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.screen_margin_medium))
                    .align(Alignment.TopStart) // Positions it in the top-left corner
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = PrimaryDarkBlue // Using our brand color for the icon
                )
            }

            // Central content will go here in the next features
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = dimensionResource(id = R.dimen.screen_margin_large)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                // Feature 2: Branding Section
                Spacer(modifier = Modifier.height(40.dp))
                
                // App Logo
                Image(
                    painter = painterResource(id = R.drawable.login_logo),
                    contentDescription = "App Logo",
                    modifier = Modifier.size(180.dp)
                )
                
                // App Name with Multi-color (Ndejje in Blue, Nest in Yellow)
                val annotatedTitle = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = PrimaryDarkBlue)) {
                        append("Ndejje")
                    }
                    withStyle(style = SpanStyle(color = PrimaryYellow)) {
                        append("Nest")
                    }
                }

                Text(
                    text = annotatedTitle,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontFamily = Outfit,
                        fontWeight = FontWeight.Bold
                    )
                )

                Text(
                    text = "Welcome back!",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = Outfit,
                        color = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_extra_large)))

                // Feature 3: Input Fields (Email & Password)
                
                // Email Field
                OutlinedTextField(
                    value = email,
                    onValueChange = { viewModel.onEmailChanged(it) },
                    label = { Text("Email or Phone", fontFamily = Outfit) },
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

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))

                // Password Field
                OutlinedTextField(
                    value = password,
                    onValueChange = { viewModel.onPasswordChanged(it) },
                    label = { Text("Password", fontFamily = Outfit) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = PrimaryDarkBlue)
                    },
                    trailingIcon = {
                        IconButton(onClick = { viewModel.togglePasswordVisibility() }) {
                            val icon = if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility
                            Icon(imageVector = icon, contentDescription = "Toggle Visibility", tint = Color.Gray)
                        }
                    },
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryDarkBlue,
                        focusedLabelColor = PrimaryDarkBlue
                    )
                )

                // Feature 4: Forgot Password
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = { viewModel.onForgotPasswordClicked() }
                    ) {
                        Text(
                            text = "Forgot Password?",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontFamily = Outfit,
                                color = PrimaryDarkBlue,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
                
                // Feature 5: Primary Action (Log In Button)
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_large)))
                
                Button(
                    onClick = { viewModel.onLoginClicked() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryDarkBlue,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Log In",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontFamily = Outfit,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                // Feature 7: Bottom Navigation (Register Link)
                Spacer(modifier = Modifier.weight(1f))
                
                Row(
                    modifier = Modifier.padding(bottom = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Don't have an account?",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = Outfit,
                            color = Color.Gray
                        )
                    )
                    TextButton(
                        onClick = { 
                            viewModel.onRegisterClicked()
                            navController.navigate(ug.ac.ndejje.ndejjenest.navigation.Screen.Register.route) 
                        }
                    ) {
                        Text(
                            text = "Register",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontFamily = Outfit,
                                color = PrimaryDarkBlue,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }
    }
}
