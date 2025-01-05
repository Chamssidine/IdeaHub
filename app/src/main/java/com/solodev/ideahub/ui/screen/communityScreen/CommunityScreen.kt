package com.solodev.ideahub.ui.screen.communityScreen

import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.solodev.ideahub.R
import com.solodev.ideahub.model.communityCategories
import com.solodev.ideahub.model.communityTabItems
import com.solodev.ideahub.model.groupItemData
import com.solodev.ideahub.model.threadItems
import com.solodev.ideahub.ui.screen.CustomSearchBar
import com.solodev.ideahub.ui.screen.goalScreen.ActiveDiscussionSection
import com.solodev.ideahub.ui.screen.threadScreen.ThreadItemUI

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
           modifier = modifier.fillMaxSize(),
           verticalArrangement = Arrangement.Center,
           horizontalAlignment = Alignment.CenterHorizontally
       ) {
            item {
                Box(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))){
                    Text(
                        text = stringResource(id = R.string.home_idea_hub),
                        modifier = modifier.fillMaxWidth()
                    )
                }
            }

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
            item {
                MainCommunityTabScreen(tabTitle = "",selectedTabIndex = selectedItem)
            }
       }
    }
}
@Composable
fun MainCommunityTabScreen(
    modifier: Modifier = Modifier,
    selectedTabIndex: Int = 0,
    tabTitle: String
) {
    Log.d("MainTabScreen", "selectedTabIndex: $selectedTabIndex, tabTitle: $tabTitle")
    when (selectedTabIndex) {
        0 -> {
            Column(modifier = modifier.fillMaxWidth()) {
                Text(text = tabTitle, style = MaterialTheme.typography.displayMedium)
                MainGroupScreen()
            }
        }
        1 -> {
            Box(modifier = modifier.padding(16.dp)) {
                ExploreTabSection()
            }
        }
        2 -> {
            Box(modifier = modifier.padding(16.dp)) {
                ActiveDiscussionSection()
            }
        }
    }
}
@Composable
fun MainGroupScreen(
    modifier: Modifier = Modifier,
){
    Column(modifier = modifier) {

        LazyRow(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)))
            {
                items(groupItemData.size) { index ->
                    CommunityGroupItem(
                        modifier = modifier,
                        imageUrl = groupItemData[index].groupImage,
                        onClick = {},
                        groupName = groupItemData[index].groupName

                    )
                    Log.d("TAG", "CommunityScreen: ${groupItemData[index].groupImage}")
                    Spacer(modifier = modifier.padding(dimensionResource(id = R.dimen.spacing_small)))
                }
            }

        }
    threadItems.forEachIndexed { index, threadItem ->
                ThreadItemUI(threadItem = threadItem)
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityGroupItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    imageUrl: String = "",
    groupName: String = "Group Name",
) {
    ElevatedCard(
        onClick = onClick,
        modifier = modifier.size(100.dp),
        shape = MaterialTheme.shapes.medium
        ) {

      Box(
          contentAlignment = Alignment.BottomCenter,
          modifier = modifier.fillMaxSize()
      ) {
          AsyncImage(
              model = ImageRequest.Builder(LocalContext.current)
                  .data(imageUrl)
                  .crossfade(true)
                  .build(),
              placeholder = painterResource(R.drawable.loading_img),
              contentDescription = stringResource(R.string.description),
              error = painterResource(R.drawable.ic_broken_image),
              contentScale = ContentScale.Crop,
              modifier = Modifier
                  .fillMaxSize()
                  .clip(CircleShape)
          )
          Text(
              groupName,
              maxLines = 1,
              overflow = TextOverflow.Ellipsis,
              modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)),
              style = MaterialTheme.typography.bodyMedium
          )
      }

    }
}

@Composable
fun ExploreTabSection(
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(
            "Community Categories",
            style = MaterialTheme.typography.displayMedium,
            modifier = modifier.padding(16.dp) // Utiliser une valeur codée en dur pour simplifier
        )
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(500.dp) // Définir une hauteur fixe ou utiliser une autre contrainte appropriée
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 128.dp),
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(communityCategories.size) { item ->
                    CategoryItem(
                        categoryName = communityCategories[item].categoryName,
                        onClick = {},
                        categoryImage = communityCategories[item].categoryImage
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    categoryName: String,
    onClick: () -> Unit = {},
    categoryImage: String?,
){
    ElevatedCard(
        onClick = onClick
    ) {
        Box(modifier = modifier)
        {
            ElevatedCard(
                modifier = modifier.fillMaxWidth()
            ) {
                AsyncImage(model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(categoryImage)
                    .crossfade(true)
                    .build(),
                    placeholder = painterResource(R.drawable.loading_img),
                    contentDescription = stringResource(R.string.description),
                    error = painterResource(R.drawable.ic_broken_image),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
                Text(
                    categoryName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)),
                    style = MaterialTheme.typography.bodyLarge
                )

            }
        }
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