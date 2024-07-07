package com.solodev.ideahub.ui.screen.welcomeScreen

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.solodev.ideahub.R
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier
){
    Box(modifier = modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {


        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_large)))
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_large)))
            Box(
                modifier = modifier
                    .padding(top = dimensionResource(id = R.dimen.padding_large))
                    .align(Alignment.Start)
            ) {
                Text(
                    text = stringResource(id = R.string.home_begin_tour),
                    style = MaterialTheme.typography.displayLarge
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_large)))
            Text(
                text = stringResource(id = R.string.home_no_objectives),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_large)))
            Box(
                modifier = modifier
                    .padding(top = dimensionResource(id = R.dimen.padding_extra_large))
            ) {
                PulsingElevatedButton(
                    width = dimensionResource(id = R.dimen.button_width_full_width),
                    height = dimensionResource(id = R.dimen.button_height_medium),
                    onLetsGoClicked = {}
                )
            }

        }
    }
 }


@Composable
fun PulsingElevatedButton(
    modifier: Modifier = Modifier,
    width: Dp,
    height: Dp,
    onLetsGoClicked: () -> Unit
) {
    var targetWidth by remember { mutableStateOf(width) }
    var targetHeight by remember { mutableStateOf(height) }

    // Animate the width and height
    val animatedWidth by animateDpAsState(
        targetValue = targetWidth,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val animatedHeight by animateDpAsState(
        targetValue = targetHeight,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    LaunchedEffect(Unit) {
        while (true) {
            targetWidth = if (targetWidth == width) {
                width + 10.dp
            } else {
                width
            }
            targetHeight = if (targetHeight == height) {
                height + 5.dp
            } else {
                height
            }
            delay(1000)
        }
    }

    ElevatedButton(
        onClick = onLetsGoClicked,
        modifier = modifier
            .width(animatedWidth)
            .height(animatedHeight)
    ) {
        Text(
            text = stringResource(id = R.string.home_create_objective_button),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun WelcomeScreenPreview(){
    WelcomeScreen()
}