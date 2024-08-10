package com.solodev.ideahub.ui.screen.popularGroupScreen

import CommunityCategoryUiState
import GroupItemUiState
import PopularGroupViewModel
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.solodev.ideahub.R
import com.solodev.ideahub.ui.screen.InputContainer
import com.solodev.ideahub.ui.screen.components.FloatingButton

@Composable
fun PopularGroupScreen(
    viewModel: PopularGroupViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(dimensionResource(id = R.dimen.padding_medium))) {
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
        if(showDialog) {
            Log.d("PopularGroupScreen", "Showing dialog")
            CreateGroupDialog(
                viewModel = viewModel,
                showDialog = showDialog,
                onDismiss = { showDialog = false },
                onConfirm = {
                    if(viewModel.checkInputs()) {
                        viewModel.createGroup()
                        showDialog = false
                    }
                },
                )
        }
        FloatingButton(onClick = { showDialog = true })
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


@Composable
fun CreateGroupDialog(
    viewModel: PopularGroupViewModel,
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
){
    val uiState by viewModel.groupItemUIState.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    if(showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            ElevatedCard {
                Column(
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))

                )  {
                    Text(
                        text = stringResource(id = R.string.create_group)
                    , style = MaterialTheme.typography.headlineMedium
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
                    InputContainer(
                        inputValue = uiState.groupName,
                        labelValue = stringResource(id = R.string.group_name),
                        onInputValueChange = {viewModel.updateGroupName(it)}
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
                    InputContainer(
                        inputValue = uiState.groupDescription,
                        labelValue = stringResource(id = R.string.group_description),
                        maxLines = 5,
                        onInputValueChange = {viewModel.updateDescription(it)}
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
                    if(uiState.hasError) {
                        Text(
                            text = uiState.errorMessage ?: "",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))

                    Text(
                        text = stringResource(id = R.string.community_group_select),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    IconButton(onClick = {
                        expanded = !expanded
                    }) {
                        Icon(
                            if(!expanded)Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                            contentDescription = "ico_select_community",

                        )
                    }
                    if(expanded) {
                        CommunityGroupDropDown(
                            communities = viewModel.getCommunityList(),
                            onItemSelected = { expanded = false },
                            viewModel = viewModel
                        )
                    }
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = onDismiss) {
                            Text(text = stringResource(id = R.string.cancel))
                        }
                        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
                        Button(onClick = onConfirm) {
                            Text(text = stringResource(id = R.string.create))
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun CommunityGroupDropDown(
    modifier: Modifier = Modifier,
    onItemSelected: ()-> Unit = {},
    communities: List<CommunityCategoryUiState> = emptyList(),
    viewModel: PopularGroupViewModel
){
    var expanded by remember { mutableStateOf(false) }
    var showCommunityCreateDialog by remember { mutableStateOf(false) }
    if(communities.isNotEmpty()) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ){
            communities.forEach {
                    community -> CommunityGroupItem (
                            communityName = community.categoryName,
                            onItemSelected = onItemSelected
                    )
            }
            DropdownMenuItem(
                text = { Text(stringResource(id = R.string.create_community)) },
                onClick = { showCommunityCreateDialog != showCommunityCreateDialog },
                leadingIcon = { Icon(Icons.Outlined.Edit, contentDescription = null) }
            )
        }


    }
    else {

        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                text = stringResource(id = R.string.no_communities),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
            TextButton(onClick = {showCommunityCreateDialog =  true}) {
                Text(text = stringResource(id = R.string.community_create_a_new_one))
            }
        }
    }
    if(showCommunityCreateDialog) {
        CommunityCategoryCreateDialog (
            viewModel = PopularGroupViewModel(),
            showDialog = showCommunityCreateDialog,
            onDismiss = { showCommunityCreateDialog = false },
            onConfirm = {
                viewModel.createCategory()
                showCommunityCreateDialog = false

            },
        )
    }

}


@Composable
fun CommunityGroupItem(
    modifier: Modifier = Modifier,
    communityName: String = "",
    onItemSelected: ()-> Unit = {}
) {
    DropdownMenuItem(
        text = { Text(communityName, style = MaterialTheme.typography.labelMedium) },
        onClick = onItemSelected,
        leadingIcon = { Icon(Icons.Outlined.Edit, contentDescription = null) }
    )
}

@Composable
fun CommunityCategoryCreateDialog(
    viewModel: PopularGroupViewModel,
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    if(showDialog) {

        val uiState by viewModel.categoryUIState.collectAsState()
        Dialog(onDismissRequest = onDismiss) {
            ElevatedCard {
                Column(
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))

                )  {
                    Text(
                        text = stringResource(id = R.string.create_community)
                        , style = MaterialTheme.typography.headlineMedium
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
                    InputContainer(
                        inputValue = uiState.categoryName,
                        labelValue = stringResource(id = R.string.community_name),
                        onInputValueChange = {viewModel.updateCategoryNameOnCreate(it)}
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))

                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = onDismiss) {
                            Text(text = stringResource(id = R.string.cancel))
                        }
                        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
                        Button(onClick = onConfirm) {
                            Text(text = stringResource(id = R.string.create))
                        }

                    }
                }
            }
        }
    }
}