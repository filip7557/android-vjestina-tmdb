package agency.five.codebase.android.movieapp.ui.moviedetails

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.ui.component.ActorCard
import agency.five.codebase.android.movieapp.ui.component.CircularProgressBar
import agency.five.codebase.android.movieapp.ui.component.CrewItem
import agency.five.codebase.android.movieapp.ui.component.FavoriteButton
import agency.five.codebase.android.movieapp.ui.moviedetails.mapper.MovieDetailsMapper
import agency.five.codebase.android.movieapp.ui.moviedetails.mapper.MovieDetailsMapperImpl
import agency.five.codebase.android.movieapp.ui.theme.MovieAppTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

private val MovieDetailsMapper: MovieDetailsMapper = MovieDetailsMapperImpl()

val movieDetailsViewState = MovieDetailsMapper.toMovieDetailsViewState(MoviesMock.getMovieDetails())

@Composable
fun MovieDetailsRoute(
    onFavoriteButtonClick: () -> Unit
) {
    val detailsViewState by remember { mutableStateOf(movieDetailsViewState) }

    MovieDetailsScreen(
        detailsViewState,
        onFavoriteButtonClick
    )
}

@Composable
fun MovieDetailsScreen(
    movieDetailsViewState: MovieDetailsViewState,
    onFavoriteButtonClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colors.background)
            .verticalScroll(state = rememberScrollState()),
    ) {
            MovieImage(movieDetailsViewState, onFavoriteButtonClick)

            MovieOverview(movieDetailsViewState)

            MovieCrewman(movieDetailsViewState)

            MovieCast(movieDetailsViewState)
    }
}

@Composable
fun MovieCast(
    movieDetailsViewState: MovieDetailsViewState,
) {
    Column {
        Text(
            text = stringResource(id = R.string.topBilledCastTitle),
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 8.dp)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 8.dp)
        ) {
            items(movieDetailsViewState.cast.size) { actor ->
                ActorCard(
                    actorCardViewState = movieDetailsViewState.cast[actor].actorCardViewState,
                    modifier = Modifier
                        .size(width = 140.dp, height = 220.dp)
                )
            }
        }
    }
}

@Composable
fun MovieCrewman(
    movieDetailsViewState: MovieDetailsViewState
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(10.dp),
        userScrollEnabled = false,
        modifier = Modifier
            .height(150.dp)
    ) {
        items(
            movieDetailsViewState.crew,
        ) { crewman ->
            CrewItem(
                crewItemViewState = crewman.crewItemViewState,
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 8.dp)
            )
        }
    }
}

@Composable
fun MovieOverview(
    movieDetailsViewState: MovieDetailsViewState
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 15.dp)
    ) {
        Text(
            text =  stringResource(id = R.string.overviewTitle),
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .padding(vertical = 8.dp)
        )
        Text(
            text = movieDetailsViewState.overview,
            color = MaterialTheme.colors.onSurface,
            fontSize = 15.sp,
        )
    }
}

@Composable
fun MovieImage(
    movieDetailsViewState: MovieDetailsViewState,
    onFavoriteButtonClick: () -> Unit,
) {
        Box(
            contentAlignment = Alignment.BottomStart
        ) {
            AsyncImage(
                model = movieDetailsViewState.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .height(350.dp)
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    CircularProgressBar(
                        score = movieDetailsViewState.voteAverage,
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.userScoreTitle),
                        fontSize = 14.sp,
                        color = MaterialTheme.colors.onPrimary,
                    )
                }
                Text(
                    text = movieDetailsViewState.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                )
                FavoriteButton(
                    isSelected = movieDetailsViewState.isFavorite,
                    onClick = onFavoriteButtonClick,
                    modifier = Modifier
                        .size(50.dp)
                        .padding(8.dp)
                )
            }
        }
}

@Preview
@Composable
fun MovieDetailsScreenPreview() {
    MovieAppTheme {
        MovieDetailsScreen(movieDetailsViewState = movieDetailsViewState) {

        }
    }
}
