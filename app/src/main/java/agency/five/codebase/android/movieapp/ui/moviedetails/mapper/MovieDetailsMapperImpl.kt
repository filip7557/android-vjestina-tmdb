package agency.five.codebase.android.movieapp.ui.moviedetails.mapper

import agency.five.codebase.android.movieapp.model.MovieDetails
import agency.five.codebase.android.movieapp.ui.component.ActorCardViewState
import agency.five.codebase.android.movieapp.ui.component.CrewItemViewState
import agency.five.codebase.android.movieapp.ui.moviedetails.ActorViewState
import agency.five.codebase.android.movieapp.ui.moviedetails.CrewmanViewState
import agency.five.codebase.android.movieapp.ui.moviedetails.MovieDetailsViewState

class MovieDetailsMapperImpl : MovieDetailsMapper {
    override fun toMovieDetailsViewState(movieDetails: MovieDetails): MovieDetailsViewState {
        val crewmen = mutableListOf<CrewmanViewState>()
        val actors = mutableListOf<ActorViewState>()

        for(crewman in movieDetails.crew) {
            crewmen.add(CrewmanViewState(CrewItemViewState(crewman.name, crewman.job)))
        }

        for(actor in movieDetails.cast) {
            actors.add(ActorViewState(ActorCardViewState(actor.imageUrl, actor.name, actor.character)))
        }

        return MovieDetailsViewState(
            movieDetails.movie.id,
            movieDetails.movie.imageUrl,
            movieDetails.voteAverage,
            movieDetails.movie.title,
            movieDetails.movie.overview,
            movieDetails.movie.isFavorite,
            crewmen,
            actors,
        )
    }
}
