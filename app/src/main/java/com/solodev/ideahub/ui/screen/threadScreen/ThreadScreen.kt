package com.solodev.ideahub.ui.screen.threadScreen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.solodev.ideahub.model.ThreadItem
import com.solodev.ideahub.model.threadItems
import com.solodev.ideahub.ui.screen.CustomSearchBar
import com.solodev.ideahub.ui.screen.components.ThreadContent
import com.solodev.ideahub.ui.screen.components.UserProfileUI


import com.solodev.ideahub.ui.screen.userThreadScreen.UserThreadViewModel


@Composable
fun ThreadScreen(
    showSearchBar: Boolean = true
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if(showSearchBar)
                CustomSearchBar()

            LazyColumn {
                items(threadItems.size) { threadItem ->
                    ThreadItemUI(
                        threadItem = threadItems[threadItem],
                        modifier = Modifier.padding(8.dp),
                        onThreadClick = {}

                    )
                }
            }
        }

    }
}

@Composable
fun GeneralThreadListContent(
    modifier: Modifier = Modifier,
    threadViewModel: UserThreadViewModel = hiltViewModel<UserThreadViewModel>(),
    onThreadClick: () -> Unit = {}
){
    val threadItemUIsState by threadViewModel.threadItemUiState.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = modifier.fillMaxWidth(),
    ) {
        items(threadItemUIsState.threadItems.size) { index ->
            ThreadItemUI(
                threadItem = threadItemUIsState.threadItems[index],
                modifier = Modifier.fillMaxWidth(),
                onThreadClick = onThreadClick,
                userThreadViewModel = threadViewModel
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThreadItemUI(
    modifier: Modifier = Modifier,
    threadItem: ThreadItem,
    onThreadClick: () -> Unit = {},
    userThreadViewModel: UserThreadViewModel = hiltViewModel<UserThreadViewModel>(),
) {


    var expanded by remember { mutableStateOf(false) }
    var lineCount by remember { mutableIntStateOf(3) }
    var defaultTextOverflowValue by remember { mutableStateOf(TextOverflow.Ellipsis) }
    ElevatedCard(
        onClick = {
            expanded = !expanded
            if(expanded)
            {
                defaultTextOverflowValue = TextOverflow.Visible
                lineCount = Int.MAX_VALUE
            } else {
                defaultTextOverflowValue = TextOverflow.Ellipsis
                lineCount = 3
            }


    },
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            UserProfileUI(
                modifier = Modifier,
                name = threadItem.userProfile.name,
                image = threadItem.userProfile.profileImage,
                publicationTime = threadItem.userProfile.publicationTime.toString(),

            )
            Spacer(modifier = Modifier.height(8.dp))
            ThreadContent(
                modifier = Modifier,
                defaultTextOverflowValue = defaultTextOverflowValue,
                onContribute = {
                    userThreadViewModel.selectThread(threadItem)
                    onThreadClick()
                },
                threadItem = threadItem
            )
            Spacer(modifier = Modifier.height(8.dp))

        }
    }
}



@Preview(showBackground = true)
@Composable
fun ThreadScreenPreview()
{
    //ThreadItem(threadItem = threadItems[0])
}