package com.solodev.ideahub.ui.screen.goalScreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Button
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
import com.solodev.ideahub.R
import com.solodev.ideahub.ui.screen.CustomSearchBar

@Composable
fun GoalScreen(
  modifier: Modifier = Modifier,
  selectedIndex: Int = 0
) {
    var customIndex by remember { mutableStateOf(selectedIndex) }
    val items = listOf("Item 1", "Item 2", "Item 3", "Item 4")
    var defaultItemCount by rememberSaveable { mutableStateOf(3) }

    LazyColumn(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        item {
            CustomSearchBar()
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))

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
            Text(
                text = stringResource(id = R.string.your_goal),
                style = MaterialTheme.typography.labelMedium
            )
        }

        items(defaultItemCount) {
            GoalItem(Modifier)
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))
        }

        item {
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
            Spacer(modifier = Modifier.weight(1f))
            if (hasCompleted) {
                Text(text = "Completed",style = MaterialTheme.typography.labelSmall)
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
@Preview(showBackground = true)
@Composable
fun GoalItemPreview() {
    GoalScreen(modifier = Modifier)
}