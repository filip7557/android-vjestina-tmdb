package agency.five.codebase.android.movieapp.ui.moviedetails

import agency.five.codebase.android.movieapp.data.repository.MovieRepository
import agency.five.codebase.android.movieapp.ui.moviedetails.mapper.MovieDetailsMapper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val movieRepository: MovieRepository,
    private val movieDetailsMapper: MovieDetailsMapper,
    private val movieId: Int
) : ViewModel() {

    private val _movieDetailsViewState = MutableStateFlow(MovieDetailsViewState())
    val movieDetailsViewState: StateFlow<MovieDetailsViewState> = _movieDetailsViewState.asStateFlow()

    init {
        viewModelScope.launch{
            movieRepository.movieDetails(movieId).collect {
                _movieDetailsViewState.value = movieDetailsMapper.toMovieDetailsViewState(it)
            }
        }
    }

    fun toggleFavorite(movieId: Int) {
        viewModelScope.launch {
            movieRepository.toggleFavorite(movieId)
        }
    }
}
