package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.mock.MoviesMock.getMoviesList
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
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
    onIconClick: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = AbsoluteRoundedCornerShape(10.dp),
    ) {
        AsyncImage(
            model = movieCardViewState.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(1f)
                .clickable(onClick = onClick),
            contentScale = ContentScale.FillWidth
        )
        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier
                .padding(8.dp),
        ) {
            FavoriteButton(
                isSelected = movieCardViewState.isFavorite,
                onClick = onIconClick,
                modifier = Modifier
                    .size(30.dp)
            )
        }
    }
}

@Preview
@Composable
fun MovieCardPreview() {
    val movie = getMoviesList()[1]
    MovieCard(movieCardViewState = MovieCardViewState(imageUrl = movie.imageUrl,
        movieId = movie.id, isFavorite = movie.isFavorite),
        modifier = Modifier
            .size(200.dp, 295.dp),
        onIconClick = {},
        onClick = { /*TODO later with data*/ }
    )
}
