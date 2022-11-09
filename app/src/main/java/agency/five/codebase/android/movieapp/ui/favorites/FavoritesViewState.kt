package agency.five.codebase.android.movieapp.ui.favorites

import agency.five.codebase.android.movieapp.ui.component.MovieCardViewState

data class FavoritesViewState(
    val movies: List<FavoritesMovieViewState>
    )

data class FavoritesMovieViewState(
    val movieCardViewState: MovieCardViewState
)
