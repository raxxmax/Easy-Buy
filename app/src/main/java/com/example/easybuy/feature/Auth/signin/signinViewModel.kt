package com.example.easybuy.feature.Auth.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easybuy.data.repository.UserRepository
import com.example.easybuy.feature.Auth.SigninWithAnIntent
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.launch
import kotlinx.coroutines.Job
import javax.inject.Inject

@HiltViewModel
class SignInViewmodel @Inject constructor(
    private val googleAuthClient: SigninWithAnIntent,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _state = MutableStateFlow<SignInState>(SignInState.Nothing)
    val state = _state.asStateFlow()

    private val auth = FirebaseAuth.getInstance()
    private var currentSignInJob: Job? = null

    init {
        // Check if user is already signed in
        try {
            checkCurrentUser()
        } catch (e: Exception) {
            _state.value = SignInState.Error("Initialization error: ${e.message}")
        }
    }

    private fun checkCurrentUser() {
        val currentUser = googleAuthClient.getSignInUser()
        if (currentUser != null) {
            _state.value = SignInState.Success
        }
    }

    fun SignIn(email: String, password: String) {
        // Cancel any ongoing sign-in operation
        currentSignInJob?.cancel()

        currentSignInJob = viewModelScope.launch {
            try {
                _state.value = SignInState.Loading

                // Basic validation
                if (email.isEmpty() || password.isEmpty()) {
                    _state.value = SignInState.Error("All fields are required")
                    return@launch
                }

                val authResult = withTimeout(20000) {
                auth.signInWithEmailAndPassword(email, password).await()
                }

                val user = authResult.user
                if (user != null) {
                    val result = userRepository.createOrUpdateUserFromFirebaseAuth(user)
                    if (result.isSuccess) {
                        _state.value = SignInState.Success
                    } else {
                        _state.value =
                            SignInState.Error("Failed to create user profile: ${result.exceptionOrNull()?.message}")
                    }
                } else {
                    _state.value = SignInState.Error("Authentication failed")
                }
            } catch (e: TimeoutCancellationException) {
                _state.value =
                    SignInState.Error("Sign in timed out. Please check your connection and try again.")
            } catch (e: Exception) {
                _state.value = SignInState.Error(e.message ?: "Sign in failed")
            }
        }
    }

    fun signInWithGoogle() {
        // Cancel any ongoing sign-in operation
        currentSignInJob?.cancel()

        currentSignInJob = viewModelScope.launch {
            try {
                _state.value = SignInState.Loading
                val intentSender = googleAuthClient.signIn()
                if (intentSender == null) {
                    _state.value = SignInState.Error("Failed to initiate Google Sign-In")
                    return@launch
                }
                _state.value = SignInState.GoogleSignInIntentReady(intentSender)
            } catch (e: Exception) {
                _state.value =
                    SignInState.Error("Google Sign-In error: ${e.message ?: "Unknown error"}")
            }
        }
    }

    fun handleGoogleSignInResult(intent: android.content.Intent) {
        // Cancel any ongoing sign-in operation
        currentSignInJob?.cancel()

        currentSignInJob = viewModelScope.launch {
            try {
                _state.value = SignInState.Loading
                val result = googleAuthClient.getSigInResultFromIntent(intent)
                when {
                    result.data != null -> {
                        // Get the Firebase user and create/update profile
                        val firebaseUser = auth.currentUser
                        if (firebaseUser != null) {
                            val userResult =
                                userRepository.createOrUpdateUserFromFirebaseAuth(firebaseUser)
                            if (userResult.isSuccess) {
                                _state.value = SignInState.Success } else {
                                _state.value =
                                    SignInState.Error("Failed to create user profile: ${userResult.exceptionOrNull()?.message}")
                            }
                        } else {
                            _state.value =
                                SignInState.Error("Firebase user not found after Google Sign-In")
                        }
                    }

                    !result.errorMessage.isNullOrEmpty() -> {
                        _state.value = SignInState.Error(result.errorMessage)
                    }

                    else -> {
                        _state.value = SignInState.Error("Unknown Google Sign-In result error")
                    }
                }
            } catch (e: Exception) {
                _state.value =
                    SignInState.Error("Failed to process Google Sign-In: ${e.message ?: "Unknown error"}")
            }
        }
    }

    fun resetState() {
        currentSignInJob?.cancel()
        _state.value = SignInState.Nothing
    }

    override fun onCleared() {
        super.onCleared()
        currentSignInJob?.cancel()
    }
}

data class signInResult(
    val data: UserData?,
    val errorMessage: String?
)

data class UserData(
    val userId: String,
    val userName: String?,
    val profilePicture: String?
)

sealed class SignInState {
    object Nothing : SignInState()
    object Loading : SignInState()
    object Success : SignInState()
    data class Error(val message: String) : SignInState()
    data class GoogleSignInIntentReady(val intentSender: android.content.IntentSender) : SignInState()
}