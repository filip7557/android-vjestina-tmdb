package agency.five.codebase.android.movieapp.ui.moviedetails

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.data.repository.FakeMovieRepository
import agency.five.codebase.android.movieapp.ui.component.ActorCard
import agency.five.codebase.android.movieapp.ui.component.CircularProgressBar
import agency.five.codebase.android.movieapp.ui.component.CrewItem
import agency.five.codebase.android.movieapp.ui.component.FavoriteButton
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.Dispatchers

@Composable
fun MovieDetailsRoute(
    viewModel: MovieDetailsViewModel
) {
    val movieDetailsViewState: MovieDetailsViewState by viewModel.movieDetailsViewState.collectAsState()

    MovieDetailsScreen(
        movieDetailsViewState = movieDetailsViewState,
        onFavoriteButtonClick = {
            viewModel.toggleFavorite(it)
        }
    )
}

@Composable
fun MovieDetailsScreen(
    movieDetailsViewState: MovieDetailsViewState,
    onFavoriteButtonClick: (Int) -> Unit,
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
                    actorCardViewState = movieDetailsViewState.cast[actor],
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
                crewItemViewState = crewman,
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
    onFavoriteButtonClick: (Int) -> Unit,
) {
        Box(
            contentAlignment = Alignment.BottomStart,
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = movieDetailsViewState.imageUrl,
                contentDescription = null,
                alignment = Alignment.TopCenter,
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxSize(),
                contentScale = ContentScale.Crop,
            )

            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                MaterialTheme.colors.onSurface
                            )
                        )
                    )
                    .align(Alignment.Center)
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
                    onClick = { onFavoriteButtonClick(movieDetailsViewState.id) },
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
        MovieDetailsRoute(viewModel = MovieDetailsViewModel(FakeMovieRepository(Dispatchers.IO), MovieDetailsMapperImpl(), 2))
    }
}
