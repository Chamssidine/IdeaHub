package com.solodev.ideahub.ui.screen.goalCreationScreen


import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.solodev.ideahub.R
import com.solodev.ideahub.ui.screen.components.CreateGoalButton
import com.solodev.ideahub.ui.screen.components.GoalDialogContent
import com.solodev.ideahub.ui.screen.components.GoalCreationDialog
import com.solodev.ideahub.ui.screen.goalScreen.GoalScreenViewModel

@Composable
fun GoalCreationScreen(
    modifier: Modifier = Modifier,
    onGoalCreated: () -> Unit = {},
    goalScreenViewModel: GoalScreenViewModel = GoalScreenViewModel()
) {
    var showDialog by remember { mutableStateOf(false) }
    Log.d("GoalCreationScreen", "Entering GoalCreationScreen")

    Box(
        modifier = modifier.fillMaxSize(),
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
            Box(
                modifier = modifier
                    .padding(top = dimensionResource(id = R.dimen.padding_extra_large))
            ) {
                CreateGoalButton(
                    width = dimensionResource(id = R.dimen.button_height_large),
                    height = dimensionResource(id = R.dimen.button_height_large),
                    onCreateGoalClicked = {
                        Log.d("GoalCreationScreen", "Create goal button clicked")
                        showDialog = true
                    }
                )
            }
            if (showDialog) {
                Log.d("GoalCreationScreen", "Showing dialog")
                GoalCreationDialog(
                    showDialog = showDialog,
                    onConfirm = {
                        Log.d("GoalCreationDialog", "DialogContent onConfirm called")
                        val newGoal = goalScreenViewModel.createGoal()
                        if (newGoal != null) {
                            Log.d("GoalCreationDialog", "Goal created: ${newGoal.id} ${newGoal.title}")
                            goalScreenViewModel.onGoalCreated(newGoal)
                            goalScreenViewModel.refreshGoals()
                            showDialog = false
                            onGoalCreated()
                        }

                    },

                    onDismissButtonClicked = {
                        Log.d("GoalCreationScreen", "Dialog dismissed")
                        showDialog = false
                    },
                    goalScreenViewModel = goalScreenViewModel,
                )
            }
        }
    }
}



@Preview(showSystemUi = true, showBackground = true)
@Composable
fun WelcomeScreenPreview(){
    GoalDialogContent(viewModel = GoalScreenViewModel(), onConfirm = {}, onCancel = {})
}