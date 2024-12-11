package com.solodev.ideahub.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.solodev.ideahub.R
import com.solodev.ideahub.model.GoalScreenTabItem
import com.solodev.ideahub.ui.screen.goalScreen.GoalScreenTab


@Composable fun HomeScreen(
 tabOption: List<GoalScreenTabItem> =  listOf(),
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
            TabSection(goalTabItems = tabOption)
        }

    }

}

@Composable
fun TabSection(
    goalTabItems: List<GoalScreenTabItem>
) {
    val goalTabItems = goalTabItems.filter { it.showItem }
    var state by remember { mutableStateOf(0) }
    var content by remember { mutableStateOf( goalTabItems[0]) }
    var tabtitle by remember { mutableStateOf(goalTabItems[0].title)  }

    Column {
        ScrollableTabRow(
            containerColor = Color.Transparent,
            modifier = Modifier.wrapContentWidth(),
            selectedTabIndex = state,
            edgePadding = dimensionResource(id = R.dimen.padding_medium)

        ) {
            goalTabItems.forEachIndexed { index, item ->

                GoalScreenTab(
                    tabTitle = stringResource(id = item.title),
                    onSelected = { state = index ; tabtitle = item.title; content = item },
                    selected = index == state
                )

            }
        }
        MainTabScreen(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
            tabTitle = stringResource(id = tabtitle),
            selectedTabIndex = state,
            selectedContent = {content.screenContent()}
        )
    }
}

@Composable
fun MainTabScreen(
    modifier: Modifier = Modifier,
    selectedTabIndex: Int = 0,
    tabTitle: String,
    selectedContent: @Composable () -> Unit = {},
    ) {

    var showDialog by remember { mutableStateOf(false) }
    Column(
        modifier = modifier.fillMaxWidth()
    ) {

        //render the content based on the selected tab
        selectedContent()
    }


}


@Preview(showBackground = true)
@Composable fun HomeScreenPreview() {
    HomeScreen()

}