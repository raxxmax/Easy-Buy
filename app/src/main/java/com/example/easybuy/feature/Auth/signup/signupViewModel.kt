package com.example.easybuy.feature.Auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easybuy.data.repository.UserRepository
import com.example.easybuy.feature.Auth.SigninWithAnIntent
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val googleAuthClient: SigninWithAnIntent,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _state = MutableStateFlow<SignUpState>(SignUpState.Nothing)
    val state = _state.asStateFlow()

    private val auth = FirebaseAuth.getInstance()

    fun signUp(email: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            _state.value = SignUpState.Loading

            // Basic validation
            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                _state.value = SignUpState.Error("All fields are required")
                return@launch
            }

            if (password != confirmPassword) {
                _state.value = SignUpState.Error("Passwords do not match")
                return@launch
            }

            if (password.length < 6) {
                _state.value = SignUpState.Error("Password must be at least 6 characters")
                return@launch
            }

            // Firebase signup
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = task.result.user
                        if (user != null) {
                            // Create/update user profile in Firestore
                            viewModelScope.launch {
                                val result = userRepository.createOrUpdateUserFromFirebaseAuth(user)
                                if (result.isSuccess) {
                                    _state.value = SignUpState.Success
                                } else {
                                    _state.value =
                                        SignUpState.Error("Failed to create user profile")
                                }
                            }
                        } else {
                            _state.value = SignUpState.Error("Sign up failed")
                        }
                    } else {
                        _state.value =
                            SignUpState.Error(task.exception?.message ?: "Sign up failed")
                    }
                }
        }
    }

    fun signUpWithGoogle() {
        viewModelScope.launch {
            _state.value = SignUpState.Loading
            try {
                val intentSender = googleAuthClient.signIn()
                if (intentSender == null) {
                    _state.value = SignUpState.Error("Failed to initiate Google Sign-Up")
                    return@launch
                }
                _state.value = SignUpState.GoogleSignUpIntentReady(intentSender)
            } catch (e: Exception) {
                _state.value =
                    SignUpState.Error("Google Sign-Up error: ${e.message ?: "Unknown error"}")
            }
        }
    }

    fun handleGoogleSignUpResult(intent: android.content.Intent) {
        viewModelScope.launch {
            _state.value = SignUpState.Loading
            try {
                val result = googleAuthClient.getSigInResultFromIntent(intent)
                when {
                    result.data != null -> {
                        // Get the Firebase user and create/update profile
                        val firebaseUser = auth.currentUser
                        if (firebaseUser != null) {
                            val userResult =
                                userRepository.createOrUpdateUserFromFirebaseAuth(firebaseUser)
                            if (userResult.isSuccess) {
                                _state.value = SignUpState.Success
                            } else {
                                _state.value = SignUpState.Error("Failed to create user profile")
                            }
                        } else {
                            _state.value =
                                SignUpState.Error("Firebase user not found after Google Sign-Up")
                        }
                    }

                    !result.errorMessage.isNullOrEmpty() -> {
                        _state.value = SignUpState.Error(result.errorMessage)
                    }

                    else -> {
                        _state.value = SignUpState.Error("Unknown Google Sign-Up result error")
                    }
                }
            } catch (e: Exception) {
                _state.value =
                    SignUpState.Error("Failed to process Google Sign-Up: ${e.message ?: "Unknown error"}")
            }
        }
    }

    fun resetState() {
        _state.value = SignUpState.Nothing
    }
}

sealed class SignUpState {
    object Nothing : SignUpState()
    object Loading : SignUpState()
    object Success : SignUpState()
    data class Error(val message: String) : SignUpState()
    data class GoogleSignUpIntentReady(val intentSender: android.content.IntentSender) :
        SignUpState()
}