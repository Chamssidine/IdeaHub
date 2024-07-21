package com.solodev.ideahub.ui.screen.communityScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.SubcomposeAsyncImage
import com.solodev.ideahub.R
import com.solodev.ideahub.ui.screen.CustomSearchBar
import com.solodev.ideahub.ui.screen.components.communityTabItems
import com.solodev.ideahub.ui.screen.components.groupItemData
import com.solodev.ideahub.ui.screen.goalScreen.GroupItem

@Composable
fun CommunityScreen(
    modifier: Modifier = Modifier
){
    var selectedItem by rememberSaveable { mutableStateOf(0) }
    Column(
      modifier = modifier.fillMaxSize()
    ) {
       Box(modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))){
           CustomSearchBar()
       }
       LazyColumn(
           modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
           verticalArrangement = Arrangement.Center,
           horizontalAlignment = Alignment.CenterHorizontally
       ) {
            item {
                Text(
                    text = stringResource(id = R.string.home_idea_hub),
                    modifier = modifier.fillMaxWidth()
                )
            }
             
            item { Spacer(modifier = modifier.padding(dimensionResource(id = R.dimen.spacing_small))) }

            item{
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                )
                {
                    communityTabItems.forEachIndexed { index, communityTabItem ->
                        CustomTab(
                            modifier = modifier,
                            onTabSelected = {
                                selectedItem = index
                            },
                            selected = selectedItem == index,
                            tabTitle = communityTabItem.title
                        )

                    }
                }
            }

            item{ Spacer(modifier = modifier.padding(dimensionResource(id = R.dimen.spacing_small)))}

            item{
                Row(modifier =Modifier.fillMaxWidth())
                {
                    groupItemData.forEachIndexed { index, groupItem ->
                        GroupItem(
                            modifier = modifier,
                            imageUrl = groupItemData[index].groupeImage,
                            onClick = {},
                            groupName = groupItemData[index].groupName

                        )
                    }
                }
            }

           items(groupItemData.size) { index ->

            }
       }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    imageUrl: String = "",
    groupName: String = "Group Name",
) {
    ElevatedCard(
        onClick = onClick,
        ) {
        SubcomposeAsyncImage(
            model = imageUrl,
            loading = {
                CircularProgressIndicator()
            },
            contentDescription = stringResource(R.string.description)
        )
        Text(groupName)
    }
}

@Composable
fun CustomTab(
    modifier: Modifier = Modifier,
    onTabSelected: () -> Unit = {},
    selected: Boolean = false,
    tabTitle: String = "tab1"
) {
    if(selected) {

        Button(onClick = onTabSelected) {
            Text(text = tabTitle)
        }
    }
    else {
        TextButton(onClick = onTabSelected) {
            Text(text = tabTitle)
        }
    }

}
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CommunityScreenPreview(){
    CommunityScreen()
}