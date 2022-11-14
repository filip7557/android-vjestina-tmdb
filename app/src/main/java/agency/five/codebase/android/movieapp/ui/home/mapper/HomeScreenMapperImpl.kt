package agency.five.codebase.android.movieapp.ui.home.mapper

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.model.Movie
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.ui.component.MovieCategoryLabelTextViewState
import agency.five.codebase.android.movieapp.ui.component.MovieCategoryLabelViewState
import agency.five.codebase.android.movieapp.ui.home.HomeMovieCategoryViewState
import agency.five.codebase.android.movieapp.ui.home.HomeMovieViewState

class HomeScreenMapperImpl : HomeScreenMapper {
    override fun toHomeMovieCategoryViewState(
        movieCategories: List<MovieCategory>,
        selectedMovieCategory: MovieCategory,
        movies: List<Movie>
    ): HomeMovieCategoryViewState {
        val categories = mutableListOf<MovieCategoryLabelViewState>()
        val movieViewStates = mutableListOf<HomeMovieViewState>()

        for (movieCategory in movieCategories) {
            categories.add(
                MovieCategoryLabelViewState(itemId = movieCategory.ordinal,
                    isSelected = selectedMovieCategory == movieCategory,
                    categoryText = MovieCategoryLabelTextViewState.ResourceText(
                        getResourceTextIdFromMovieCategory(movieCategory)
                    )
                )
            )
        }

        for (movie in movies) {
            movieViewStates.add(HomeMovieViewState(movieId = movie.id, imageUrl = movie.imageUrl, isFavorite = movie.isFavorite))
        }

        return HomeMovieCategoryViewState(categories, movieViewStates)
    }
}

fun getResourceTextIdFromMovieCategory(
    movieCategory: MovieCategory
): Int {
    return when (movieCategory) {
        MovieCategory.POPULAR_STREAMING -> R.string.streaming
        MovieCategory.POPULAR_ONTV -> R.string.on_tv
        MovieCategory.POPULAR_FORRENT -> R.string.for_rent
        MovieCategory.POPULAR_INTHEATHERS -> R.string.in_theatres
        MovieCategory.NOWPLAYING_MOVIES -> R.string.movies
        MovieCategory.NOWPLAYING_TV -> R.string.TV
        MovieCategory.UPCOMING_TODAY -> R.string.today
        MovieCategory.UPCOMING_THISWEEK -> R.string.this_week
    }
}
