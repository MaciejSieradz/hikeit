package com.example.hikeit.trails.presentation.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.example.hikeit.R
import com.example.hikeit.apollo.AuthorizationInterceptor
import com.example.hikeit.trails.data.security.AppState
import com.example.hikeit.trails.data.security.GoogleUserState
import com.example.hikeit.ui.theme.HikeItTheme
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.util.UUID

@Composable
fun LoginScreen(onSuccessLogin: () -> Unit, modifier: Modifier = Modifier) {

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Hiker",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.padding(vertical = 16.dp))
        GoogleSignInButton(onSuccessLogin)
    }
}

@Composable
fun GoogleSignInButton(onLogin: () -> Unit,modifier: Modifier = Modifier) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val onClick: () -> Unit = {
        val credentialManager = CredentialManager.create(context)

        val rawNone = UUID.randomUUID().toString()
        val bytes = rawNone.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        val hashedNonce = digest.fold("") { str, it -> str + "%02x".format(it) }

        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(context.getString(R.string.client_id))
            .setNonce(hashedNonce)
            .build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        coroutineScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = context
                )
                val credential = result.credential

                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

                val googleIdToken = googleIdTokenCredential.idToken
                AuthorizationInterceptor.token = googleIdToken
                AppState.user = GoogleUserState(
                    email = googleIdTokenCredential.id,
                    name = googleIdTokenCredential.displayName,
                    avatarUrl = googleIdTokenCredential.profilePictureUri
                )
                AppState.token = googleIdToken
                onLogin()
            } catch (e: GetCredentialException) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }

        }
    }

    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 48.dp, end = 48.dp),
        colors = ButtonColors(Color.Black, Color.White, Color.Blue, Color.Blue)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(R.drawable.google),
                contentDescription = "Google Icon",
                modifier = Modifier.size(32.dp),
                tint = Color.Unspecified
            )
            Text("Kontynuuj za pomocą konta Google")
        }
    }
}

@PreviewLightDark
@Composable
private fun LoginScreenPreview() {
    HikeItTheme {
        LoginScreen({})
    }
}

@PreviewLightDark
@Composable
private fun GoogleSignInButtonPreview() {
    HikeItTheme {
        GoogleSignInButton({})
    }
}