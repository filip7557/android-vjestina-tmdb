package agency.five.codebase.android.movieapp.ui.home

import agency.five.codebase.android.movieapp.data.repository.MovieRepository
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.ui.home.mapper.HomeScreenMapper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val movieRepository: MovieRepository,
    private val homeScreenMapper: HomeScreenMapper,
) : ViewModel() {

    private val popularCategoryLabels = listOf(
        MovieCategory.POPULAR_STREAMING,
        MovieCategory.POPULAR_ONTV,
        MovieCategory.POPULAR_FORRENT,
        MovieCategory.POPULAR_INTHEATHERS
    )

    private val nowPlayingCategoryLabels = listOf(
        MovieCategory.NOWPLAYING_MOVIES,
        MovieCategory.NOWPLAYING_TV
    )

    private val upcomingCategoryLabels = listOf(
        MovieCategory.UPCOMING_TODAY,
        MovieCategory.UPCOMING_THISWEEK
    )

    private val _popularViewState = MutableStateFlow(HomeMovieCategoryViewState(listOf(), listOf()))
    val popularViewState: StateFlow<HomeMovieCategoryViewState> = _popularViewState.asStateFlow()

    private val _nowPlayingViewState = MutableStateFlow(HomeMovieCategoryViewState(listOf(), listOf()))
    val nowPlayingViewState: StateFlow<HomeMovieCategoryViewState> = _nowPlayingViewState.asStateFlow()

    private val _upcomingViewState = MutableStateFlow(HomeMovieCategoryViewState(listOf(), listOf()))
    val upcomingViewState: StateFlow<HomeMovieCategoryViewState> = _upcomingViewState.asStateFlow()

    init {
        getPopularMovies(homeScreenMapper)
        getNowPlayingMovies(homeScreenMapper)
        getUpcomingMovies(homeScreenMapper)
    }

    private fun getPopularMovies(mapper: HomeScreenMapper) {
        viewModelScope.launch {
            movieRepository.popularMovies(MovieCategory.POPULAR_STREAMING).collect {
                _popularViewState.value = mapper.toHomeMovieCategoryViewState(
                    movieCategories = popularCategoryLabels,
                    selectedMovieCategory = MovieCategory.POPULAR_STREAMING,
                    movies = it
                )
            }
        }
    }

    private fun getNowPlayingMovies(mapper: HomeScreenMapper) {
        viewModelScope.launch {
            movieRepository.nowPlayingMovies(MovieCategory.NOWPLAYING_MOVIES).collect {
                _nowPlayingViewState.value = mapper.toHomeMovieCategoryViewState(
                    movieCategories = nowPlayingCategoryLabels,
                    selectedMovieCategory = MovieCategory.NOWPLAYING_MOVIES,
                    movies = it
                )
            }
        }
    }

    private fun getUpcomingMovies(mapper: HomeScreenMapper) {
        viewModelScope.launch {
            movieRepository.upcomingMovies(MovieCategory.UPCOMING_TODAY).collect {
                _upcomingViewState.value = mapper.toHomeMovieCategoryViewState(
                    movieCategories = upcomingCategoryLabels,
                    selectedMovieCategory = MovieCategory.UPCOMING_TODAY,
                    movies = it
                )
            }
        }
    }

    fun onPopularLabelClick(id: Int) {
        viewModelScope.launch {
            movieRepository.popularMovies(MovieCategory.POPULAR_STREAMING).collect {
                _popularViewState.value = homeScreenMapper.toHomeMovieCategoryViewState(
                    movieCategories = popularCategoryLabels,
                    selectedMovieCategory = MovieCategory.getByOrdinal(id)!!,
                    movies = it
                )
            }
        }
    }

    fun onNowPlayingLabelClick(id: Int) {
        viewModelScope.launch {
            movieRepository.nowPlayingMovies(MovieCategory.NOWPLAYING_MOVIES).collect {
                _nowPlayingViewState.value = homeScreenMapper.toHomeMovieCategoryViewState(
                    movieCategories = nowPlayingCategoryLabels,
                    selectedMovieCategory = MovieCategory.getByOrdinal(id)!!,
                    movies = it
                )
            }
        }
    }

    fun onUpcomingLabelClick(id: Int) {
        viewModelScope.launch {
            movieRepository.upcomingMovies(MovieCategory.UPCOMING_TODAY).collect {
                _upcomingViewState.value = homeScreenMapper.toHomeMovieCategoryViewState(
                    movieCategories = upcomingCategoryLabels,
                    selectedMovieCategory = MovieCategory.getByOrdinal(id)!!,
                    movies = it
                )
            }
        }
    }

    fun toggleFavorites(movieId: Int) {
        viewModelScope.launch {
            movieRepository.toggleFavorite(movieId)
        }
    }
}
