package com.healyks.app.view.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.healyks.app.BuildConfig
import com.healyks.app.R
import com.healyks.app.state.UiState
import com.healyks.app.view.components.core.CustomButton
import com.healyks.app.view.navigation.HealyksScreens
import com.healyks.app.vm.UserViewModel
import `in`.iotkiit.raidersreckoningapp.view.components.login.GoogleButtonTheme
import `in`.iotkiit.raidersreckoningapp.view.components.login.GoogleOneTapButton
import `in`.iotkiit.raidersreckoningapp.view.components.login.rememberFirebaseAuthLauncher

@Composable
fun OnBoardingScreen(
    navController: NavController,
    userViewModel: UserViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val verifyTokenState = userViewModel.verifyTokenState.collectAsState().value
    var resetTrigger by remember { mutableIntStateOf(0) }
    val token = BuildConfig.FIREBASE_TOKEN

    val launcher = rememberFirebaseAuthLauncher(
        onAuthComplete = { result ->
            result.user?.getIdToken(true)?.addOnSuccessListener { tokenResult ->
                val idToken = tokenResult.token
                if (idToken != null) {
                    Log.d("Google Auth", "Token: $idToken")
                    userViewModel.verifyToken(idToken)
                } else {
                    Toast.makeText(context, "Login Failed!", Toast.LENGTH_LONG).show()
                    resetTrigger++
                }
            }?.addOnFailureListener {
                Toast.makeText(context, "Failed to fetch ID token!", Toast.LENGTH_LONG).show()
                resetTrigger++
            }
        },
        onAuthError = { exception ->
            Log.e("Google Auth", "Authentication failed", exception)
            Toast.makeText(context, "Login Failed! Please try again.", Toast.LENGTH_LONG).show()
            resetTrigger++
        }
    )

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

            GoogleOneTapButton(
                theme = if (isDarkTheme) GoogleButtonTheme.Dark else GoogleButtonTheme.Light
            ) {
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(token)
                    .requestEmail()
                    .build()

                val googleSignInClient = GoogleSignIn.getClient(context, gso)

                googleSignInClient.signOut().addOnCompleteListener {
                    googleSignInClient.revokeAccess().addOnCompleteListener {
                        Log.d("LoginScreen", "Signed out and access revoked.")
                        launcher.launch(googleSignInClient.signInIntent)
                    }
                }
            }
        }
    }

    LaunchedEffect(verifyTokenState) {
        when (verifyTokenState) {
            is UiState.Success -> {
                navController.navigate(HealyksScreens.PostUserBodyScreen.route) {
                    popUpTo(HealyksScreens.OnBoardingScreen.route) { inclusive = true }
                }
                userViewModel.resetVerifyTokenState()
            }

            is UiState.Failed -> {
                Firebase.auth.signOut()
                Toast.makeText(context, "Login Failed! Please try again.", Toast.LENGTH_LONG).show()
                userViewModel.resetVerifyTokenState()
            }

            else -> {}
        }
    }
}
