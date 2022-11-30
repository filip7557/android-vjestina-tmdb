package agency.five.codebase.android.movieapp.ui.favorites

import agency.five.codebase.android.movieapp.data.repository.MovieRepository
import agency.five.codebase.android.movieapp.mock.FavoritesDBMock
import agency.five.codebase.android.movieapp.model.Movie
import agency.five.codebase.android.movieapp.ui.favorites.mapper.FavoritesMapper
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

class FavoritesViewModel(
    private val movieRepository: MovieRepository,
    FavoritesMapper: FavoritesMapper,
) : ViewModel() {

    val favoritesViewState: StateFlow<FavoritesViewState> =
        MutableStateFlow(FavoritesMapper.toFavoritesViewState(
            runBlocking {
                movieRepository.favoriteMovies().first()
            }
        ))
}
