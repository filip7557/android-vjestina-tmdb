package agency.five.codebase.android.movieapp.ui.home

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.navigation.MovieDetailsDestination
import agency.five.codebase.android.movieapp.ui.component.MovieCard
import agency.five.codebase.android.movieapp.ui.component.MovieCardViewState
import agency.five.codebase.android.movieapp.ui.component.MovieCategoryLabel
import agency.five.codebase.android.movieapp.ui.component.MovieCategoryLabelViewState
import agency.five.codebase.android.movieapp.ui.home.mapper.HomeScreenMapper
import agency.five.codebase.android.movieapp.ui.home.mapper.HomeScreenMapperImpl
import agency.five.codebase.android.movieapp.ui.theme.MovieAppTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val homeScreenMapper: HomeScreenMapper = HomeScreenMapperImpl()

val popularCategoryLabels = listOf(
    MovieCategory.POPULAR_STREAMING,
    MovieCategory.POPULAR_ONTV,
    MovieCategory.POPULAR_FORRENT,
    MovieCategory.POPULAR_INTHEATHERS
)

val nowPlayingCategoryLabels = listOf(
    MovieCategory.NOWPLAYING_MOVIES,
    MovieCategory.NOWPLAYING_TV
)

val upcomingCategoryLabels = listOf(
    MovieCategory.UPCOMING_TODAY,
    MovieCategory.UPCOMING_THISWEEK
)

val popularCategoryViewState = homeScreenMapper.toHomeMovieCategoryViewState(
    popularCategoryLabels,
    MovieCategory.POPULAR_STREAMING,
    MoviesMock.getMoviesList()
)
val nowPlayingCategoryViewState = homeScreenMapper.toHomeMovieCategoryViewState(
    nowPlayingCategoryLabels,
    MovieCategory.NOWPLAYING_MOVIES,
    MoviesMock.getMoviesList()
)
val upcomingCategoryViewState = homeScreenMapper.toHomeMovieCategoryViewState(
    upcomingCategoryLabels,
    MovieCategory.UPCOMING_TODAY,
    MoviesMock.getMoviesList()
)

@Composable
fun HomeScreenRoute(
    onNavigateToMovieDetails: (String) -> Unit,
    onFavoriteButtonClicked: () -> Unit
) {
    var popularViewState by remember { mutableStateOf(popularCategoryViewState) }
    var nowPlayingViewState by remember { mutableStateOf(nowPlayingCategoryViewState) }
    var upcomingViewState by remember { mutableStateOf(upcomingCategoryViewState) }

    HomeScreen(
        popularViewState = popularViewState,
        nowPlayingViewState = nowPlayingViewState,
        upcomingViewState = upcomingViewState,
        onPopularLabelClick = {
            popularViewState = homeScreenMapper.toHomeMovieCategoryViewState(
                popularCategoryLabels,
                MovieCategory.getByOrdinal(it)!!,
                MoviesMock.getMoviesList()
            )
        },
        onNowPlayingLabelClick = {
            nowPlayingViewState = homeScreenMapper.toHomeMovieCategoryViewState(
                nowPlayingCategoryLabels,
                MovieCategory.getByOrdinal(it)!!,
                MoviesMock.getMoviesList()
            )
        },
        onUpcomingLabelClick = {
            upcomingViewState = homeScreenMapper.toHomeMovieCategoryViewState(
                upcomingCategoryLabels,
                MovieCategory.getByOrdinal(it)!!,
                MoviesMock.getMoviesList()
            )
        },
        onNavigateToMovieDetails = onNavigateToMovieDetails,
        onFavoriteButtonClicked = onFavoriteButtonClicked
    )
}

@Composable
fun HomeScreen(
    popularViewState: HomeMovieCategoryViewState,
    nowPlayingViewState: HomeMovieCategoryViewState,
    upcomingViewState: HomeMovieCategoryViewState,
    onPopularLabelClick: (Int) -> Unit,
    onNowPlayingLabelClick: (Int) -> Unit,
    onUpcomingLabelClick: (Int) -> Unit,
    onNavigateToMovieDetails: (String) -> Unit,
    onFavoriteButtonClicked: () -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        item {
            HomeMovieCategory(popularViewState, onPopularLabelClick, onNavigateToMovieDetails, stringResource(id = R.string.popularTitle), onFavoriteButtonClicked)
        }

        item {
            HomeMovieCategory(nowPlayingViewState, onNowPlayingLabelClick, onNavigateToMovieDetails, stringResource(id = R.string.nowPlayingTitle), onFavoriteButtonClicked)
        }

        item {
            HomeMovieCategory(upcomingViewState, onUpcomingLabelClick, onNavigateToMovieDetails, stringResource(id = R.string.upcomingTitle), onFavoriteButtonClicked)
        }
    }
}

@Composable
fun HomeMovieCategory(
    homeMovieViewState: HomeMovieCategoryViewState,
    onLabelClick: (Int) -> Unit,
    onNavigateToMovieDetails: (String) -> Unit,
    title: String,
    onFavoriteButtonClicked: () -> Unit
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
                onIconClick = onFavoriteButtonClicked
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    MovieAppTheme {
        HomeScreenRoute({}) {

        }
    }
}

