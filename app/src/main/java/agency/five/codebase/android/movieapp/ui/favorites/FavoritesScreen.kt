package agency.five.codebase.android.movieapp.ui.favorites

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.ui.component.MovieCard
import agency.five.codebase.android.movieapp.ui.favorites.mapper.FavoritesMapper
import agency.five.codebase.android.movieapp.ui.favorites.mapper.FavoritesMapperImpl
import agency.five.codebase.android.movieapp.ui.theme.MovieAppTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val FavoritesMapper: FavoritesMapper = FavoritesMapperImpl()

val favoritesViewState = FavoritesMapper.toFavoritesViewState(MoviesMock.getMoviesList())

@Composable
fun FavoritesRoute(
    //actions
) {
    val z by remember { mutableStateOf(favoritesViewState) }
    // ...

    FavoritesScreen(
        z,
        // other states and actions
    )
}

@Composable
fun FavoritesScreen(
    favoritesViewState: FavoritesViewState,
    // other states and actions
) {
    Column(
        modifier = Modifier
            .fillMaxSize(1f)
    ) {
        FavoritesTopAppBar()
        FavoritesLazyVerticalGrid(favoritesViewState)
    }
    FavoritesBottomNavigation()
}

@Composable
fun FavoritesTopAppBar() {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.primary,
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
fun FavoritesLazyVerticalGrid(favoritesViewState: FavoritesViewState) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(8.dp),
    ) {
        header {
            Text(
                text = "Favorites",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .padding(10.dp)
            )
        }
        items(
            favoritesViewState.movies,
            key = { movie -> movie.movieCardViewState.movieId }
        ) { movie ->
            MovieCard(
                movieCardViewState = movie.movieCardViewState,
                modifier = Modifier
                    .padding(vertical = 15.dp, horizontal = 4.dp),
                onClick = { /*TODO*/ })
        }
    }
}

@Composable
fun FavoritesBottomNavigation() {
    Box(
        modifier = Modifier
            .fillMaxSize(1f),
        contentAlignment = Alignment.BottomCenter
    ) {
        BottomNavigation(
            backgroundColor = MaterialTheme.colors.background,
            contentColor = MaterialTheme.colors.onSurface,
        ) {
            BottomNavigationItem(
                selected = false,
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.home_notselected),
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = "Home",
                        style = typography.button
                    )
                },
                onClick = { /*TODO*/ },
            )
            BottomNavigationItem(
                selected = true,
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.heart_icon_selected),
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = "Favorites",
                        style = typography.button
                    )
                },
                onClick = { /*TODO*/ },
            )
        }
    }
}

fun LazyGridScope.header(
    content: @Composable LazyGridItemScope.() -> Unit
) {
    item(span = { GridItemSpan(this.maxLineSpan) }, content = content)
}

@Preview
@Composable
fun FavoritesScreenPreview() {
    val favoritesMapper = FavoritesMapperImpl()
    MovieAppTheme {
        FavoritesScreen(
            favoritesViewState = favoritesMapper.toFavoritesViewState(MoviesMock.getMoviesList()),
            // states and empty actions
        )
    }
}
