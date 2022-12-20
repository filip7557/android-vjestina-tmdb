package agency.five.codebase.android.movieapp.ui.favorites

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.navigation.MovieDetailsDestination
import agency.five.codebase.android.movieapp.ui.component.MovieCard
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private const val NUMBER_OF_COLUMNS = 3

@Composable
fun FavoritesRoute(
    viewModel: FavoritesViewModel,
    onNavigateToMovieDetails: (String) -> Unit
) {
    val favoritesViewState: FavoritesViewState by viewModel.favoritesViewState.collectAsState()

    FavoritesScreen(
        favoritesViewState,
        onNavigateToMovieDetails,
        onFavoriteButtonClick = {
            viewModel.toggleFavorite(it)
        }
    )
}

@Composable
fun FavoritesScreen(
    favoritesViewState: FavoritesViewState,
    onNavigateToMovieDetails: (String) -> Unit,
    onFavoriteButtonClick: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(NUMBER_OF_COLUMNS),
        contentPadding = PaddingValues(8.dp),
    ) {
        header {
            Text(
                text = stringResource(id = R.string.favorites),
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
                onClick = {
                    onNavigateToMovieDetails(MovieDetailsDestination.createNavigation(movie.movieCardViewState.movieId))
                },
                onIconClick = { onFavoriteButtonClick(movie.movieCardViewState.movieId) }
            )
        }
    }
}

fun LazyGridScope.header(
    content: @Composable LazyGridItemScope.() -> Unit
) {
    item(span = { GridItemSpan(this.maxLineSpan) }, content = content)
}
