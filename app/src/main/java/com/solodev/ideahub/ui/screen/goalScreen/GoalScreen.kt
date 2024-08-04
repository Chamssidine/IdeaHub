package com.solodev.ideahub.ui.screen.goalScreen


import android.util.Log
import androidx.compose.animation.AnimatedVisibility

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image

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
import androidx.compose.foundation.layout.width

import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton

import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.solodev.ideahub.R
import com.solodev.ideahub.ui.screen.CustomSearchBar
import com.solodev.ideahub.ui.screen.components.GoalCreationDialog
import com.solodev.ideahub.ui.screen.components.MenuSample
import kotlinx.coroutines.delay

@Composable
fun GoalScreen(
    modifier: Modifier = Modifier,
    selectedIndex: Int = 0,
    goalScreenViewModel: GoalScreenViewModel
) {
    var customIndex by remember { mutableStateOf(selectedIndex) }
    val items = listOf("Item 1", "Item 2", "Item 3", "Item 4")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        item {
            Box(modifier = modifier.padding(dimensionResource(id = R.dimen.spacing_medium))
            ) {
                CustomSearchBar()
            }
            Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.padding_small )))
        }

        item {
            Box(modifier = modifier.padding(start = dimensionResource(id = R.dimen.padding_medium),end = dimensionResource(id = R.dimen.padding_medium))) {
                Text(
                    text = stringResource(id = R.string.congratulations_message),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
        }

        item {
            LazyRow(
                modifier = modifier
                    .padding(
                        start = dimensionResource(id = R.dimen.padding_medium),
                        end = dimensionResource(id = R.dimen.padding_medium)
                    )
                    .fillMaxWidth(),
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
            Log.d("GoalScreen", "$customIndex")
            MainTabScreen(
                modifier = modifier.padding(start = dimensionResource(id = R.dimen.padding_medium),end = dimensionResource(id = R.dimen.padding_medium)),
                selectedTabIndex = customIndex,
                tabTitle = "MyGoal",
                goalScreenViewModel = goalScreenViewModel
            )
        }


    }
}

@Composable
fun MainTabScreen(
    modifier: Modifier = Modifier,
    selectedTabIndex: Int = 0,
    tabTitle: String,
    goalScreenViewModel: GoalScreenViewModel,

) {
    val goalScreenUIState by goalScreenViewModel.uiState.collectAsState()
    Log.d("MainTabScreen", "selectedTabIndex: $selectedTabIndex, tabTitle: $tabTitle")
    var showDialog by remember { mutableStateOf(false) }

    when (selectedTabIndex) {
        0 -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
            ) {
                Text(
                    text = tabTitle,
                    style = MaterialTheme.typography.displayMedium
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))
                UnAchievedGoal(
                    goalScreenViewModel =  goalScreenViewModel
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))

                AchievedGoal(
                    goalScreenViewModel =  goalScreenViewModel
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.BottomEnd
                )
                {
                    FloatingButton(onClick = {
                        showDialog = true
                    })
                }
                if (showDialog) {
                    val goal : Goal? = null
                    goalScreenViewModel.clearUiState()
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
                            }

                        },

                        onDismissButtonClicked = {
                            Log.d("GoalCreationScreen", "Dialog dismissed")
                            showDialog = false
                        },
                        goalScreenViewModel = goalScreenViewModel,
                    )
                }
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))
            }
        }
        1 -> {
            Box(modifier = modifier.wrapContentSize())
            {
                DayPlan()
            }
        }
        2 -> {
            Box(modifier = modifier.wrapContentSize())
            {
                PopularGroupSection()
            }
        }
        3 -> {
            Box(modifier = modifier.wrapContentSize())
            {
                ActiveDiscussionSection()
            }
        }

    }
}

@Composable
fun AchievedGoal(
    modifier: Modifier = Modifier,
    goalScreenViewModel: GoalScreenViewModel,
) {

    val goalScreenUIState by goalScreenViewModel.uiState.collectAsState()
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
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))
        Log.d("AchievedGoal", "GoalList size: ${goalScreenUIState.achievedGoalList.size}")
        Log.d("AchievedGoal", "GoalList: ${goalScreenUIState.achievedGoalList}")

        Column {
            goalScreenUIState.achievedGoalList.forEachIndexed { index, goal ->
                Log.d("AchievedGoal", "Goal: ${goal.title}")
                Log.d("AchievedGoal", "Goal: ${goal.creationDate}")
                GoalItem(
                    modifier,
                    isCompleted = true,
                    title = goal.title,
                    creationDate = goal.creationDate,
                    deadLine = goal.deadline,
                    priority = goal.reminderFrequency,
                    description = goal.description,
                    onDelete = {
                        goalScreenViewModel.deleteGoal(goal)
                    },
                    
                    iAchievedSection = true
                )
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
    modifier: Modifier = Modifier,
    goalScreenViewModel: GoalScreenViewModel,
) {
    val goalScreenUIState by goalScreenViewModel.uiState.collectAsState()
    var defaultItemCount by rememberSaveable { mutableStateOf(minOf(3, goalScreenUIState.unAchievedGoalList.size)) }
    var openDialog by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = spring(
                    stiffness = Spring.StiffnessMediumLow
                )
            )
    ) {
        Text(text = stringResource(id = R.string.unachieved))
        Column {
            val itemsToShow = goalScreenUIState.unAchievedGoalList.take(defaultItemCount)

            itemsToShow.forEach { goal ->
                GoalItem(
                    modifier,
                    title = goal.title,
                    creationDate = goal.creationDate,
                    deadLine = goal.deadline,
                    priority = goal.reminderFrequency,
                    description = goal.description,
                    isCompleted = goal.isCompleted,
                    onDelete = {
                        goalScreenViewModel.deleteGoalFromUnAchievedList(goal)
                    },
                    onMarkAsCompleted = {
                        goalScreenViewModel.markGoalAsCompleted(goal)
                    },
                    onEdit = {
                        Log.d("GoalScreen", "Open button clicked")
                        goalScreenViewModel.onEditGoal(goal)
                        openDialog = true
                    }
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))
            }
        }

        if (openDialog) {
            GoalCreationDialog(
                showDialog = openDialog,
                onConfirm = {
                    Log.d("GoalCreationScreen", "Dialog onConfirm called")
                    goalScreenViewModel.updateGoalInList()
                    if (!goalScreenViewModel.goalCreationUiState.value.hasError) {
                        openDialog = false
                    }
                },
                onDismissButtonClicked = {
                    Log.d("GoalCreationScreen", "Dialog dismissed")
                    openDialog = false
                },
                goalScreenViewModel = goalScreenViewModel,
            )
        }

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomEnd
        ) {
            TextButton(
                onClick = {
                    if (defaultItemCount == 3) {
                        defaultItemCount = goalScreenUIState.unAchievedGoalList.size
                    } else {
                        defaultItemCount = minOf(3, goalScreenUIState.unAchievedGoalList.size)
                    }
                }
            ) {
                Text(
                    text = if (defaultItemCount == 3) {
                        stringResource(id = R.string.see_all)
                    } else {
                        stringResource(id = R.string.see_less)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalItem(
    modifier: Modifier,
    title: String = "My Goaldnbsjdbsj",
    creationDate: String = "01/01/2023",
    isCompleted: Boolean = false,
    onMarkAsCompleted: () -> Unit = {},
    onDelete: () -> Unit = {},
    onEdit: () -> Unit = {},
    deadLine: String = "19-05-2024",
    priority: String = "High",
    delete: Boolean = false,
    description: String = "Description",
    iAchievedSection: Boolean = false,
){
    var expanded by remember { mutableStateOf(false) }
    var visible by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = Unit, block = {
        delay(600L)
        visible = true
    })
    if(!delete)
    {
        ElevatedCard(
            onClick = {expanded =! expanded},
            modifier = modifier
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                ),
            shape = MaterialTheme.shapes.medium,
        )  {
            Row(
                modifier = modifier
                    .padding(dimensionResource(id = R.dimen.padding_small)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = modifier,
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center

                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
                    if(expanded)
                    {
                        AnimatedVisibility(
                            visible = visible,
                            enter = fadeIn(initialAlpha = 0.0f) + slideInVertically(
                            tween(
                                durationMillis = 600,
                                delayMillis = 600,
                                easing = EaseIn
                            ),
                            initialOffsetY = { it * 6 }
                        ) ){
                            Text(
                                text = description,
                                style = MaterialTheme.typography.bodySmall,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
                    }
                   
                    Text(
                        text = creationDate,
                        style = MaterialTheme
                            .typography.labelSmall,
                    )
                }
                if (isCompleted) {
                    Spacer(modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.spacing_medium)))
                    Text(text = stringResource(id = R.string.completed),style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.weight(1f))
                }
                else {
                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.spacing_medium)))
                    Column(
                        modifier = modifier,
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
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
                }
                
                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.spacing_medium)))
                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.spacing_medium)))
                Column(modifier = modifier) {
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
                MenuSample(
                    modifier = modifier.fillMaxSize(),
                    onDeleteClicked = onDelete,
                    onMarkAsCompletedClicked = onMarkAsCompleted,
                    onEditClicked = onEdit,
                    isCompleted = iAchievedSection
                )
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
fun FloatingButton(
    onClick: () -> Unit
) {
    FloatingActionButton(onClick = onClick) {
        IconButton(onClick = onClick) {
            Icon(Icons.Filled.Add,
                contentDescription = "icon_add_goal",
            )
        }
    }
}

@Composable
fun DayPlan(
    modifier: Modifier = Modifier,
    onAddButtonCLicked: ()-> Unit = {}
) {
    val defaultIcount by remember{mutableStateOf(3)}
    Column(
        modifier = modifier.wrapContentWidth()
    ) {
        repeat(3) {
            DayPlanItem()
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))
        }
       Box(
           modifier = Modifier.fillMaxWidth(),
           contentAlignment = Alignment.BottomEnd
       )
       {
           FloatingButton(onClick = onAddButtonCLicked)
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
                    style = MaterialTheme.typography.bodyMedium
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
                    Icon(
                        Icons.Filled.Done,
                        contentDescription = "icon_done",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            }

        }
}

@Composable
fun PopularGroupSection(
    modifier: Modifier = Modifier,
    title: String = "Science",
){
    Column {
        repeat(3)
        {
            GroupSection(sectionName = title)
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
        }
    }
}

@Composable
fun GroupSection(
    modifier: Modifier = Modifier,
    sectionName: String = "Group",
    groupCont: Int = 3,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    )
    {
        Text(text = sectionName,
            style = MaterialTheme.typography.displayMedium
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
        LazyRow(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween)
        {
            items(groupCont)
            {
                GroupItem()
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
            }
        }
    }

}

@Composable
fun GroupItem(
    modifier: Modifier = Modifier,
    groupName: String = "Group Name",
    description: String = "Description",
    onlikeClicked: () -> Unit = {},
    onJoinClicked:() -> Unit = {},
    onAddToFavoryClicked:() -> Unit = {},
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        ) {
            Text(
                groupName,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
            Text(description)
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
            Row(

            ){
                ElevatedCard(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_small)))
                {
                    IconButton(onClick = onlikeClicked) {
                        Icon(painter = painterResource(
                            id = R.drawable.thumb_up_24px),
                            contentDescription = "icon_like",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                ElevatedButton(
                    onClick = {},
                    modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_small)))
                {
                   Text(
                       text = stringResource(R.string.join),
                       style = MaterialTheme.typography.labelMedium
                   )
                }
                ElevatedCard(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_small)))
                {
                    IconButton(onClick = onlikeClicked) {
                        Icon(
                            Icons.Filled.FavoriteBorder,
                            contentDescription = "icon_favorite",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ActiveDiscussionSection(

) {
    Column(modifier = Modifier.fillMaxWidth()) {
        ElevatedCard(){
               repeat(5)
               {
                   ActiveDiscussionItem()
                   Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
               }
        }
    }
}

@Composable
fun ActiveDiscussionItem(
    modifier: Modifier = Modifier,
    title: String = "Discussion Title",
    description: String = "Discussion Description",
    onOpen: () -> Unit = {},
    isActive: Boolean = true

){
  Row(
      modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Center,
      ){
    Box()
    {
        ElevatedCard {
            Image(
                painter = painterResource(id = R.drawable.account_circle_24px_filled),
                contentDescription = "imageProfile",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(60.dp),
            )
        }
        if(isActive)
        {
            Box {
                Icon(
                    Icons.Filled.CheckCircle,
                    contentDescription = "icon_check",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp),

                )
            }
        }

    }
      Spacer(modifier = Modifier.weight(1f))
      Column() {
          Text(text = title,style = MaterialTheme.typography.bodyMedium)
          Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
          Text(text = description,style = MaterialTheme.typography.bodySmall)
      }
      Spacer(modifier = Modifier.weight(1f))
      ElevatedButton(onClick = onOpen) {
          Text(text = stringResource(id = R.string.open_button))
      }

  }
}


@Preview(showBackground = true)
@Composable
fun GoalItemPreview() {
    Box(modifier = Modifier.height(60.dp))
    {
        GoalItem(modifier = Modifier)
    }

}