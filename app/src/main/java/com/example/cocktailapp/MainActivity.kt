package com.example.cocktailapp

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cocktailapp.presentation.screens.details.DetailsScreen
import com.example.cocktailapp.presentation.screens.drinklist.DrinkListScreen
import com.example.cocktailapp.presentation.screens.home.HomeScreen
import com.example.cocktailapp.presentation.screens.search.SearchScreen
import com.example.cocktailapp.ui.theme.CocktailAppTheme
import dagger.hilt.android.AndroidEntryPoint

sealed class Screen(val route: String, val title: String, val icon: ImageVector?) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Search : Screen("search", "Search", Icons.Default.Search)
    object DrinkList : Screen("drinks_by_ingredient/{ingredientName}", "Drinks", null) {
        fun createRoute(ingredientName: String) = "drinks_by_ingredient/$ingredientName"
    }
    object DrinkDetails : Screen("drink_details/{drinkId}", "Details", null) {
        fun createRoute(drinkId: String) = "drink_details/$drinkId"
    }
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CocktailAppTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController, 
            startDestination = Screen.Home.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(300)) + fadeIn(animationSpec = tween(300)) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(300)) + fadeOut(animationSpec = tween(300)) },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(300)) + fadeIn(animationSpec = tween(300)) },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(300)) + fadeOut(animationSpec = tween(300)) }
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onIngredientClick = { ingredientName ->
                        navController.navigate(Screen.DrinkList.createRoute(ingredientName))
                    },
                    onDrinkClick = { drinkId ->
                        navController.navigate(Screen.DrinkDetails.createRoute(drinkId))
                    }
                )
            }
            composable(Screen.Search.route) { 
                SearchScreen(onDrinkClick = { drinkId ->
                    navController.navigate(Screen.DrinkDetails.createRoute(drinkId))
                })
            }
            composable(
                route = Screen.DrinkList.route,
                arguments = listOf(navArgument("ingredientName") { type = NavType.StringType })
            ) {
                DrinkListScreen(
                    onDrinkClick = { drinkId ->
                        navController.navigate(Screen.DrinkDetails.createRoute(drinkId))
                    },
                    onBackClick = { navController.navigateUp() }
                )
            }
            composable(
                route = Screen.DrinkDetails.route,
                arguments = listOf(navArgument("drinkId") { type = NavType.StringType })
            ) {
                DetailsScreen(onBackClick = { navController.navigateUp() })
            }
        }

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        if (currentDestination?.route == Screen.Home.route || currentDestination?.route == Screen.Search.route) {
            BottomBar(
                navController = navController,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp, start = 54.dp, end = 54.dp)
            )
        }
    }
}

@Composable
fun BottomBar(navController: NavController, modifier: Modifier = Modifier) {
    val navBarItems = listOf(Screen.Home, Screen.Search)
    val navBarShape = RoundedCornerShape(44.dp)

    Box(modifier = modifier.clip(navBarShape)) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .graphicsLayer {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        renderEffect = RenderEffect.createBlurEffect(
                            10.dp.toPx(),
                            10.dp.toPx(),
                            Shader.TileMode.DECAL
                        ).asComposeRenderEffect()
                    }
                }
                .background(Color.Black.copy(alpha = 0.85f))
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            navBarItems.forEach { screen ->
                screen.icon?.let {
                    NavigationBarItem(
                        icon = { Icon(it, contentDescription = screen.title) },
                        selected = currentDestination?.hierarchy?.any { dest -> dest.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            unselectedIconColor = Color.White.copy(alpha = 0.6f),
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CocktailAppTheme {
        val navController = rememberNavController()
        Box(modifier = Modifier.fillMaxSize()) {
            BottomBar(
                navController = navController,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp, start = 54.dp, end = 54.dp)
            )
        }
    }
}
