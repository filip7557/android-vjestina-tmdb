package agency.five.codebase.android.movieapp.data.network

import agency.five.codebase.android.movieapp.data.network.model.ApiMovieDetails
import agency.five.codebase.android.movieapp.data.network.model.MovieCreditsResponse
import agency.five.codebase.android.movieapp.data.network.model.MovieResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"
private const val BASE_URL = "https://api.themoviedb.org/3"
private const val API_KEY = "77229919eb9b0ff7a109794fa8c881c4"

class MovieServiceImpl(private val client: HttpClient) : MovieService {
    override suspend fun fetchPopularMovies(): MovieResponse =
        client.get("$BASE_URL/movie/popular?api_key=$API_KEY").body()

    override suspend fun fetchNowPlayingMovies(): MovieResponse =
        client.get("$BASE_URL/movie/now_playing?api_key=$API_KEY").body()

    override suspend fun fetchUpcomingMovies(): MovieResponse =
        client.get("$BASE_URL/movie/upcoming?api_key=$API_KEY").body()

    override suspend fun fetchTopRatedMovies(): MovieResponse =
        client.get("$BASE_URL/movie/top_rated?api_key=$API_KEY").body()

    override suspend fun fetchMovieDetails(movieId: Int): ApiMovieDetails =
        client.get("$BASE_URL/movie/$movieId?api_key=$API_KEY").body()

    override suspend fun fetchMovieCredits(movieId: Int): MovieCreditsResponse =
        client.get("$BASE_URL/movie/$movieId/credits?api_key=$API_KEY").body()
}
