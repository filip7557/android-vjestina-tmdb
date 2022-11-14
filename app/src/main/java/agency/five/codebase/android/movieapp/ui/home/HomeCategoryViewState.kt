package agency.five.codebase.android.movieapp.ui.home

import agency.five.codebase.android.movieapp.ui.component.MovieCategoryLabelViewState

data class HomeMovieCategoryViewState(
    val movieCategories: List<MovieCategoryLabelViewState>,
    val movies: List<HomeMovieViewState>
)

data class HomeMovieViewState (
    val movieId: Int,
    val imageUrl: String?,
    val isFavorite: Boolean
)
