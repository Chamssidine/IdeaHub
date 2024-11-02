package com.solodev.ideahub.ui.screen.popularGroup

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.solodev.ideahub.R
import com.solodev.ideahub.ui.screen.InputContainer
import com.solodev.ideahub.ui.screen.components.FloatingButton

@Composable
fun PopularGroupScreen(
    viewModel: PopularGroupViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    var showDialog by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // Display each group category
        uiState.communityCategories.forEach { category ->
            GroupSection(
                sectionName = category.categoryName,
                groups = category.groupList,
                onJoinClicked = { groupId -> viewModel.joinGroup(groupId) },
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
        }

        // Show dialog when required
        if (showDialog) {
            Log.d("PopularGroupScreen", "Showing dialog")
            CreateGroupDialog(
                viewModel = viewModel,
                showDialog = showDialog,
                onDismiss = { showDialog = false },
                onConfirm = {
                    if (viewModel.checkInputs()) {
                        Log.d("PopularGroupScreen", "On confirm in create group dialog called!")
                        viewModel.createGroup()
                        showDialog = false
                    }
                },
            )
        }
    }

    // Floating action button positioned at the bottom end
    Box(modifier = Modifier
        .fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
        FloatingButton(
            onClick = { showDialog = true }
        )
    }
}

@Composable
fun GroupSection(
    sectionName: String,
    groups: List<GroupItemUiState>,
    onJoinClicked: (String) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = sectionName,
            style = MaterialTheme.typography.displayMedium
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
        Box(modifier = Modifier.fillMaxWidth()) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(), // Ensure LazyRow has full width
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing_medium)),
            ) {
                items(groups.size) { index ->
                    val group = groups[index]
                    GroupItem(
                        groupName = group.groupName,
                        description = group.groupDescription,
                        isJoined = group.isJoined,
                        onJoinClicked = { onJoinClicked(group.groupId) },
                    )
                }
            }
        }


    }
}

@Composable
fun GroupItem(
    groupName: String,
    description: String,
    isJoined: Boolean,
    onJoinClicked: () -> Unit,
) {
    ElevatedCard(
        modifier = Modifier
            .height(250.dp)
            .width(180.dp),
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                groupName,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier
                .weight(1f)
                .height(dimensionResource(id = R.dimen.padding_small)))
            Text(
                description, modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))


          Box(
              modifier = Modifier.align(Alignment.End),

              contentAlignment = Alignment.BottomEnd) {
              ElevatedButton(
                  onClick = onJoinClicked,
                  modifier = Modifier.fillMaxWidth()
              ) {
                  Text(
                      text = if (isJoined) stringResource(R.string.joined) else stringResource(R.string.join),
                      style = MaterialTheme.typography.labelMedium
                  )
              }
              Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
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
    val communitiesUiState by viewModel.uiState.collectAsState()
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
                        text =  if(communitiesUiState.selectedCategory != null)
                                    "Category:${communitiesUiState.selectedCategory!!.categoryName}"
                                else
                                    stringResource(id = R.string.community_group_select)
                        ,
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
                            expanded = expanded,
                            communities = communitiesUiState.communityCategories,
                            onItemSelected = {
                                expanded = false
                            },
                            viewModel = viewModel,
                            onDismiss = {expanded = false}
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
                        Button(onClick = {
                            onConfirm()
                           // Log.d("PopularGroupScreen", "On confirm in create group dialog called!")
                        }) {
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
    expanded: Boolean = false,
    onItemSelected: ()-> Unit,
    onDismiss: () -> Unit,
    communities: List<CommunityCategoryUiState> = emptyList(),
    viewModel: PopularGroupViewModel
){

    var showCommunityCreateDialog by remember { mutableStateOf(false) }
    if(communities.isNotEmpty()) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {onDismiss}
        ){
            communities.forEach {
                    community -> CommunityGroupItem (
                            communityName = community.categoryName,
                            onItemSelected = {
                                viewModel.updateSelectedCategoryOnGroupCreation(community)
                                onItemSelected()
                            }
                    )
            }
            Divider()
            DropdownMenuItem(
                text = { Text(stringResource(id = R.string.create_community)) },
                onClick = { showCommunityCreateDialog  = true },
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
            viewModel = viewModel,
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
    communityName: String = "",
    onItemSelected: ()-> Unit
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

