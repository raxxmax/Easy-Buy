package com.example.easybuy.feature.home

import android.R.attr.angle
import android.R.attr.label
import android.R.attr.onClick
import android.R.attr.radius
import android.R.attr.text
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.AlertDialogDefaults.titleContentColor
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.easybuy.feature.Auth.signup.SignUpScreen
import com.example.easybuy.R
import kotlin.math.cos
import kotlin.math.sin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController :NavController) {
    var linkText by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf(0) }

    //  Detect if dark mode is enabled
    val isDarkTheme = isSystemInDarkTheme()

    //  Dynamic colors based on theme
    val backgroundColor = if (isDarkTheme) Color(0xFF121212) else Color(0xFFF5F5F5)
    val cardColor = if (isDarkTheme) Color(0xFF1E1E1E) else Color.White
    val textPrimaryColor = if (isDarkTheme) Color(0xFFE0E0E0) else Color(0xFF1F2937)
    val textSecondaryColor = if (isDarkTheme) Color(0xFFB0B0B0) else Color.Gray
    val borderColor = if (isDarkTheme) Color(0xFF3A3A3A) else Color.LightGray

    val ccolors = listOf(
        Color(0xFF1E2F8E),
        Color(0xFF60CFDC),
        Color(0xFFEEB36A)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row (modifier = Modifier.fillMaxWidth() , verticalAlignment = Alignment.CenterVertically){
                        Image(painter = painterResource(id = R.drawable.easybuyicon), contentDescription = "Logo"
                            , modifier = Modifier.shadow(
                                elevation = 8.dp ,
                                shape = CircleShape
                            ) )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Easy Buy",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 22.sp
                        )
                    }
                } ,  colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent ,
                    titleContentColor = Color.White
                )
                ,
                modifier = Modifier.background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFEEB36A),
                            Color(0xFF1E2F8E)
                        )
                    )
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it },
                navController = navController
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(backgroundColor)
        ) {
            // Link Input Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = cardColor
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = "Find Best Deal",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = textPrimaryColor
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Paste your product link to find the best price",
                        fontSize = 14.sp,
                        color = textPrimaryColor
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = linkText,
                        onValueChange = { linkText = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Paste product link here...") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Link"
                            )
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF6366F1),
                            unfocusedBorderColor = Color.LightGray
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { /* Handle link submission */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFD29F5C)
                        ),
                        enabled = linkText.isNotEmpty()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Find Best Price",
                            fontSize = 16.sp,
                            color = textPrimaryColor,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            // Trending Products Section
            Text(
                text = "Products you might like",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                color = textPrimaryColor
            )

            // Product Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(6) { index ->
                    ProductCard()
                }
            }
        }
    }
}

@Composable
public fun ProductCard() {

    //  Detect if dark mode is enabled
    val isDarkTheme = isSystemInDarkTheme()

    //  Dynamic colors based on theme
    val backgroundColor = if (isDarkTheme) Color(0xFF121212) else Color(0xFFF5F5F5)
    val cardColor = if (isDarkTheme) Color(0xFF1E1E1E) else Color.White
    // 🌟 Shimmer animation state
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")


    val shimmerTranslate by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Placeholder for product image with shimmer
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFFE5E7EB),
                                Color(0xFFF3F4F6),
                                Color(0xFFE5E7EB)
                            ),
                            start = androidx.compose.ui.geometry.Offset(
                                shimmerTranslate - 200f,
                                shimmerTranslate - 200f
                            ),
                            end = androidx.compose.ui.geometry.Offset(
                                shimmerTranslate,
                                shimmerTranslate
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Product",
                    modifier = Modifier
                        .size(40.dp)
                        .alpha(0.3f),
                    tint = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Placeholder for product name with shimmer
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFFE5E7EB),
                                Color(0xFFF3F4F6),
                                Color(0xFFE5E7EB)
                            ),
                            start = androidx.compose.ui.geometry.Offset(
                                shimmerTranslate - 200f,
                                shimmerTranslate - 200f
                            ),
                            end = androidx.compose.ui.geometry.Offset(
                                shimmerTranslate,
                                shimmerTranslate
                            )
                        )
                    )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Placeholder for price with shimmer
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .height(20.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFFD1D5DB),
                                Color(0xFFE5E7EB),
                                Color(0xFFD1D5DB)
                            ),
                            start = androidx.compose.ui.geometry.Offset(
                                shimmerTranslate - 200f,
                                shimmerTranslate - 200f
                            ),
                            end = androidx.compose.ui.geometry.Offset(
                                shimmerTranslate,
                                shimmerTranslate
                            )
                        )
                    )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Coming Soon",
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun BottomNavigationBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .graphicsLayer {
                clip = false
            }
            .background(Color.White)
    ) {
        RadialMenuScreen(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-40).dp)
                .zIndex(2f),
            navController = navController
        )
    }
}

@Composable
fun RadialMenuScreen(modifier: Modifier = Modifier, navController: NavController) {
    Box(
        modifier = modifier
            .background(Color.Transparent)
    ) {
        var isExpanded by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // 🔹 Menu items with their navigation routes
            data class MenuItem(val emoji: String, val route: String, val label: String)

            val menuItems = listOf(
                MenuItem("❤️", "favorites", "Favorites"),  // ❤️ → FavoritesScreen
                MenuItem("👤", "profile", "Profile"),      // 👤 → ProfileScreen
                MenuItem("⚙️", "settings", "Settings")     // ⚙️ → SettingsScreen
            )

            // 🔹 Loop to create circular menu items with individual navigation
            menuItems.forEachIndexed { index, menuItem ->
                RadialMenuItem(
                    modifier = Modifier.shadow(
                        elevation = 48.dp,
                        shape = RoundedCornerShape(12.dp),
                        clip = false
                    ),
                    isExpanded = isExpanded,
                    angle = index * (270f / menuItems.size),
                    emoji = menuItem.emoji,
                    radius = 80f,
                    onClick = {
                        // 🎯 Each button navigates to different screen:
                        // ❤️ button → navController.navigate("favorites")
                        // 👤 button → navController.navigate("profile")
                        // ⚙️ button → navController.navigate("settings")
                        navController.navigate(menuItem.route)
                        isExpanded = false // Close menu after navigation
                    }
                )
            }

            // 🔹 Center button (toggle button)
            Button(
                onClick = { isExpanded = !isExpanded },
                modifier = Modifier.size(80.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6C5CE7)
                ),
                shape = CircleShape
            ) {
                Text(
                    text = if (isExpanded) "✕" else "☰",
                    fontSize = 30.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun RadialMenuItem(
    modifier: Modifier,
    isExpanded: Boolean,
    angle: Float,
    emoji: String,
    radius: Float,
    onClick: () -> Unit // 👈 Added onClick parameter
) {
    // 🔹 X offset animation
    val offsetX by animateFloatAsState(
        targetValue = if (isExpanded)
            radius * cos(Math.toRadians(angle.toDouble())).toFloat()
        else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    // 🔹 Y offset animation
    val offsetY by animateFloatAsState(
        targetValue = if (isExpanded)
            -radius * sin(Math.toRadians(angle.toDouble())).toFloat()
        else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    // 🔹 Scale animation
    val scale by animateFloatAsState(
        targetValue = if (isExpanded) 1f else 0f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

    // 👇 Emoji button with onClick handler
    Box(
        modifier = Modifier
            .offset(x = offsetX.dp, y = offsetY.dp)
            .size((60 * scale).dp)
            .clip(CircleShape)
            .background(Color(0xE2EEA03B))
            .clickable(enabled = isExpanded) { // 👈 Only clickable when expanded
                onClick() // 🎯 Trigger navigation
            },
        contentAlignment = Alignment.Center
    ) {
        Text(text = emoji, fontSize = 24.sp)
    }
}