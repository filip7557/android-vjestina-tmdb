package agency.five.codebase.android.movieapp.ui.home

import agency.five.codebase.android.movieapp.data.repository.MovieRepository
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.ui.home.mapper.HomeScreenMapper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
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


    private val popularMoviesCategorySelected = MutableStateFlow(HomeMovieCategoryViewState(listOf(), listOf()))
    val popularViewState: StateFlow<HomeMovieCategoryViewState> = popularMoviesCategorySelected.asStateFlow()

    private val nowPlayingMoviesCategorySelected = MutableStateFlow(HomeMovieCategoryViewState(listOf(), listOf()))
    val nowPlayingViewState: StateFlow<HomeMovieCategoryViewState> = nowPlayingMoviesCategorySelected.asStateFlow()

    private val upcomingMoviesCategorySelected = MutableStateFlow(HomeMovieCategoryViewState(listOf(), listOf()))
    val upcomingViewState: StateFlow<HomeMovieCategoryViewState> = upcomingMoviesCategorySelected.asStateFlow()

    init {
        getPopularMovies()
        getNowPlayingMovies()
        getUpcomingMovies()
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            movieRepository.popularMovies(MovieCategory.POPULAR_STREAMING).collect {
                popularMoviesCategorySelected.emit(
                    homeScreenMapper.toHomeMovieCategoryViewState(
                        movieCategories = popularCategoryLabels,
                        selectedMovieCategory = MovieCategory.POPULAR_STREAMING,
                        movies = it
                    )
                )
            }
        }
    }

    private fun getNowPlayingMovies() {
        viewModelScope.launch {
            movieRepository.nowPlayingMovies(MovieCategory.NOWPLAYING_MOVIES).collect {
                nowPlayingMoviesCategorySelected.emit(
                    homeScreenMapper.toHomeMovieCategoryViewState(
                        movieCategories = nowPlayingCategoryLabels,
                        selectedMovieCategory = MovieCategory.NOWPLAYING_MOVIES,
                        movies = it
                    )
                )
            }
        }
    }

    private fun getUpcomingMovies() {
        viewModelScope.launch {
            movieRepository.upcomingMovies(MovieCategory.UPCOMING_TODAY).collect {
                upcomingMoviesCategorySelected.emit(
                    homeScreenMapper.toHomeMovieCategoryViewState(
                        movieCategories = upcomingCategoryLabels,
                        selectedMovieCategory = MovieCategory.UPCOMING_TODAY,
                        movies = it
                    )
                )
            }
        }
    }

    fun onCategoryLabelClick(categoryId: Int) {
        when (categoryId) {
            MovieCategory.POPULAR_STREAMING.ordinal,
            MovieCategory.POPULAR_FORRENT.ordinal,
            MovieCategory.POPULAR_ONTV.ordinal,
            MovieCategory.POPULAR_INTHEATHERS.ordinal
            -> {
                viewModelScope.launch {
                    movieRepository.popularMovies(MovieCategory.POPULAR_STREAMING).collect {
                       popularMoviesCategorySelected.emit(
                           homeScreenMapper.toHomeMovieCategoryViewState(
                               movieCategories = popularCategoryLabels,
                                selectedMovieCategory = MovieCategory.getByOrdinal(categoryId)!!,
                                movies = it
                           )
                       )
                    }
                }
            }

            MovieCategory.NOWPLAYING_MOVIES.ordinal,
            MovieCategory.NOWPLAYING_TV.ordinal
            -> {
                viewModelScope.launch {
                    movieRepository.nowPlayingMovies(MovieCategory.NOWPLAYING_MOVIES).collect {
                        nowPlayingMoviesCategorySelected.emit(
                            homeScreenMapper.toHomeMovieCategoryViewState(
                                movieCategories = nowPlayingCategoryLabels,
                                selectedMovieCategory = MovieCategory.getByOrdinal(categoryId)!!,
                                movies = it
                            )
                        )
                    }
                }
            }

            MovieCategory.UPCOMING_TODAY.ordinal,
            MovieCategory.UPCOMING_THISWEEK.ordinal
            -> {
                viewModelScope.launch {
                    movieRepository.upcomingMovies(MovieCategory.UPCOMING_TODAY).collect {
                        upcomingMoviesCategorySelected.emit(
                            homeScreenMapper.toHomeMovieCategoryViewState(
                                movieCategories = upcomingCategoryLabels,
                                selectedMovieCategory = MovieCategory.getByOrdinal(categoryId)!!,
                                movies = it
                            )
                        )
                    }
                }
            }
        }
    }

    fun toggleFavorites(movieId: Int) {
        viewModelScope.launch {
            movieRepository.toggleFavorite(movieId)
        }
    }
}
