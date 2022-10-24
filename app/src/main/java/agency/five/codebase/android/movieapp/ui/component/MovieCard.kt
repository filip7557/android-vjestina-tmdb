package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.mock.MoviesMock.getMoviesList
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

data class MovieCardViewState(
    val imageUrl: String?,
    val movieId: Int,
    val isFavorite: Boolean,
)

@Composable
fun MovieCard(
    movieCardViewState: MovieCardViewState,
    modifier: Modifier = Modifier
) {
    Card(
        modifier.size(width = 175.dp, height = 270.dp),
        shape = AbsoluteRoundedCornerShape(15.dp),
    ) {
        AsyncImage(
            model = movieCardViewState.imageUrl,
            contentDescription = null,
            modifier
                .scale(1.05f),
        )
        Box(
            modifier.size(width = 150.dp, height = 270.dp),
            contentAlignment = Alignment.TopStart
        ) {
            FavoriteButton(
                isSelected = movieCardViewState.isFavorite,
            )
        }
    }
}

@Preview
@Composable
fun MovieCardPreview() {
    val movie = getMoviesList()[1]
    MovieCard(movieCardViewState = MovieCardViewState(imageUrl = movie.imageUrl, movieId = movie.id, isFavorite = movie.isFavorite))
}