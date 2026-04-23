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
            }
        }
    }
}
