package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.model.Actor
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

data class ActorCardViewState(
    val imageUrl: String?,
    val name: String,
    val character: String,
)

@Composable
fun ActorCard(
    actorCardViewState: ActorCardViewState,
    modifier: Modifier = Modifier,
)
{
    Card(
        elevation = 50.dp,
        shape = AbsoluteRoundedCornerShape(18.dp),
        modifier = modifier
    ) {
        Column {
            AsyncImage(
                model = actorCardViewState.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(1f),
                contentScale = ContentScale.FillWidth,
            )
            Column(
                modifier = Modifier.fillMaxWidth(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = actorCardViewState.name,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = actorCardViewState.character,
                    fontSize = 15.sp,
                    color = Color.Gray,
                )
            }
            Spacer(
                modifier = Modifier
                    .size(5.dp)
            )
        }
    }
}

@Preview
@Composable
private fun ActorCardPreview(){
    val actor: Actor = MoviesMock.getActor()
    val actorCardViewState = ActorCardViewState(
        imageUrl = actor.imageUrl,
        name = actor.name,
        character = actor.character,
    )
    ActorCard(
        actorCardViewState = actorCardViewState,
        modifier = Modifier
            .size(width = 150.dp, height = 270.dp)
    )

}
