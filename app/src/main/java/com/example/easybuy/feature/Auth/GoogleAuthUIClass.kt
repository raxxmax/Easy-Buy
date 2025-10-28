package com.example.easybuy.feature.Auth

import android.content.Context
import android.content.IntentSender
import android.content.Intent
import android.util.Log
import com.example.easybuy.R
import com.example.easybuy.feature.Auth.signin.UserData
import com.example.easybuy.feature.Auth.signin.signInResult
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException

class SigninWithAnIntent(
    private val context: Context,
    private val oneTapClient: SignInClient
) {

    private val auth: FirebaseAuth = Firebase.auth
    private val TAG = "GoogleAuthUIClass"

    suspend fun signIn(): IntentSender? {
        val result = try {
            Log.d(TAG, "Starting Google Sign-In process")
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        } catch (e: Exception) {
            Log.e(TAG, "Error during Google Sign-In initiation", e)
            e.printStackTrace()
            if (e is CancellationException) throw e
            null
        }
        return result?.pendingIntent?.intentSender
    }

    suspend fun getSigInResultFromIntent(intent: Intent) : signInResult {
        return try {
            Log.d(TAG, "Processing Google Sign-In result")
            val credential = oneTapClient.getSignInCredentialFromIntent(intent)
            val googleIdToken = credential.googleIdToken

            if (googleIdToken == null) {
                Log.e(TAG, "Google ID token is null")
                return signInResult(
                    data = null,
                    errorMessage = "Failed to get Google ID token"
                )
            }

            val googleCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
            val authResult = auth.signInWithCredential(googleCredential).await()
            val user = authResult.user

            if (user == null) {
                Log.e(TAG, "Firebase user is null after authentication")
                return signInResult(
                    data = null,
                    errorMessage = "Authentication failed"
                )
            }

            val userData = user.run {
                UserData(
                    userId = uid,
                    userName = displayName,
                    profilePicture = photoUrl?.toString()
                )
            }

            Log.d(TAG, "Google Sign-In successful for user: ${user.displayName}")
            signInResult(
                data = userData,
                errorMessage = null
            )
        } catch (e: Exception) {
            Log.e(TAG, "Error processing Google Sign-In result", e)
            e.printStackTrace()
            if (e is CancellationException) throw e
            signInResult(
                data = null,
                errorMessage = e.message ?: "Unknown authentication error"
            )
        }
    }

    suspend fun signOut() {
        try {
            Log.d(TAG, "Signing out user")
            oneTapClient.signOut().await()
            auth.signOut()

        } catch (e: Exception) {
            Log.e(TAG, "Error during sign out", e)
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

    fun getSignInUser() : UserData? = auth.currentUser?.run {
        UserData(
            userId = uid,
            userName = displayName,
            profilePicture = photoUrl?.toString()
        )
    }
    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)  // Allow both new and existing accounts
                    .setServerClientId(context.getString(R.string.web_client_id))
                    .build()
            )
            .setAutoSelectEnabled(false)  // Don't auto-select to give user choice
            .build()
    }
}

