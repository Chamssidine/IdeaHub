package com.solodev.ideahub.ui.screen.goalScreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.solodev.ideahub.R
import com.solodev.ideahub.ui.screen.CustomSearchBar

@Composable
fun GoalScreen(
    modifier: Modifier = Modifier,
    selectedIndex: Int = 0
) {
    var customIndex by remember { mutableStateOf(selectedIndex) }
    val items = listOf("Item 1", "Item 2", "Item 3", "Item 4")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        item {
            CustomSearchBar()
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
        }

        item {
            Text(
                text = stringResource(id = R.string.congratulations_message),
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
        }

        item {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                itemsIndexed(items) { index, item ->
                    GoalScreenTab(
                        modifier = Modifier,
                        tabTitle = if (index == customIndex) "Selected Tab" else item,
                        onSelected = { customIndex = index },
                        selected = index == customIndex
                    )
                }
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))
        }

        item {
            MainTabScreen(
                selecTedTabIndex = selectedIndex,
                tabTitle = "MyGoal"
            )
        }

    }
}

@Composable
fun MainTabScreen(
    modifier: Modifier = Modifier,
    selecTedTabIndex: Int = 0,
    tabTitle: String
){
    Column {
        Text(
            text = tabTitle,
            style = MaterialTheme.typography.labelMedium
        )
        AchievedGoal()
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))
        UnAchievedGoal()
    }
}

@Composable
fun AchievedGoal(
    modifier: Modifier = Modifier
) {
    var defaultItemCount by rememberSaveable { mutableStateOf(3) }
    Column(
        modifier = modifier
            .wrapContentHeight()
            .animateContentSize(
                animationSpec = spring(
                    stiffness = Spring.StiffnessMediumLow
                )
            )
    ) {
        Text(text = stringResource(id = R.string.achieved))
        Column {
            repeat(defaultItemCount) {
                GoalItem(modifier, hasCompleted = true)
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))
            }
        }
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomEnd
        ) {
            TextButton(onClick = { defaultItemCount = if (defaultItemCount == 3) 6 else 3 }) {
                Text(text = if (defaultItemCount == 3) stringResource(id = R.string.see_all) else stringResource(id = R.string.see_less))
            }
        }
    }
}

@Composable
fun UnAchievedGoal(
    modifier: Modifier = Modifier
) {
    var defaultItemCount by rememberSaveable { mutableStateOf(3) }
    Column(
        modifier = modifier
            .wrapContentHeight()
            .animateContentSize(
                animationSpec = spring(
                    stiffness = Spring.StiffnessMediumLow
                )
            )
    ) {
        Text(text = stringResource(id = R.string.unachieved))
        Column {
            repeat(defaultItemCount) {
                GoalItem(modifier, hasCompleted = false)
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))
            }
        }
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomEnd
        ) {
            TextButton(onClick = { defaultItemCount = if (defaultItemCount == 3) 6 else 3 }) {
                Text(text = if (defaultItemCount == 3) stringResource(id = R.string.see_all) else stringResource(id = R.string.see_less))
            }
        }
    }
}

@Composable
fun GoalItem(
    modifier: Modifier,
    title: String = "My Goal",
    creationDate: String = "01/01/2023",
    hasCompleted: Boolean = false,
    onCompleted: () -> Unit = {},
    onDelete: () -> Unit = {},
    onOpen: () -> Unit = {},
    deadLine: String = "19-05-2024",
    priority: String = "High",

){
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
    )  {
        Row(
            modifier = modifier.padding(dimensionResource(id = R.dimen.padding_small)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
                Text(
                    text = creationDate,
                    style = MaterialTheme
                        .typography.labelSmall,
                    modifier = Modifier
                        .align(Alignment.End)
                )
            }
            if (hasCompleted) {
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "Completed",style = MaterialTheme.typography.labelSmall)
            }
            else {
                Spacer(modifier = Modifier.weight(1f))
                Column() {
                    Text(
                        text = stringResource(id = R.string.deadline),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))
                    Text(
                        text = stringResource(id = R.string.priority),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Column() {
                    Text(
                        text = deadLine,
                        style = MaterialTheme.typography.labelSmall
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))
                    Text(
                        text = priority,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
            ElevatedCard(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_small)))
            {
                IconButton(onClick = onOpen) {
                    Icon(painter = painterResource(
                        id = R.drawable.open_in_new_24px),
                        contentDescription = "icon_share",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            ElevatedCard(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_small)))
            {
                IconButton(onClick = onDelete) {
                    Icon(painter = painterResource(
                        id = R.drawable.delete_forever_24px),
                        contentDescription = "icon_share",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun GoalScreenTab(
    modifier: Modifier = Modifier,
    tabTitle: String = "Tab",
    onSelected: () -> Unit = {},
    selected: Boolean = false
) {
    if(selected) {

        Button(onClick = onSelected) {
            Text(text = tabTitle)
        }
    }
    else {
        TextButton(onClick = onSelected) {
            Text(text = tabTitle)
        }
    }

}


@Composable
fun DayPlan(modifier: Modifier = Modifier
) {
    val defaultIcount by remember{mutableStateOf(3)}
    Column(
        modifier = modifier
    ) {
        LazyColumn {
            items(count = defaultIcount){
                DayPlanItem()
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))

            }
        }
    }

}

@Composable
fun DayPlanItem(
    modifier: Modifier = Modifier,
    hasFinished: Boolean = false,
    onChecked: () -> Unit = {},
    deadLine: String = "DeadLine",
    deadLineValue: String = "Today",
    priority: String = "Priority",
    priorityValue: String = "Low",
    title: String = "My Goal",
    creationDate: String ="19-05-2024"
) {

    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
    )  {
        Row(
            modifier = modifier.padding(dimensionResource(id = R.dimen.padding_small)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
                Text(
                    text = creationDate,
                    style = MaterialTheme
                        .typography.labelSmall,
                    modifier = Modifier
                        .align(Alignment.End)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column() {
                    Text(
                        text = stringResource(id = R.string.deadline),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))
                    Text(
                        text = stringResource(id = R.string.priority),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
            Column() {
                Text(
                    text = deadLine,
                    style = MaterialTheme.typography.labelSmall
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))
                Text(
                    text = priority,
                    style = MaterialTheme.typography.labelSmall
                )
            }
            ElevatedCard(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_small)))
            {
                IconButton(onClick = onChecked) {
                    Icon(painter = painterResource(
                        id = R.drawable.open_in_new_24px),
                        contentDescription = "icon_share",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            }

        }
}



@Preview(showBackground = true)
@Composable
fun GoalItemPreview() {
    DayPlan(modifier = Modifier.padding(16.dp))
}