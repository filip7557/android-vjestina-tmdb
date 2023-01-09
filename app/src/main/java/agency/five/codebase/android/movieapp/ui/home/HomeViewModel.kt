package agency.five.codebase.android.movieapp.ui.home

import agency.five.codebase.android.movieapp.data.repository.MovieRepository
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.ui.home.mapper.HomeScreenMapper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
    @OptIn(ExperimentalCoroutinesApi::class)
    val popularViewState: StateFlow<HomeMovieCategoryViewState> =
        popularMoviesCategorySelected
            .flatMapLatest { selectedMovieCategory ->
                val category: MovieCategory = try {
                    MovieCategory.getByOrdinal(selectedMovieCategory.movieCategories.first { category -> category.isSelected }.itemId)!!
                } catch (e: NoSuchElementException) {
                    MovieCategory.POPULAR_STREAMING
                }
                movieRepository.movies(category)
                    .map {
                        homeScreenMapper.toHomeMovieCategoryViewState(
                            movieCategories = popularCategoryLabels,
                            selectedMovieCategory = category,
                            movies = it
                        )
                    }
            }.stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                HomeMovieCategoryViewState(listOf(), listOf())
            )

    private val nowPlayingMoviesCategorySelected = MutableStateFlow(HomeMovieCategoryViewState(listOf(), listOf()))
    @OptIn(ExperimentalCoroutinesApi::class)
    val nowPlayingViewState: StateFlow<HomeMovieCategoryViewState> =
        nowPlayingMoviesCategorySelected
            .flatMapLatest { selectedMovieCategory ->
                val category: MovieCategory = try {
                    MovieCategory.getByOrdinal(selectedMovieCategory.movieCategories.first { category -> category.isSelected }.itemId)!!
                } catch (e: NoSuchElementException) {
                    MovieCategory.NOWPLAYING_MOVIES
                }
                movieRepository.movies(category)
                    .map {
                        homeScreenMapper.toHomeMovieCategoryViewState(
                            movieCategories = nowPlayingCategoryLabels,
                            selectedMovieCategory = category,
                            movies = it
                        )
                    }
            }.stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                HomeMovieCategoryViewState(listOf(), listOf())
            )

    private val upcomingMoviesCategorySelected = MutableStateFlow(HomeMovieCategoryViewState(listOf(), listOf()))
    @OptIn(ExperimentalCoroutinesApi::class)
    val upcomingViewState: StateFlow<HomeMovieCategoryViewState> =
        upcomingMoviesCategorySelected
            .flatMapLatest { selectedMovieCategory ->
                val category: MovieCategory = try {
                    MovieCategory.getByOrdinal(selectedMovieCategory.movieCategories.first { category -> category.isSelected }.itemId)!!
                } catch (e: NoSuchElementException) {
                    MovieCategory.UPCOMING_TODAY
                }
                movieRepository.movies(category)
                    .map {
                        homeScreenMapper.toHomeMovieCategoryViewState(
                            movieCategories = upcomingCategoryLabels,
                            selectedMovieCategory = category,
                            movies = it
                        )
                    }
            }.stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                HomeMovieCategoryViewState(listOf(), listOf())
            )

    fun onCategoryLabelClick(categoryId: Int) {
        viewModelScope.launch {
            when (categoryId) {
                MovieCategory.POPULAR_STREAMING.ordinal,
                MovieCategory.POPULAR_FORRENT.ordinal,
                MovieCategory.POPULAR_ONTV.ordinal,
                MovieCategory.POPULAR_INTHEATHERS.ordinal
                -> {
                    popularMoviesCategorySelected.emit(
                        homeScreenMapper.toHomeMovieCategoryViewState(
                            popularCategoryLabels,
                            MovieCategory.getByOrdinal(categoryId)!!,
                            listOf()
                        )
                    )
                }

                MovieCategory.NOWPLAYING_MOVIES.ordinal,
                MovieCategory.NOWPLAYING_TV.ordinal
                -> {
                    nowPlayingMoviesCategorySelected.emit(
                        homeScreenMapper.toHomeMovieCategoryViewState(
                            movieCategories = nowPlayingCategoryLabels,
                            selectedMovieCategory = MovieCategory.getByOrdinal(categoryId)!!,
                            movies = listOf()
                        )
                    )
                }

                MovieCategory.UPCOMING_TODAY.ordinal,
                MovieCategory.UPCOMING_THISWEEK.ordinal
                -> {
                    upcomingMoviesCategorySelected.emit(
                        homeScreenMapper.toHomeMovieCategoryViewState(
                            movieCategories = upcomingCategoryLabels,
                            selectedMovieCategory = MovieCategory.getByOrdinal(categoryId)!!,
                            movies = listOf()
                        )
                    )
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
