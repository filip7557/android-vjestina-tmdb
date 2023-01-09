package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.ui.theme.GreenProgressBar
import agency.five.codebase.android.movieapp.ui.theme.GreenProgressBarBackground
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CircularProgressBar(
    modifier: Modifier = Modifier,
    score: Float,
){
    val animationPlayed = remember { mutableStateOf(false) }
    val currentPercentage = animateFloatAsState(
        targetValue = (if(animationPlayed.value) score/10 else 0f),
        animationSpec = tween(
            durationMillis = 650
        )
    )

    LaunchedEffect(key1 = true){
        animationPlayed.value = true
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(20.dp * 2f)
    ){
        Canvas(modifier = Modifier
            .padding(5.dp)
            .size(20.dp * 2f)){
            drawArc(
                color = GreenProgressBarBackground,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(5.dp.toPx(), cap = StrokeCap.Round)
            )
            drawArc(
                color = GreenProgressBar,
                startAngle = -90f,
                sweepAngle = 360 * currentPercentage.value,
                useCenter = false,
                style = Stroke(5.dp.toPx(), cap = StrokeCap.Round)
            )
        }

        Text(
            text = String.format("%.2f", score),
            fontSize = 11.sp,
            color = MaterialTheme.colors.onPrimary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CircularProgressBarPreview(){
    CircularProgressBar(score = 0.85f)
}
