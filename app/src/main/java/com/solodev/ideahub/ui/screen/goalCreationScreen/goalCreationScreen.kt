package com.solodev.ideahub.ui.screen.goalCreationScreen


import android.widget.Space
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.solodev.ideahub.R
import kotlinx.coroutines.delay

@Composable
fun GoalCreationScreen(
    modifier: Modifier = Modifier
){
    var showDialog by remember { mutableStateOf(false)}
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
                    text = stringResource(id = R.string.first_goal_creation),
                    style = MaterialTheme.typography.displayLarge
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_large)))
            Text(
                text = stringResource(id = R.string.home_no_objectives),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_large)))
            Box (
                modifier = modifier
                    .padding(top = dimensionResource(id = R.dimen.padding_extra_large))
            ) {
                CreateGoalButton (
                    width = dimensionResource(id = R.dimen.button_height_large),
                    height = dimensionResource(id = R.dimen.button_height_large),
                    onCreateGoalClicked = {
                        showDialog = true
                    }
                )
            }
           if (showDialog){
                GoalCreationDialog(
                    showDialog = showDialog,
                    onCreateButtonClicked = {showDialog = false},
                    onDismissButtonClicked = {showDialog = true}
                )
            }

        }
    }
}

@Composable
fun GoalCreationDialog(
    modifier: Modifier = Modifier,
    showDialog: Boolean = false,
    onCreateButtonClicked: () -> Unit = {},
    onDismissButtonClicked: () -> Unit = {}
) {
    var animateIn by remember { mutableStateOf(false) }

    LaunchedEffect(showDialog) {
        animateIn = showDialog
    }

    if (showDialog) {
        Dialog(onDismissRequest = onDismissButtonClicked) {
            AnimatedVisibility(
                visible = animateIn,
                enter = fadeIn(spring(stiffness = Spring.StiffnessHigh)) + scaleIn(
                    initialScale = .8f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMediumLow
                    )
                ),
                exit = slideOutVertically { it / 8 } + fadeOut() + scaleOut(targetScale = .95f)
            ) {
                Column(
                    modifier = modifier
                        .padding(dimensionResource(id = R.dimen.padding_medium)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DialogContent(
                        modifier = modifier
                    )
                    ElevatedButton(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = onCreateButtonClicked
                    ) {
                        Text(text = stringResource(id = R.string.create))
                    }
                }
            }
        }
    }
}

@Composable
fun DialogContent(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {}
){
    Box(
        modifier = modifier.wrapContentSize(),
        contentAlignment = Alignment.Center
    ) {
        ElevatedCard(
            modifier = Modifier
               // .padding(dimensionResource(id = R.dimen.padding_medium))
                .wrapContentSize(),
        ) {
            Column(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_medium)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                HeaderTitle(title = stringResource(id = R.string.title_and_description))
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
                Box()
                {
                    TextArea(text = "", onTextChange = {}, label = stringResource(id = R.string.title))
                }
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
                Box()
                {
                    TextArea(text = "", onTextChange = {},)
                }
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
                HeaderTitle(title = stringResource(id = R.string.deadline))
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
                Box()
                {
                    Text(text = stringResource(id = R.string.objectives_due_date),modifier = modifier.wrapContentSize(align = Alignment.Center))
                }
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
                HeaderTitle(title = stringResource(id = R.string.reminder_frequency))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(id = R.dimen.padding_medium)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier) {
                        Text(text = stringResource(id = R.string.daily))
                        Checkbox(checked = false, onCheckedChange = {})
                    }
                    Column(modifier = Modifier) {
                        Text(text = stringResource(id = R.string.weekly))
                        Checkbox(checked = false, onCheckedChange = {})
                    }
                    Column(modifier = Modifier) {
                        Text(text = stringResource(id = R.string.monthly))
                        Checkbox(checked = false, onCheckedChange = {})
                    }

                }
           }
       }
   }


}

@Composable
fun CreateGoalButton(
    modifier: Modifier = Modifier,
    width: Dp,
    height: Dp,
    onCreateGoalClicked: () -> Unit
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
        onClick = onCreateGoalClicked,
        modifier = modifier.wrapContentSize(),
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Icon(
            Icons.Filled.AddCircle, contentDescription = "create_goal_button",
            modifier = modifier.size(animatedWidth)
            ,

        )
    }
}


@Composable
fun HeaderTitle(
    modifier: Modifier = Modifier,
    title: String
){
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
            ,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary,
            ),
            shape = MaterialTheme.shapes.extraLarge,
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Text(
                text = title,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

@Composable
fun TextArea(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    singleLine: Boolean = true,
    label: String = stringResource(id = R.string.description_example)
) {
    Box(
        modifier = modifier.wrapContentSize(),
        contentAlignment = Alignment.Center
    ){
        Card (
            shape = MaterialTheme.shapes.small,
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent,
            ),
            modifier = modifier.border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = MaterialTheme.shapes.small
            )
        ){
            TextField(
                value = text, onValueChange = onTextChange,
                singleLine = singleLine,
                label = {Text(text = label)},
                //placeholder = {Text(text = stringResource(id = R.string.description_example))},

                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
    }
}
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun WelcomeScreenPreview(){
    GoalCreationDialog()
}