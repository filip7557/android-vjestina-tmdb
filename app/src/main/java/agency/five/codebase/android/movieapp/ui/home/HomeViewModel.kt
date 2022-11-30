package agency.five.codebase.android.movieapp.ui.home

import agency.five.codebase.android.movieapp.data.repository.MovieRepository
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.ui.home.mapper.HomeScreenMapper
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class HomeViewModel(
    private val movieRepository: MovieRepository,
    HomeScreenMapper: HomeScreenMapper,
) : ViewModel() {

    val popularViewState: StateFlow<HomeMovieCategoryViewState> = MutableStateFlow(HomeScreenMapper.toHomeMovieCategoryViewState(
        popularCategoryLabels,
        MovieCategory.POPULAR_STREAMING,
        runBlocking {
            movieRepository.favoriteMovies().first()
        }
    ))

    val nowPlayingViewState: StateFlow<HomeMovieCategoryViewState> = MutableStateFlow(HomeScreenMapper.toHomeMovieCategoryViewState(
        nowPlayingCategoryLabels,
        MovieCategory.NOWPLAYING_MOVIES,
        runBlocking {
            movieRepository.favoriteMovies().first()
        }
    ))

    val upcomingViewState: StateFlow<HomeMovieCategoryViewState> = MutableStateFlow(HomeScreenMapper.toHomeMovieCategoryViewState(
        upcomingCategoryLabels,
        MovieCategory.UPCOMING_TODAY,
        runBlocking {
            movieRepository.favoriteMovies().first()
        }
    ))
}
