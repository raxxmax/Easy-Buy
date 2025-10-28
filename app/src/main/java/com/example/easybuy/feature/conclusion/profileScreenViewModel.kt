package com.example.easybuy.feature.conclusion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easybuy.data.model.User
import com.example.easybuy.data.repository.UserRepository
import com.example.easybuy.feature.Auth.SigninWithAnIntent
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val auth: FirebaseAuth,
    private val googleAuthClient: SigninWithAnIntent
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    init {
        loadUserProfile()
    }

    fun loadUserProfile() {
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading
            try {
                val currentUser = userRepository.getCurrentUser()
                if (currentUser != null) {
                    _user.value = currentUser
                    _uiState.value = ProfileUiState.Success
                } else {
                    _uiState.value = ProfileUiState.Error("User profile not found")
                }
            } catch (e: Exception) {
                _uiState.value = ProfileUiState.Error(e.message ?: "Failed to load profile")
            }
        }
    }

    fun updateProfile(
        displayName: String,
        firstName: String,
        lastName: String,
        bio: String,
        location: String,
        phoneNumber: String
    ) {
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading
            try {
                val currentUserId = auth.currentUser?.uid
                if (currentUserId != null) {
                    val result = userRepository.updateUserProfile(
                        userId = currentUserId,
                        displayName = displayName.ifBlank { null },
                        firstName = firstName.ifBlank { null },
                        lastName = lastName.ifBlank { null },
                        bio = bio.ifBlank { null },
                        location = location.ifBlank { null },
                        phoneNumber = phoneNumber.ifBlank { null }
                    )

                    if (result.isSuccess) {
                        _user.value = result.getOrNull()
                        _uiState.value = ProfileUiState.Success
                    } else {
                        _uiState.value = ProfileUiState.Error("Failed to update profile")
                    }
                } else {
                    _uiState.value = ProfileUiState.Error("User not authenticated")
                }
            } catch (e: Exception) {
                _uiState.value = ProfileUiState.Error(e.message ?: "Failed to update profile")
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            try {
                // Sign out from Google
                googleAuthClient.signOut()
                // Sign out from Firebase
                auth.signOut()
                _user.value = null
                _uiState.value = ProfileUiState.SignedOut
            } catch (e: Exception) {
                _uiState.value = ProfileUiState.Error("Failed to sign out: ${e.message}")
            }
        }
    }

    fun refreshProfile() {
        loadUserProfile()
    }

    fun resetState() {
        _uiState.value = ProfileUiState.Success
    }

    // Helper functions for profile data
    fun getDisplayName(): String = _user.value?.displayName ?: "User"
    fun getEmail(): String = _user.value?.email ?: ""
    fun getPhoneNumber(): String = _user.value?.phoneNumber ?: ""
    fun getLocation(): String = _user.value?.location ?: ""
    fun getBio(): String = _user.value?.bio ?: "No bio available"
    fun getProfilePictureUrl(): String = _user.value?.profilePictureUrl ?: ""
    fun getAuthProvider(): String = _user.value?.authProvider ?: ""
    fun isEmailVerified(): Boolean = _user.value?.isEmailVerified ?: false
}

sealed class ProfileUiState {
    object Loading : ProfileUiState()
    object Success : ProfileUiState()
    object SignedOut : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
}