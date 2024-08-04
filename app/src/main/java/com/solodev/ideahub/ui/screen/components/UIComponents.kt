package com.solodev.ideahub.ui.screen.components

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.solodev.ideahub.R
import com.solodev.ideahub.ui.screen.goalScreen.GoalScreenViewModel
import com.solodev.ideahub.util.Tools
import kotlinx.coroutines.delay


@Composable
fun GoalDialogContent(
    modifier: Modifier = Modifier,
    viewModel: GoalScreenViewModel,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
) {
    Log.d("DialogContent", "Entering DialogContent")

    var showDatePickerDialog by remember { mutableStateOf(false) }

    val uiState by viewModel.goalCreationUiState.collectAsState()
    Box(
        modifier = modifier.wrapContentSize(),
        contentAlignment = Alignment.Center
    ) {
        ElevatedCard(
            modifier = Modifier.wrapContentSize(),
            shape = MaterialTheme.shapes.extraLarge
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        top = dimensionResource(id = R.dimen.padding_medium),
                        start = dimensionResource(id = R.dimen.padding_medium),
                        end = dimensionResource(id = R.dimen.padding_medium)
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                HeaderTitle(title = stringResource(id = R.string.title_and_description))
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
                Box {
                    TextArea(
                        text = uiState.title,
                        onTextChange = { viewModel.updateTitle(it) },
                        label = stringResource(id = R.string.title)
                    )
                }
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
                Box {
                    TextArea(
                        text = uiState.description,
                        onTextChange = { viewModel.updateDescription(it) }
                    )
                }
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
                HeaderTitle(title = stringResource(id = R.string.deadline))
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
                Box {
                    TextButton(onClick = {
                        Log.d("DialogContent", "Date picker button clicked")
                        showDatePickerDialog = true
                    }) {
                        Text(
                            text = if (uiState.deadline.isBlank())
                                stringResource(id = R.string.pick_a_date)
                            else
                                uiState.deadline,
                            modifier = modifier.wrapContentSize(align = Alignment.Center)
                        )
                    }
                    if (showDatePickerDialog) {
                        DatePickerDialog(
                            onConfirmButtonClicked = {
                                viewModel.onConfirmDatePickingDialog(it)
                                showDatePickerDialog = false
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
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
                        Checkbox(
                            checked = uiState.reminderFrequency == "Daily",
                            onCheckedChange = { if (it) viewModel.updateReminderFrequency("Daily") },
                            colors = CheckboxDefaults.colors(
                                uncheckedColor = MaterialTheme.colorScheme.secondaryContainer,
                            ),
                        )
                    }
                    Column(modifier = Modifier) {
                        Text(text = stringResource(id = R.string.weekly))
                        Checkbox(
                            checked = uiState.reminderFrequency == "Weekly",
                            onCheckedChange = { if (it) viewModel.updateReminderFrequency("Weekly") },
                            colors = CheckboxDefaults.colors(
                                uncheckedColor = MaterialTheme.colorScheme.secondaryContainer,
                            ),
                        )
                    }
                    Column(modifier = Modifier) {
                        Text(text = stringResource(id = R.string.monthly))
                        Checkbox(
                            checked = uiState.reminderFrequency == "Monthly",
                            onCheckedChange = { if (it) viewModel.updateReminderFrequency("Monthly") },
                            colors = CheckboxDefaults.colors(
                                uncheckedColor = MaterialTheme.colorScheme.secondaryContainer,
                            ),
                        )
                    }
                }
                if(uiState.hasError)
                {
                    Text(
                        text = uiState.errorMessage!!
                        , color = MaterialTheme.colorScheme.error)
                }
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .align(Alignment.End),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Spacer(modifier = modifier.weight(1f))
                    TextButton(onClick = {
                        Log.d("DialogContent", "Cancel button clicked")
                        onCancel()
                    }) {
                        Text(text = stringResource(id = R.string.cancel))
                    }

                    TextButton(
                        onClick = {
                            Log.d("DialogContent", "Confirm button clicked")
                            onConfirm()
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.confirm),
                            textAlign = TextAlign.Center
                        )
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

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    onConfirmButtonClicked: (String)-> Unit = {}
) {
    var dateResult by remember {
        mutableStateOf("Date Picker")
    }
    val openDialog = remember { mutableStateOf(true) }
    val datePickerState = rememberDatePickerState()
    val confirmEnabled = derivedStateOf { datePickerState.selectedDateMillis != null }

    if (openDialog.value) {
        androidx.compose.material3.DatePickerDialog(
            onDismissRequest = { openDialog.value = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        var date = "No Selection"
                        if (datePickerState.selectedDateMillis != null) {
                            date = Tools.convertLongToTime((datePickerState.selectedDateMillis!!))
                        }
                        dateResult = date
                        onConfirmButtonClicked(dateResult)
                    },
                    enabled = confirmEnabled.value
                ) {
                    Text(text = "Confirm")
                }
            }
        ) {
            DatePicker(state = datePickerState)
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
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent,
            ),
            modifier = modifier.border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = MaterialTheme.shapes.medium

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
@Composable
fun GoalCreationDialog(
    modifier: Modifier = Modifier,
    showDialog: Boolean = false,
    onConfirm: () -> Unit = {},
    onDismissButtonClicked: () -> Unit = {},
    goalScreenViewModel: GoalScreenViewModel,
) {
    var animateIn by remember { mutableStateOf(false) }

    LaunchedEffect(showDialog) {
        Log.d("GoalCreationDialog", "LaunchedEffect triggered with showDialog = $showDialog")
        animateIn = showDialog
    }

    if (showDialog) {
        Log.d("GoalCreationDialog", "Dialog is visible")
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
                    GoalDialogContent(
                        modifier = modifier,
                        viewModel = goalScreenViewModel,
                        onConfirm = onConfirm,
                        onCancel = {
                            Log.d("GoalCreationDialog", "DialogContent onCancel called")
                            onDismissButtonClicked()
                            animateIn = false
                        },
                    )
                }
            }
        }
    }
}


@Composable
fun MenuSample(
    modifier: Modifier = Modifier,
    isCompleted: Boolean = false,
    onEditClicked: () -> Unit = {},
    onMarkAsCompletedClicked: () -> Unit = {},
    onDeleteClicked: () -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.CenterEnd),
        contentAlignment = Alignment.CenterEnd
    ) {
        IconButton(onClick = { expanded = true }) {
            Icon(Icons.Default.MoreVert, contentDescription = "Localized description")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { Text(stringResource(id = R.string.edit)) },
                onClick = onEditClicked,
                leadingIcon = { Icon(Icons.Outlined.Edit, contentDescription = null) }
            )
            if(!isCompleted)
            {
                DropdownMenuItem(
                    text = { Text(stringResource(id = R.string.mark_as_completed)) },
                    onClick = onMarkAsCompletedClicked,
                    leadingIcon = { Icon(painter = painterResource(id = R.drawable.check_circle_24px_filled), contentDescription = null ) }
                )

            }

            DropdownMenuItem(
                text = { Text(stringResource(id = R.string.delete)) },
                onClick = onDeleteClicked,
                leadingIcon = { Icon(painter = painterResource(id = R.drawable.delete_forever_24px), contentDescription = null) },
                trailingIcon = { Text("F11", textAlign = TextAlign.Center) }
            )
        }
    }
}
