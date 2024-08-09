package com.solodev.ideahub.ui.screen.popularGroupScreen

import GroupItemUiState
import PopularGroupViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.solodev.ideahub.R

@Composable
fun PopularGroupScreen(
    viewModel: PopularGroupViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(dimensionResource(id = R.dimen.padding_medium))) {
        uiState.communityCategories.forEach { category ->
            GroupSection(
                sectionName = category.categoryName,
                groups = category.groupList,
                onJoinClicked = { groupId -> viewModel.joinGroup(groupId) },
                onLikeClicked = { groupId -> viewModel.likeGroup(groupId) },
                onAddToFavoriteClicked = { groupId -> viewModel.addToFavorites(groupId) }
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
        }
    }
}

@Composable
fun GroupSection(
    sectionName: String,
    groups: List<GroupItemUiState>,
    onJoinClicked: (String) -> Unit,
    onLikeClicked: (String) -> Unit,
    onAddToFavoriteClicked: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = sectionName,
            style = MaterialTheme.typography.displayMedium
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            items(groups.size) { index ->
                val group = groups[index]
                GroupItem(
                    groupName = group.groupName,
                    description = group.groupDescription,
                    isLiked = group.isLiked,
                    isJoined = group.isJoined,
                    isFavorite = group.isFavorite,
                    onJoinClicked = { onJoinClicked(group.groupId) },
                    onLikeClicked = { onLikeClicked(group.groupId) },
                    onAddToFavoriteClicked = { onAddToFavoriteClicked(group.groupId) }
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
            }
        }
    }
}

@Composable
fun GroupItem(
    groupName: String,
    description: String,
    isLiked: Boolean,
    isJoined: Boolean,
    isFavorite: Boolean,
    onJoinClicked: () -> Unit,
    onLikeClicked: () -> Unit,
    onAddToFavoriteClicked: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))) {
            Text(
                groupName,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
            Text(description)
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
            Row {
                IconButton(
                    onClick = onLikeClicked,
                    modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_small))
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (isLiked) R.drawable.thumb_up_24px_filled else R.drawable.thumb_up_24px_outlined
                        ),
                        contentDescription = "icon_like",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
                ElevatedButton(
                    onClick = onJoinClicked,
                    modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_small))
                ) {
                    Text(
                        text = if (isJoined) stringResource(R.string.joined) else stringResource(R.string.join),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
                IconButton(
                    onClick = onAddToFavoriteClicked,
                    modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_small))
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (isFavorite) R.drawable.favorite_24px_filled else R.drawable.favorite_24px_outlined
                        ),
                        contentDescription = "icon_favorite",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}
