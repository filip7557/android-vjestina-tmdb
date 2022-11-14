package agency.five.codebase.android.movieapp.ui.main

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.navigation.*
import agency.five.codebase.android.movieapp.ui.favorites.FavoritesRoute
import agency.five.codebase.android.movieapp.ui.home.HomeScreenRoute
import agency.five.codebase.android.movieapp.ui.moviedetails.MovieDetailsRoute
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val showBottomBar by remember { mutableStateOf( //need to fix this bs
        navController.currentDestination?.route == MovieDetailsDestination.route
    )
    }

    val showBackIcon = !showBottomBar

    Scaffold(
        topBar = {
            TopBar(
                navigationIcon = {
                    if (showBackIcon) BackIcon(onBackClick = navController::popBackStack)
                }
            )
        },
        bottomBar = {
            if (showBottomBar)
                BottomNavigationBar(
                    destinations = listOf(
                        NavigationItem.HomeDestination,
                        NavigationItem.FavoritesDestination,
                    ),
                    onNavigateToDestination = {
                        navController.navigate(it.route) {
                            popUpTo(HOME_ROUTE) {
                                if(it.route == HOME_ROUTE)
                                    inclusive = true
                            }
                        }
                    },
                    currentDestination = navBackStackEntry?.destination
                )
        }
    ) { padding ->
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxSize()
        ) {
            NavHost(
                navController = navController,
                startDestination = NavigationItem.HomeDestination.route,
                modifier = Modifier.padding(padding)
            ) {
                composable(NavigationItem.HomeDestination.route) {
                    HomeScreenRoute(
                        onNavigateToMovieDetails = { }
                    )
                }

                composable(NavigationItem.FavoritesDestination.route) {
                    FavoritesRoute(
                        onNavigateToMovieDetails = {
                            !showBottomBar
                            navController.navigate(it)
                        }
                    )
                }

                composable(
                    route = MovieDetailsDestination.route,
                    arguments = listOf(navArgument(MOVIE_ID_KEY) { type = NavType.IntType }),
                ) {
                    MovieDetailsRoute()
                }
            }
        }
    }
}

@Composable
private fun TopBar(
    navigationIcon: @Composable (() -> Unit)? = null,
){
    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        modifier = Modifier
            .fillMaxHeight(0.07f)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(1f),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(id = R.drawable.tmdb_logo),
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun BackIcon(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize(1f),
        contentAlignment = Alignment.CenterStart
    ) {
        Icon(
            painter = painterResource(id = R.drawable.back_icon),
            contentDescription = null,
            modifier = Modifier
                .padding(8.dp)
                .clickable(onClick = onBackClick)
        )
    }
}

@Composable
private fun BottomNavigationBar(
    destinations: List<NavigationItem>,
    onNavigateToDestination: (NavigationItem) -> Unit,
    currentDestination: NavDestination?
) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.onSurface
    ) {
        destinations.forEach { destination ->
            BottomNavigationItem(
                selected = currentDestination?.route == destination.route,
                icon = {
                       Icon(
                           painter = painterResource(id = if(currentDestination?.route == destination.route) destination.selectedIconId else destination.unselectedIconId),
                           contentDescription = null)
                },
                label = {
                    Text(
                        text = stringResource(id = destination.labelId),
                        style = MaterialTheme.typography.button
                    )
                },
                onClick = { onNavigateToDestination(destination) })
        }
    }
}

