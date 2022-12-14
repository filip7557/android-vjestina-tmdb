package agency.five.codebase.android.movieapp.data.network

import agency.five.codebase.android.movieapp.data.network.model.ApiMovieDetails
import agency.five.codebase.android.movieapp.data.network.model.MovieCreditsResponse
import agency.five.codebase.android.movieapp.data.network.model.MovieResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"
private const val BASE_URL = "https://api.themoviedb.org/3"
private const val API_KEY = "77229919eb9b0ff7a109794fa8c881c4"

class MovieServiceImpl(private val client: HttpClient) : MovieService {
    override suspend fun fetchPopularMovies(): MovieResponse {
        return client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = BASE_URL
                path("/movie/popular")
                parameter("api_key", API_KEY)
            }
        } as MovieResponse
    }

    override suspend fun fetchNowPlayingMovies(): MovieResponse {
        return client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = BASE_URL
                path("/movie/now_playing")
                parameter("api_key", API_KEY)
            }
        } as MovieResponse
    }

    override suspend fun fetchUpcomingMovies(): MovieResponse {
        return client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = BASE_URL
                path("/movie/upcoming")
                parameter("api_key", API_KEY)
            }
        } as MovieResponse
    }

    override suspend fun fetchTopRatedMovies(): MovieResponse {
        return client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = BASE_URL
                path("/movie/top_rated")
                parameter("api_key", API_KEY)
            }
        } as MovieResponse
    }

    override suspend fun fetchMovieDetails(movieId: Int): ApiMovieDetails {
        return client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = BASE_URL
                path("/$movieId")
                parameter("api_key", API_KEY)
            }
        } as ApiMovieDetails
    }

    override suspend fun fetchMovieCredits(movieId: Int): MovieCreditsResponse {
        return client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = BASE_URL
                path("/$movieId/credits")
                parameter("api_key", API_KEY)
            }
        } as MovieCreditsResponse
    }
}
