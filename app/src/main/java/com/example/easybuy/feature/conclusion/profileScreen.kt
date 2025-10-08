package com.example.easybuy.feature.conclusion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
// Color Theme
val BackgroundDark = Color(0xFF1A1A1A)
val SurfaceDark = Color(0xFF252525)
val BorderDark = Color(0xFF3A3A3A)
val PrimaryOrange = Color(0xFFE7AD60)
val PrimaryCyan = Color(0xFF35B5C4)
val PrimaryBlue = Color(0xFF304AD9)

@OptIn(ExperimentalMaterial3Api::class)


@Composable
fun ProfileScreen(navController: NavController) {

        var isEditing by remember { mutableStateOf(false) }
        var name by remember { mutableStateOf("Your Name") }
        var email by remember { mutableStateOf("test@example.com") }
        var phone by remember { mutableStateOf("888-888-8888") }
        var location by remember { mutableStateOf("San Francisco") }
        var bio by remember { mutableStateOf("Write your bio here") }

        var tempName by remember { mutableStateOf(name) }
        var tempEmail by remember { mutableStateOf(email) }
        var tempPhone by remember { mutableStateOf(phone) }
        var tempLocation by remember { mutableStateOf(location) }
        var tempBio by remember { mutableStateOf(bio) }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Profile", fontSize = 24.sp, fontWeight = FontWeight.SemiBold) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = SurfaceDark,
                        titleContentColor = Color.White
                    )
                )
            },
            containerColor = BackgroundDark
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
            ) {
                // Profile Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceDark)
                ) {
                    Column {
                        // Cover Section with Gradient
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(PrimaryOrange, PrimaryBlue, PrimaryBlue)
                                    )
                                )
                        )

                        // Profile Picture
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 24.dp)
                                .offset(y = (-48).dp)
                        ) {
                            Box {
                                Box(
                                    modifier = Modifier
                                        .size(96.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF2A2A2A)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = "Profile",
                                        modifier = Modifier.size(48.dp),
                                        tint = Color.Gray
                                    )
                                }

                                // Camera Button
                                IconButton(
                                    onClick = { /* Handle photo change */ },
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .size(32.dp)
                                        .background(PrimaryOrange, CircleShape)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CameraAlt,
                                        contentDescription = "Change photo",
                                        tint = Color.White,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }

                        // Profile Info Section
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp)
                                .offset(y = (-32).dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {
                                if (isEditing) {
                                    OutlinedTextField(
                                        value = tempName,
                                        onValueChange = { tempName = it },
                                        modifier = Modifier.weight(1f),
                                        textStyle = LocalTextStyle.current.copy(
                                            fontSize = 28.sp,
                                            fontWeight = FontWeight.Bold
                                        ),
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedBorderColor = PrimaryOrange,
                                            unfocusedBorderColor = BorderDark,
                                            focusedTextColor = Color.White,
                                            unfocusedTextColor = Color.White
                                        )
                                    )
                                } else {
                                    Text(
                                        text = name,
                                        fontSize = 28.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        modifier = Modifier.weight(1f)
                                    )
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                if (!isEditing) {
                                    Button(
                                        onClick = {
                                            isEditing = true
                                            tempName = name
                                            tempEmail = email
                                            tempPhone = phone
                                            tempLocation = location
                                            tempBio = bio
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = PrimaryOrange
                                        ),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = "Edit",
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("Edit Profile")
                                    }
                                } else {
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Button(
                                            onClick = {
                                                tempName = name
                                                tempEmail = email
                                                tempPhone = phone
                                                tempLocation = location
                                                tempBio = bio
                                                isEditing = false
                                            },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color(0xFF3A3A3A)
                                            ),
                                            shape = RoundedCornerShape(8.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Close,
                                                contentDescription = "Cancel",
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }

                                        Button(
                                            onClick = {
                                                name = tempName
                                                email = tempEmail
                                                phone = tempPhone
                                                location = tempLocation
                                                bio = tempBio
                                                isEditing = false
                                            },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = PrimaryOrange
                                            ),
                                            shape = RoundedCornerShape(8.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = "Save",
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            // Bio
                            if (isEditing) {
                                OutlinedTextField(
                                    value = tempBio,
                                    onValueChange = { tempBio = it },
                                    modifier = Modifier.fillMaxWidth(),
                                    label = { Text("Bio") },
                                    minLines = 2,
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = PrimaryOrange,
                                        unfocusedBorderColor = BorderDark,
                                        focusedTextColor = Color.White,
                                        unfocusedTextColor = Color.White,
                                        focusedLabelColor = PrimaryOrange,
                                        unfocusedLabelColor = Color.Gray
                                    )
                                )
                            } else {
                                Text(
                                    text = bio,
                                    fontSize = 14.sp,
                                    color = Color.Gray,
                                    lineHeight = 20.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            // Contact Info
                            ProfileInfoItem(
                                icon = Icons.Default.Email,
                                value = if (isEditing) tempEmail else email,
                                isEditing = isEditing,
                                onValueChange = { tempEmail = it }
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            ProfileInfoItem(
                                icon = Icons.Default.Phone,
                                value = if (isEditing) tempPhone else phone,
                                isEditing = isEditing,
                                onValueChange = { tempPhone = it }
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            ProfileInfoItem(
                                icon = Icons.Default.LocationOn,
                                value = if (isEditing) tempLocation else location,
                                isEditing = isEditing,
                                onValueChange = { tempLocation = it }
                            )

                            Spacer(modifier = Modifier.height(24.dp))
                        }
                    }
                }

                // Menu Items
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    MenuButton(
                        icon = Icons.Default.Notifications,
                        label = "Notifications",
                        onClick = { /* Handle notifications */ }
                    )

                    MenuButton(
                        icon = Icons.Default.Security,
                        label = "Privacy & Security",
                        onClick = { /* Handle privacy */ }
                    )

                    MenuButton(
                        icon = Icons.Default.Settings,
                        label = "Account Settings",
                        onClick = { /* Handle settings */ }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    MenuButton(
                        icon = Icons.Default.Logout,
                        label = "Log Out",
                        onClick = { /* Handle logout */ },
                        textColor = Color(0xFFEF4444)
                    )

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }

    @Composable
    fun ProfileInfoItem(
        icon: ImageVector,
        value: String,
        isEditing: Boolean,
        onValueChange: (String) -> Unit
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = PrimaryOrange,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            if (isEditing) {
                OutlinedTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryOrange,
                        unfocusedBorderColor = BorderDark,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    singleLine = true
                )
            } else {
                Text(
                    text = value,
                    fontSize = 15.sp,
                    color = Color.White
                )
            }
        }
    }

    @Composable
    fun MenuButton(
        icon: ImageVector,
        label: String,
        onClick: () -> Unit,
        textColor: Color = Color.White
    ) {
        Card(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceDark)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = textColor,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = label,
                    fontSize = 16.sp,
                    color = textColor,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Navigate",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }

@Preview(showSystemUi = true)
@Composable
fun PreviewProfileScreen() {
   ProfileScreen(navController = rememberNavController())
}