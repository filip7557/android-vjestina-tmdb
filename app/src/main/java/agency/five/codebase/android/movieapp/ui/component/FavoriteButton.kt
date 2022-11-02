package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.ui.theme.Blue
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FavoriteButton(
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {
    var selected by remember { mutableStateOf(isSelected) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(width = 65.dp, height = 81.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Blue.copy(alpha = 0.7f))
                .clickable {
                    selected = selected.not()
                },
        ) {
            Icon(
                painter = painterResource(id = if (selected) R.drawable.heart_icon_selected else R.drawable.heart_icon_notselected),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp),
            )
        }
    }

}


@Preview
@Composable
fun FavoriteButtonPreview() {
        FavoriteButton(isSelected = false)
}
