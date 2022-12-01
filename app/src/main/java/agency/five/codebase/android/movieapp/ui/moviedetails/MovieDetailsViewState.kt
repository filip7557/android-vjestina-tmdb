package agency.five.codebase.android.movieapp.ui.moviedetails

import agency.five.codebase.android.movieapp.ui.component.ActorCardViewState
import agency.five.codebase.android.movieapp.ui.component.CrewItemViewState

data class MovieDetailsViewState(
    val id: Int = 1,
    val imageUrl: String? = "",
    val voteAverage: Float = 0.0f,
    val title: String = "",
    val overview: String = "",
    val isFavorite: Boolean = false,
    val crew: List<CrewItemViewState> = listOf(),
    val cast: List<ActorCardViewState> = listOf(),
)

data class ActorViewState(
    val actorCardViewState: ActorCardViewState,
)

data class CrewmanViewState(
    val crewItemViewState: CrewItemViewState,
)
