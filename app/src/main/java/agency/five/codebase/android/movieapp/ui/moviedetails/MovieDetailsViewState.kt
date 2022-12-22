package agency.five.codebase.android.movieapp.ui.moviedetails

import agency.five.codebase.android.movieapp.ui.component.ActorCardViewState
import agency.five.codebase.android.movieapp.ui.component.CrewItemViewState

data class MovieDetailsViewState(
    val id: Int,
    val imageUrl: String?,
    val voteAverage: Float,
    val title: String,
    val overview: String,
    val isFavorite: Boolean,
    val crew: List<CrewItemViewState>,
    val cast: List<ActorCardViewState>,
) {
    companion object Empty {
        fun create() = MovieDetailsViewState(
            id = 1,
            imageUrl = null,
            voteAverage = 0.0f,
            title = "",
            overview = "",
            isFavorite = false,
            crew = listOf(),
            cast = listOf()
        )
    }
}

data class ActorViewState(
    val actorCardViewState: ActorCardViewState,
)

data class CrewmanViewState(
    val crewItemViewState: CrewItemViewState,
)
