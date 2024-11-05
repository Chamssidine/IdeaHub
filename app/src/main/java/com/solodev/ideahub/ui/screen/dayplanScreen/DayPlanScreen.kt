package com.solodev.ideahub.ui.screen.dayplanScreen

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.solodev.ideahub.R
import com.solodev.ideahub.ui.screen.components.EditDayPlanDialog
import com.solodev.ideahub.ui.screen.components.MenuSample
import kotlinx.coroutines.delay

@Composable
fun DayPlan(
    viewModel: DayPlanViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    val dayPlanUiState by viewModel.dayPlanDialogUIState.collectAsState()
    var openEditDialog by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if(uiState.dayPlans.isEmpty()) {
            Text(
                text = stringResource(id = R.string.no_day_plans),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        else {
            uiState.dayPlans.forEachIndexed { index, dayPlanItemUiState ->
                DayPlanItem(
                    title = dayPlanItemUiState.title,
                    description = dayPlanItemUiState.description,
                    creationDate = dayPlanItemUiState.creationDate,
                    deadline = dayPlanItemUiState.deadline,
                    priority = dayPlanItemUiState.priority,
                    progress = dayPlanItemUiState.progress,
                    isCompleted = dayPlanItemUiState.isCompleted,
                    onEdit = {
                        viewModel.onEditGoal(dayPlanItemUiState)
                        openEditDialog = true
                    },
                    onDelete = { viewModel.deletePlan(dayPlanItemUiState) },
                    onMarkAsCompleted = { viewModel.toggleCompletion(index) }
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))
            }
        }

        if (openEditDialog) {
            EditDayPlanDialog(
                onCancel = { openEditDialog = false },
                onConfirm = {
                    viewModel.updateDayPlanList()
                    if(!viewModel.dayPlanDialogUIState.value.hasError)
                    {
                        openEditDialog = false
                    }

                },
                onProgressChange = {viewModel.updateProgress(it)},
                showDialog = openEditDialog,
                dayPlanItemUiState = dayPlanUiState,
                onHighPriorityClicked = { viewModel.updatePriority(Priority.HIGH) },
                onLowPriorityClicked = { viewModel.updatePriority(Priority.LOW) },
                onMediumPriorityClicked = { viewModel.updatePriority(Priority.MEDIUM) }
            )
        }

    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayPlanItem(
    modifier: Modifier = Modifier,
    isCompleted: Boolean = false,
    title: String = "App creation",
    description: String = "This is an important project for me",
    creationDate: String = "01/01/2023",
    deadline: String = "29/03/2024",
    priority: Priority = Priority.HIGH,
    progress: Int = 0,
    delete: Boolean = false,
    onDelete: () -> Unit = {},
    onEdit: () -> Unit = {},
    onMarkAsCompleted: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        delay(600L)
        visible = true
    }
    Log.d("DayPlanItem", "DayPlanItem called:${progress}")
    if (!delete) {
        ElevatedCard(
            onClick = { expanded = !expanded },
            modifier = modifier
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                ),
            shape = MaterialTheme.shapes.medium,
        ) {
            Row(
                modifier = modifier
                    .padding(dimensionResource(id = R.dimen.padding_small)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyMedium,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
                    Text(
                        text = creationDate,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.priority),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
                    Text(
                        text = priority.name,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentWidth(),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.due_date),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
                    Text(
                        text = deadline,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Box (
                    modifier = modifier
                        .wrapContentWidth(),
                ){
                    if(progress in 1..99) {

                        CircularProgressIndicator (
                            progress = progress / 100f,
                            modifier = modifier.size(20.dp),
                            color = MaterialTheme.colorScheme.primary,
                            strokeWidth = ProgressIndicatorDefaults.CircularStrokeWidth,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    }
                    if(isCompleted || progress == 100) {
                        Icon(
                            Icons.Filled.CheckCircle,
                            contentDescription ="icon_completed",
                            modifier = modifier.size(20.dp)
                        )
                    }
                }

                MenuSample(
                    onDelete = onDelete,
                    onEditClicked = onEdit,
                    onMarkAsCompleted = onMarkAsCompleted
                )
            }
        }
    }
}
