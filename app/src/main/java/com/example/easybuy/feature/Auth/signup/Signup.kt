package com.example.easybuy.feature.Auth.signup

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.easybuy.R
import com.example.easybuy.navigation.NavigationRoutes

@Composable
fun SignUpScreen(navController: NavController) {
    val viewModel: SignUpViewModel = hiltViewModel()
    val uiState = viewModel.state.collectAsState()

    val isDarktheme = isSystemInDarkTheme()
    val textColor = if (isDarktheme) Color.White else Color.Black
    val iconColor = if(isDarktheme) Color.White else Color.Black
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirm by remember { mutableStateOf("") }

    // Google Sign-Up launcher
    val googleSignUpLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { intent ->
                viewModel.handleGoogleSignUpResult(intent)
            }
        } else {
            viewModel.resetState()
            Toast.makeText(context, "Google Sign-Up cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(key1 = uiState.value) {
        when (val state = uiState.value) {
            is SignUpState.Success -> {
                navController.navigate(NavigationRoutes.HOME) {
                    popUpTo(NavigationRoutes.SIGNUP) { inclusive = true }
                }
            }

            is SignUpState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }

            is SignUpState.GoogleSignUpIntentReady -> {
                try {
                    val intentSenderRequest =
                        IntentSenderRequest.Builder(state.intentSender).build()
                    googleSignUpLauncher.launch(intentSenderRequest)
                } catch (e: Exception) {
                    Toast.makeText(context, "Failed to launch Google Sign-Up", Toast.LENGTH_SHORT)
                        .show()
                    viewModel.resetState()
                }
            }

            else -> {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1E2F8E),
                        Color(0xFF60CFDC),
                        Color(0xFFEEB36A)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Title
            Spacer(modifier = Modifier.height(88.dp))
            Text(
                text = "Happy to see you here!",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Sign up to continue",
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Card with form
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFCCEBF8)
                ),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
//                    name field
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Name") },
                        placeholder = { Text("Enter your Name") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Face,
                                contentDescription = "Name" ,

                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp) ,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color(0xFF1B1C1F),      // when typing
                            unfocusedTextColor = Color(0xFF212228),
                            focusedContainerColor = Color(0x0CE9ECEF),
                            unfocusedContainerColor = Color(0x08F9FFFF),
                            unfocusedBorderColor = Color.Gray,
                            focusedBorderColor = Color.DarkGray,
                            focusedLabelColor = Color.Black,
                            unfocusedLabelColor = Color.Gray
                        )
                    )


                    Spacer(modifier = Modifier.height(16.dp))
                    // Email field
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        placeholder = { Text("Enter your email") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = "Email"
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),

                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color(0xFF1B1C1F),      // when typing
                            unfocusedTextColor = Color(0xFF212228),
                            focusedContainerColor = Color(0x0CE9ECEF),
                            unfocusedContainerColor = Color(0x08F9FFFF),
                            unfocusedBorderColor = Color.Gray,
                            focusedBorderColor = Color.DarkGray,
                            focusedLabelColor = Color.Black,
                            unfocusedLabelColor = Color.Gray
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Password field
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        placeholder = { Text("Enter your password") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Password"
                            )
                        },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    painter = painterResource(
                                        id = if (passwordVisible) R.drawable.show
                                        else
                                            R.drawable.closed
                                    ),
                                    contentDescription = if (passwordVisible)
                                        "Hide password"
                                    else
                                        "Show password" ,
                                    tint = Color(0xFF010A11)
                                )

                            }
                        },
                        visualTransformation = if (passwordVisible)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp) ,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color(0xFF1B1C1F),      // when typing
                            unfocusedTextColor = Color(0xFF212228),
                            focusedContainerColor = Color(0x0CE9ECEF),
                            unfocusedContainerColor = Color(0x08F9FFFF),
                            unfocusedBorderColor = Color.Gray,
                            focusedBorderColor = Color.DarkGray,
                            focusedLabelColor = Color.Black,
                            unfocusedLabelColor = Color.Gray
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Password field
                    OutlinedTextField(
                        value = confirm,
                        onValueChange = { confirm = it },
                        placeholder = { Text(" Confirm Password") },
                        label = { Text("Confirm Password") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Password"
                            )
                        },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    painter = painterResource(
                                        id = if (passwordVisible) R.drawable.show
                                        else
                                            R.drawable.closed)   ,
                                    contentDescription = if (passwordVisible)
                                        "Hide password"
                                    else
                                        "Show password",
                                    tint = Color(0xFF010A11)
                                )

                            }
                        },
                        visualTransformation = if (passwordVisible)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp) ,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color(0xFF1B1C1F),      // when typing
                            unfocusedTextColor = Color(0xFF212228),
                            focusedContainerColor = Color(0x0CE9ECEF),
                            unfocusedContainerColor = Color(0x08F9FFFF),
                            unfocusedBorderColor = Color.Gray,
                            focusedBorderColor = Color.DarkGray,
                            focusedLabelColor = Color.Black,
                            unfocusedLabelColor = Color.Gray
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Spacer(modifier = Modifier.height(24.dp))

                    // Sign Up button
                    Button(
                        onClick = {
                            viewModel.signUp(
                                email = email,
                                password = password,
                                confirmPassword = confirm
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6366F1) ),
                        enabled = name.isNotEmpty() && email.isNotEmpty() &&
                                password.isNotEmpty() && confirm.isNotEmpty() && (password == confirm) &&
                                (uiState.value == SignUpState.Nothing || uiState.value is SignUpState.Error)
                    )
                    {
                        if (uiState.value == SignUpState.Loading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                text = "Sign up",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Divider with "OR"
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HorizontalDivider(modifier = Modifier.weight(1f))
                        Text(
                            text = "OR",
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                        HorizontalDivider(modifier = Modifier.weight(1f))
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Google sign in button
                    OutlinedButton(
                        onClick = { viewModel.signUpWithGoogle() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        enabled = uiState.value != SignUpState.Loading
                    ) {
                        if (uiState.value == SignUpState.Loading) {
                            CircularProgressIndicator(
                                color = Color(0xFF6366F1),
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                text = "Continue with Google",
                                fontSize = 16.sp,
                                color = Color.DarkGray
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Sign up link


            TextButton(
                onClick = {navController.popBackStack() },
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = " have an account? Sign in",
                    color = Color.White,
                    fontWeight = FontWeight.Bold ,
                    fontSize = 16.sp,
                    style = TextStyle(textDecoration = TextDecoration.Underline)

                )
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun PreviewSignUpScreen() {
    SignUpScreen(navController = rememberNavController())
}