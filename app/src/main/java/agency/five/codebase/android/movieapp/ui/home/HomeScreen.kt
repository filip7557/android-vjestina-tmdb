package agency.five.codebase.android.movieapp.ui.home

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
    onNavigateToMovieDetails: (String) -> Unit
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
        onNavigateToMovieDetails = onNavigateToMovieDetails
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
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        item {
            PopularMovies(popularViewState, onPopularLabelClick, onNavigateToMovieDetails)
        }

        item {
            NowPlayingMovies(nowPlayingViewState, onNowPlayingLabelClick, onNavigateToMovieDetails)
        }

        item {
            UpcomingMovies(upcomingViewState, onUpcomingLabelClick, onNavigateToMovieDetails)
        }
    }
}

@Composable
fun UpcomingMovies(
    upcomingViewState: HomeMovieCategoryViewState,
    onLabelClick: (Int) -> Unit,
    onNavigateToMovieDetails: (String) -> Unit
) {
    Text(
        text = "Upcoming",
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
        items(upcomingViewState.movieCategories.size) {category ->
            MovieCategoryLabel(
                movieCategoryLabelViewState = MovieCategoryLabelViewState(
                    upcomingViewState.movieCategories[category].itemId,
                    upcomingViewState.movieCategories[category].isSelected,
                    upcomingViewState.movieCategories[category].categoryText
                ),
                onClick = { onLabelClick(upcomingViewState.movieCategories[category].itemId) },
            )
        }
    }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(10.dp)
    ) {
        items(upcomingViewState.movies.size) {movie ->
            MovieCard(movieCardViewState = MovieCardViewState(
                upcomingViewState.movies[movie].imageUrl,
                upcomingViewState.movies[movie].movieId,
                upcomingViewState.movies[movie].isFavorite
            ),
                onClick = { onNavigateToMovieDetails(MovieDetailsDestination.createNavigation(upcomingViewState.movies[movie].movieId)) },
                onIconClick = {}
            )
        }
    }
}

@Composable
fun NowPlayingMovies(
    nowPlayingViewState: HomeMovieCategoryViewState,
    onLabelClick: (Int) -> Unit,
    onNavigateToMovieDetails: (String) -> Unit
) {
    Text(
        text = "Now playing",
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
        items(nowPlayingViewState.movieCategories.size) {category ->
            MovieCategoryLabel(
                movieCategoryLabelViewState = MovieCategoryLabelViewState(
                    nowPlayingViewState.movieCategories[category].itemId,
                    nowPlayingViewState.movieCategories[category].isSelected,
                    nowPlayingViewState.movieCategories[category].categoryText
                ),
                onClick = { onLabelClick(nowPlayingViewState.movieCategories[category].itemId) }
            )
        }
    }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(10.dp)
    ) {
        items(nowPlayingViewState.movies.size) {movie ->
            MovieCard(movieCardViewState = MovieCardViewState(
                nowPlayingViewState.movies[movie].imageUrl,
                nowPlayingViewState.movies[movie].movieId,
                nowPlayingViewState.movies[movie].isFavorite
            ),
                onClick = { onNavigateToMovieDetails(MovieDetailsDestination.createNavigation(nowPlayingViewState.movies[movie].movieId)) },
                onIconClick = {}
            )
        }
    }
}

@Composable
fun PopularMovies(
    popularViewState: HomeMovieCategoryViewState,
    onLabelClick: (Int) -> Unit,
    onNavigateToMovieDetails: (String) -> Unit
) {
    Text(
        text = "What's popular",
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
        items(popularViewState.movieCategories.size) {category ->
            MovieCategoryLabel(
                movieCategoryLabelViewState = MovieCategoryLabelViewState(
                    popularViewState.movieCategories[category].itemId,
                    popularViewState.movieCategories[category].isSelected,
                    popularViewState.movieCategories[category].categoryText
                ),
                onClick = { onLabelClick(popularViewState.movieCategories[category].itemId) }
            )
        }
    }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(10.dp)
    ) {
        items(popularViewState.movies.size) {movie ->
            MovieCard(movieCardViewState = MovieCardViewState(
                popularViewState.movies[movie].imageUrl,
                popularViewState.movies[movie].movieId,
                popularViewState.movies[movie].isFavorite
            ),
                onClick = { onNavigateToMovieDetails(MovieDetailsDestination.createNavigation(popularViewState.movies[movie].movieId)) },
                onIconClick = {}
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    MovieAppTheme {
        HomeScreenRoute {

        }
    }
}
