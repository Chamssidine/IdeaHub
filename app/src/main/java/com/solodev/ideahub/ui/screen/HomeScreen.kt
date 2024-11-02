package com.solodev.ideahub.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.solodev.ideahub.R

import com.solodev.ideahub.model.goalTabItems
import com.solodev.ideahub.ui.screen.goalScreen.DayPlanViewModel
import com.solodev.ideahub.ui.screen.goalScreen.GoalScreenTab
import com.solodev.ideahub.ui.screen.goalScreen.GoalScreenViewModel
import com.solodev.ideahub.ui.screen.goalScreen.MainTabScreen
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

@Preview(showBackground = true)
@Composable fun HomeScreenPreview() {
    HomeScreen()

}