package agency.five.codebase.android.movieapp.data.network.model

import agency.five.codebase.android.movieapp.model.Actor
import agency.five.codebase.android.movieapp.model.Crewman
import agency.five.codebase.android.movieapp.model.Movie
import agency.five.codebase.android.movieapp.model.MovieDetails
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiMovieDetails(
    @SerialName("adult")
    val adult: Boolean,

    @SerialName("backdrop_path")
    val backdrop_path: String?,

    @Contextual
    @SerialName("belongs_to_collection")
    val belongs_to_collection: BelongsToCollection?,

    @SerialName("budget")
    val budget: Int,

    @SerialName("genres")
    val genres: List<Genre>,

    @SerialName("homepage")
    val homepage: String?,

    @SerialName("id")
    val id: Int,

    @SerialName("imdb_id")
    val imdb_id: String?,

    @SerialName("original_language")
    val original_language: String,

    @SerialName("original_title")
    val original_title: String,

    @SerialName("overview")
    val overview: String?,

    @SerialName("popularity")
    val popularity: Double,

    @SerialName("poster_path")
    val poster_path: String?,

    @SerialName("production_companies")
    val production_companies: List<ProductionCompany>,

    @SerialName("production_countries")
    val production_countries: List<ProductionCountry>,

    @SerialName("release_date")
    val release_date: String,

    @SerialName("revenue")
    val revenue: Int,

    @SerialName("runtime")
    val runtime: Int?,

    @SerialName("spoken_languages")
    val spoken_languages: List<SpokenLanguage>,

    @SerialName("status")
    val status: String,

    @SerialName("tagline")
    val tagline: String?,

    @SerialName("title")
    val title: String,

    @SerialName("video")
    val video: Boolean,

    @SerialName("vote_average")
    val vote_average: Double,

    @SerialName("vote_count")
    val vote_count: Int
){
    fun toMovieDetails(movie: Movie, crew: List<Crewman>, cast: List<Actor>) = MovieDetails(
        movie = movie,
        voteAverage = vote_average.toFloat(),
        releaseDate = release_date,
        language = original_language,
        runtime = runtime!!,
        crew = crew,
        cast = cast
    )
}

@Serializable
data class BelongsToCollection(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String,

    @SerialName("backdrop_path")
    val backdrop_path: String?,

    @SerialName("poster_path")
    val poster_path: String?,
)

@Serializable
data class Genre(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String
)

@Serializable
data class ProductionCompany(
    @SerialName("id")
    val id: Int,

    @SerialName("logo_path")
    val logo_path: String? = null,

    @SerialName("name")
    val name: String,

    @SerialName("origin_country")
    val origin_country: String
)

@Serializable
data class ProductionCountry(
    @SerialName("iso_3166_1")
    val iso_3166_1: String,

    @SerialName("name")
    val name: String
)

@Serializable
data class SpokenLanguage(
    @SerialName("iso_639_1")
    val iso_639_1: String,

    @SerialName("name")
    val name: String
)
