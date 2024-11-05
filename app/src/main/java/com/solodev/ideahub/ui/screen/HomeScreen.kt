package com.solodev.ideahub.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.solodev.ideahub.R

import com.solodev.ideahub.model.goalTabItems
import com.solodev.ideahub.ui.screen.components.DayPlanDialog
import com.solodev.ideahub.ui.screen.components.FloatingButton
import com.solodev.ideahub.ui.screen.goalScreen.ActiveDiscussionSection
import com.solodev.ideahub.ui.screen.dayplanScreen.DayPlan
import com.solodev.ideahub.ui.screen.dayplanScreen.DayPlanViewModel
import com.solodev.ideahub.ui.screen.goalScreen.GoalScreen
import com.solodev.ideahub.ui.screen.goalScreen.GoalScreenTab
import com.solodev.ideahub.ui.screen.goalScreen.GoalScreenViewModel
import com.solodev.ideahub.ui.screen.popularGroup.PopularGroupScreen
import com.solodev.ideahub.ui.screen.popularGroup.PopularGroupViewModel


@Composable fun HomeScreen(
    goalScreenViewModel: GoalScreenViewModel = hiltViewModel<GoalScreenViewModel>(),
    dayPlanViewModel: DayPlanViewModel =  hiltViewModel<DayPlanViewModel>(),
    popularGroupScreenViewModel: PopularGroupViewModel =  hiltViewModel<PopularGroupViewModel>(),
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ){
        item {
            CustomSearchBar(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            )
        }
        item {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))
        }
        item {
            TabSection(
                goalScreenViewModel,
                dayPlanViewModel,
                popularGroupScreenViewModel,
            )
        }

    }

}

@Composable
fun TabSection(
    goalScreenViewModel: GoalScreenViewModel,
    dayPlanViewModel: DayPlanViewModel,
    popularGroupScreenViewModel: PopularGroupViewModel,
) {
    val goalTabItems = goalTabItems
    var state by remember { mutableStateOf(0) }
    var tabtitle by remember { mutableStateOf(goalTabItems[0].title)  }

    Column {
        ScrollableTabRow(
            containerColor = Color.Transparent,
            modifier = Modifier.wrapContentWidth(),
            selectedTabIndex = state,
            edgePadding = dimensionResource(id = R.dimen.padding_medium)

        ) {
            goalTabItems.forEachIndexed { index, title ->

                GoalScreenTab(
                    tabTitle = stringResource(id = title.title),
                    onSelected = { state = index ; tabtitle = title.title },
                    selected = index == state
                )
            }
        }
        MainTabScreen(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
            tabTitle = stringResource(id = tabtitle),
            goalScreenViewModel = goalScreenViewModel,
            dayPlanViewModel = dayPlanViewModel,
            popularGroupScreenViewModel = popularGroupScreenViewModel,
            selectedTabIndex = state
        )
    }
}

@Composable
fun MainTabScreen(
    modifier: Modifier = Modifier,
    selectedTabIndex: Int = 0,
    tabTitle: String,
    goalScreenViewModel: GoalScreenViewModel,
    dayPlanViewModel: DayPlanViewModel,
    popularGroupScreenViewModel: PopularGroupViewModel,

    ) {

    var showDialog by remember { mutableStateOf(false) }
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = tabTitle,
            style = MaterialTheme.typography.displayMedium
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))
        when (selectedTabIndex) {
            0 -> {
                GoalScreen(goalScreenViewModel)
            }
            1 -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    DayPlan(
                        viewModel = dayPlanViewModel
                    )
                    if (showDialog) {
                        dayPlanViewModel.clearUiState()
                        Log.d("GoalCreationScreen", "Showing dialog")
                        DayPlanDialog(
                            modifier = modifier,
                            showDialog = showDialog,
                            onConfirm = {
                                val dayPlanItem = dayPlanViewModel.createDayPlanItem()
                                if (dayPlanItem != null) {
                                    dayPlanViewModel.addDayPlanItem(dayPlanItem)
                                    showDialog = false
                                }

                            },
                            onDismiss = {showDialog = false},
                            dayPlanViewModel = dayPlanViewModel
                        )
                    }
                    Spacer(modifier = modifier.weight(1f))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        contentAlignment = Alignment.BottomEnd
                    )
                    {
                        FloatingButton(onClick = {
                            showDialog = true
                        })
                    }
                }

            }
            2 -> {
                Box(modifier = Modifier.fillMaxWidth()) {
                    PopularGroupScreen(
                        viewModel = popularGroupScreenViewModel
                    )
                }
            }
            3 -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    ActiveDiscussionSection()
                }
            }

        }
    }


}


@Preview(showBackground = true)
@Composable fun HomeScreenPreview() {
    HomeScreen()

}