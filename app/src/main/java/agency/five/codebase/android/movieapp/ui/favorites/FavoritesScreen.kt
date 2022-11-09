package agency.five.codebase.android.movieapp.ui.favorites

import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.ui.favorites.mapper.FavoritesMapper
import agency.five.codebase.android.movieapp.ui.favorites.mapper.FavoritesMapperImpl
import agency.five.codebase.android.movieapp.ui.theme.MovieAppTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview

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
    // ...
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
