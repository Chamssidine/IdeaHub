package com.solodev.ideahub.ui.screen.goalScreen


import android.util.Log

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
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
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
            Log.d("GoalScreen", "$customIndex")
            MainTabScreen(
                selectedTabIndex = customIndex,
                tabTitle = "MyGoal"
            )
        }

    }
}

@Composable
fun MainTabScreen(
    modifier: Modifier = Modifier,
    selectedTabIndex: Int = 0,
    tabTitle: String
) {
    Log.d("MainTabScreen", "selectedTabIndex: $selectedTabIndex, tabTitle: $tabTitle")
    when (selectedTabIndex) {
        0 -> {
            Column(
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = tabTitle,
                    style = MaterialTheme.typography.displayMedium
                )
                AchievedGoal()
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))
                UnAchievedGoal()
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
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = spring(
                    stiffness = Spring.StiffnessMediumLow
                )
            )
    ) {
        Text(text = stringResource(id = R.string.unachieved))
        Column(modifier = Modifier.fillMaxWidth()) {
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
            modifier = modifier
                .padding(dimensionResource(id = R.dimen.padding_small)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = modifier) {
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
            if (hasCompleted) {
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "Completed",style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.weight(1f))
            }
            else {
                Spacer(modifier = Modifier.weight(1f))
                Column(modifier = modifier) {
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
           FloatingActionButton(onClick = onAddButtonCLicked) {
               IconButton(onClick = onAddButtonCLicked) {
                   Icon(Icons.Filled.Add,
                       contentDescription = "icon_add_goal",
                       tint = MaterialTheme.colorScheme.primary
                   )
               }
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
    sectionName: String = "Group",
    modifier: Modifier = Modifier,
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
    ActiveDiscussionItem(modifier = Modifier.padding(16.dp))
}