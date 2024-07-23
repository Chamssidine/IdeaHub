package com.solodev.ideahub.ui.screen.communityScreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import coil.compose.AsyncImagePainter
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.solodev.ideahub.R
import com.solodev.ideahub.ui.screen.CustomSearchBar
import com.solodev.ideahub.ui.screen.ThreadScreen.ThreadItem
import com.solodev.ideahub.ui.screen.components.CommunityTabItem
import com.solodev.ideahub.ui.screen.components.communityTabItems
import com.solodev.ideahub.ui.screen.components.groupItemData
import com.solodev.ideahub.ui.screen.components.threadItems
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
                LazyRow(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)))
                {
                    items(groupItemData.size) { index ->
                        CommunityGroupItem(
                            modifier = modifier,
                            imageUrl = groupItemData[index].groupeImage,
                            onClick = {},
                            groupName = groupItemData[index].groupName

                        )
                        Log.d("TAG", "CommunityScreen: ${groupItemData[index].groupeImage}")
                        Spacer(modifier = modifier.padding(dimensionResource(id = R.dimen.spacing_small)))
                    }
                }

            }

            item { 
                threadItems.forEachIndexed { index, threadItem ->  
                    ThreadItem(threadItem = threadItem)
                }
            }

       }
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
              placeholder = painterResource(R.drawable.add_circle_24px),
              contentDescription = stringResource(R.string.description),
              error = painterResource(R.drawable.baseline_visibility_off_24),
              contentScale = ContentScale.Crop,
              modifier = Modifier.fillMaxSize()
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