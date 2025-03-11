package com.healyks.app.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.healyks.app.R
import com.healyks.app.view.components.core.CustomButton
import com.healyks.app.view.navigation.HealyksScreens
import `in`.iotkiit.raidersreckoningapp.view.components.login.GoogleButtonTheme
import `in`.iotkiit.raidersreckoningapp.view.components.login.GoogleOneTapButton

@Composable
fun OnBoardingScreen(navController: NavController) {
    val isDarkTheme = isSystemInDarkTheme()

    val vectorRes = if (isDarkTheme) {

        R.drawable.dark_logo // Your dark theme vector
    } else {
        R.drawable.light_logo // Your light theme vector
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeContentPadding(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome to",
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(30.dp))
            Image(
                painter = painterResource(id = vectorRes),
                contentDescription = "Onboarding Illustration",
                modifier = Modifier.size(200.dp)
            )
        }
        Row(
            modifier = Modifier.padding(36.dp)
        ) {

            CustomButton(
                modifier = Modifier.padding(16.dp),
                onClick = { navController.navigate(HealyksScreens.PostUserBodyScreen.route) },
                label = "Google Sign-In",
                copy = 0.6f,
                weight = FontWeight.SemiBold
            )

//            GoogleOneTapButton(
//                theme = if (isDarkTheme) GoogleButtonTheme.Dark else GoogleButtonTheme.Light
//            ) {
//                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                    .requestIdToken("token")
//                    .requestEmail()
//                    .build()
//
//            }
        }
    }
}
