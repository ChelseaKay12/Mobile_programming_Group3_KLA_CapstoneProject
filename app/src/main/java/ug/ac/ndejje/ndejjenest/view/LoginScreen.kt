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
                // Placeholder for logo and form
                Spacer(modifier = Modifier.height(100.dp))
                Text(text = "Feature 1: Back Button Implemented", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
