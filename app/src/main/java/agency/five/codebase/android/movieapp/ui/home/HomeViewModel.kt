package agency.five.codebase.android.movieapp.ui.home

import agency.five.codebase.android.movieapp.data.repository.MovieRepository
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.ui.home.mapper.HomeScreenMapper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
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
    val popularViewState: StateFlow<HomeMovieCategoryViewState> = popularMoviesCategorySelected
            .flatMapLatest { selectedMovieCategory ->
                val selectedCategory = MovieCategory.getByOrdinal(selectedMovieCategory.movieCategories.first { p -> p.isSelected }.itemId)!!
                movieRepository.popularMovies(selectedCategory)
                    .map {
                        homeScreenMapper.toHomeMovieCategoryViewState(
                            popularCategoryLabels,
                            selectedCategory,
                            it
                        )
                    }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                HomeMovieCategoryViewState(listOf(), listOf())
            )

    private val nowPlayingMoviesCategorySelected = MutableStateFlow(HomeMovieCategoryViewState(listOf(), listOf()))
    val nowPlayingViewState: StateFlow<HomeMovieCategoryViewState> = nowPlayingMoviesCategorySelected
        .flatMapLatest { selectedMovieCategory ->
            val selectedCategory = MovieCategory.getByOrdinal(selectedMovieCategory.movieCategories.first { p -> p.isSelected }.itemId)!!
            movieRepository.popularMovies(selectedCategory)
                .map {
                    homeScreenMapper.toHomeMovieCategoryViewState(
                        nowPlayingCategoryLabels,
                        selectedCategory,
                        it
                    )
                }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            HomeMovieCategoryViewState(listOf(), listOf())
        )

    private val upcomingMoviesCategorySelected = MutableStateFlow(HomeMovieCategoryViewState(listOf(), listOf()))
    val upcomingViewState: StateFlow<HomeMovieCategoryViewState> = upcomingMoviesCategorySelected
        .flatMapLatest { selectedMovieCategory ->
            val selectedCategory = MovieCategory.getByOrdinal(selectedMovieCategory.movieCategories.first { p -> p.isSelected }.itemId)!!
            movieRepository.popularMovies(selectedCategory)
                .map {
                    homeScreenMapper.toHomeMovieCategoryViewState(
                        upcomingCategoryLabels,
                        selectedCategory,
                        it
                    )
                }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            HomeMovieCategoryViewState(listOf(), listOf())
        )

    fun onCategoryLabelClick(categoryId: Int){
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
                                movieCategories = popularCategoryLabels,
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
                                movieCategories = popularCategoryLabels,
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
