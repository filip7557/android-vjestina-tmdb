package agency.five.codebase.android.movieapp.data.network.model

import agency.five.codebase.android.movieapp.data.network.BASE_IMAGE_URL
import agency.five.codebase.android.movieapp.model.Movie
import agency.five.codebase.android.movieapp.model.MovieDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiMovieDetails(
    @SerialName("id")
    val id: Int,

    @SerialName("original_language")
    val original_language: String,

    @SerialName("overview")
    val overview: String,

    @SerialName("poster_path")
    val poster_path: String?,

    @SerialName("release_date")
    val release_date: String,

    @SerialName("runtime")
    val runtime: Int,

    @SerialName("title")
    val title: String,

    @SerialName("vote_average")
    val vote_average: Double,
){
    fun toMovieDetails(isFavorite: Boolean, crew: List<ApiCrew>, cast: List<ApiCast>) = MovieDetails(
        movie = Movie(
            id = id,
            title = title,
            overview = overview,
            imageUrl = "$BASE_IMAGE_URL/$poster_path",
            isFavorite = isFavorite
        ),
        voteAverage = vote_average.toFloat(),
        releaseDate = release_date,
        language = original_language,
        runtime = runtime,
        crew = crew.map { it.toCrewman() },
        cast = cast.map { it.toActor() }
    )
}
