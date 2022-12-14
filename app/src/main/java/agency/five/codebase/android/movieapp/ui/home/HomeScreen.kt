package agency.five.codebase.android.movieapp.ui.home

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.mock.FavoritesDBMock
import agency.five.codebase.android.movieapp.navigation.MovieDetailsDestination
import agency.five.codebase.android.movieapp.ui.component.*
import agency.five.codebase.android.movieapp.ui.theme.MovieAppTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreenRoute(
    viewModel: HomeViewModel,
    onNavigateToMovieDetails: (String) -> Unit,
) {
    val popularViewState: HomeMovieCategoryViewState by viewModel.popularViewState.collectAsState()
    val nowPlayingViewState: HomeMovieCategoryViewState by viewModel.nowPlayingViewState.collectAsState()
    val upcomingViewState: HomeMovieCategoryViewState by viewModel.upcomingViewState.collectAsState()

    HomeScreen(
        popularViewState = popularViewState,
        nowPlayingViewState = nowPlayingViewState,
        upcomingViewState = upcomingViewState,
        onMovieCategoryLabelClick = {
            viewModel.onCategoryLabelClick(it)
        },
        onNavigateToMovieDetails = onNavigateToMovieDetails,
        onFavoriteButtonClicked = {
            viewModel.toggleFavorites(it)
        }
    )
}

@Composable
fun HomeScreen(
    popularViewState: HomeMovieCategoryViewState,
    nowPlayingViewState: HomeMovieCategoryViewState,
    upcomingViewState: HomeMovieCategoryViewState,
    onMovieCategoryLabelClick: (Int) -> Unit,
    onNavigateToMovieDetails: (String) -> Unit,
    onFavoriteButtonClicked: (Int) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        item {
            HomeMovieCategory(popularViewState, onMovieCategoryLabelClick, onNavigateToMovieDetails, stringResource(id = R.string.popularTitle), onFavoriteButtonClicked)
        }

        item {
            HomeMovieCategory(nowPlayingViewState, onMovieCategoryLabelClick, onNavigateToMovieDetails, stringResource(id = R.string.nowPlayingTitle), onFavoriteButtonClicked)
        }

        item {
            HomeMovieCategory(upcomingViewState, onMovieCategoryLabelClick, onNavigateToMovieDetails, stringResource(id = R.string.upcomingTitle), onFavoriteButtonClicked)
        }
    }
}

@Composable
fun HomeMovieCategory(
    homeMovieViewState: HomeMovieCategoryViewState,
    onLabelClick: (Int) -> Unit,
    onNavigateToMovieDetails: (String) -> Unit,
    title: String,
    onFavoriteButtonClicked: (Int) -> Unit
) {
    Text(
        text = title,
        fontSize = 17.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colors.primary,
        modifier = Modifier
            .padding(8.dp)
    )

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(30.dp),
        userScrollEnabled = false,
        modifier = Modifier
            .padding(10.dp)
    ) {
        items(homeMovieViewState.movieCategories.size) {category ->
            val movieCategoryLabelViewState = homeMovieViewState.movieCategories[category]
            MovieCategoryLabel(
                movieCategoryLabelViewState = MovieCategoryLabelViewState(
                    movieCategoryLabelViewState.itemId,
                    movieCategoryLabelViewState.isSelected,
                    movieCategoryLabelViewState.categoryText
                ),
                onClick = { onLabelClick(movieCategoryLabelViewState.itemId) },
            )
        }
    }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(10.dp)
    ) {
        items(homeMovieViewState.movies.size) {movie ->
            val movieState = homeMovieViewState.movies[movie]
            MovieCard(movieCardViewState = MovieCardViewState(
                movieState.imageUrl,
                movieState.movieId,
                movieState.isFavorite
            ),
                onClick = { onNavigateToMovieDetails(MovieDetailsDestination.createNavigation(movieState.movieId)) },
                onIconClick = { onFavoriteButtonClicked(movieState.movieId) }
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    FavoritesDBMock.insert(2)
    MovieAppTheme {
        }
}

