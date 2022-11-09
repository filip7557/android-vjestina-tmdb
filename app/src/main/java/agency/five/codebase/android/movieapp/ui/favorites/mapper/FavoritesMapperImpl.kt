package agency.five.codebase.android.movieapp.ui.favorites.mapper

import agency.five.codebase.android.movieapp.model.Movie
import agency.five.codebase.android.movieapp.ui.component.MovieCardViewState
import agency.five.codebase.android.movieapp.ui.favorites.FavoritesMovieViewState
import agency.five.codebase.android.movieapp.ui.favorites.FavoritesViewState

class FavoritesMapperImpl : FavoritesMapper {
    override fun toFavoritesViewState(favoriteMovies: List<Movie>): FavoritesViewState {
        val movies = mutableListOf<FavoritesMovieViewState>()
        for (movie in favoriteMovies)
        {
            movies.add(FavoritesMovieViewState(MovieCardViewState(imageUrl = movie.imageUrl, movieId = movie.id, isFavorite = movie.isFavorite)))
        }
        return FavoritesViewState(movies)
    }
}
