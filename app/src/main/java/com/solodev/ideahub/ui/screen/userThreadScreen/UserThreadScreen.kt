package com.solodev.ideahub.ui.screen.userThreadScreen

import android.os.Build

import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row


import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
 
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
 
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
 
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.solodev.ideahub.R
import com.solodev.ideahub.data.ThreadItemRepositoryImpl
import com.solodev.ideahub.data.ThreadItemRepositoryImpl_Factory
import com.solodev.ideahub.model.Comment
import com.solodev.ideahub.model.ThreadItem
import com.solodev.ideahub.model.UserProfile
import com.solodev.ideahub.ui.screen.components.CommentSectionInput
import com.solodev.ideahub.ui.screen.components.ThreadContent
import com.solodev.ideahub.ui.screen.components.UserProfileUI

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ThreadDetailsScreen(
    modifier: Modifier = Modifier,
    threadViewModel: UserThreadViewModel
) {
    val uiState by threadViewModel.comments.collectAsStateWithLifecycle()
    val threadUIState by threadViewModel.threadItemUiState.collectAsStateWithLifecycle()

    //Log.d("UserThreadScreen", "Thread items size: ${threadUIState.threadItems.size} + $counter")
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(modifier = modifier
            .verticalScroll(rememberScrollState())
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        ) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)

            ) {
                ElevatedCard(
                    modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
                    shape = MaterialTheme.shapes.small
                ){
                    UserThreadItem(
                        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
                        threadItem = threadUIState.selectedThread
                    )
                }
            }
            Column(
                modifier = modifier
                    .fillMaxWidth()

            ){
                LazyColumn(
                    modifier = Modifier
                        .height(400.dp)
                        .fillMaxWidth()
                ) {
                    items(threadUIState.selectedThread.comments){ comment ->
                        CommentItem(
                            modifier = modifier
                                .padding(
                                    start = dimensionResource(id = R.dimen.padding_medium),
                                    end = dimensionResource(id = R.dimen.padding_medium),
                                    top = dimensionResource(id = R.dimen.padding_small),
                                    bottom = dimensionResource(id = R.dimen.padding_small)
                                )
                                .animateContentSize(
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioNoBouncy,
                                        stiffness = Spring.StiffnessLow
                                    )
                                ),
                            showImage = false,
                            comment = comment

                        )

                    }
                }
//                threadUIState.selectedThread.comments.forEachIndexed { index, comment ->
//                    CommentItem(
//                        modifier = modifier
//                            .padding(
//                                start = dimensionResource(id = R.dimen.padding_medium),
//                                end = dimensionResource(id = R.dimen.padding_medium),
//                                top = dimensionResource(id = R.dimen.padding_small)
//                            )
//                            .animateContentSize(
//                                animationSpec = spring(
//                                    dampingRatio = Spring.DampingRatioNoBouncy,
//                                    stiffness = Spring.StiffnessLow
//                                )
//                            ),
//                        showImage = false,
//                        comment = comment
//
//                    )
//                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
//                }
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
                 CommentSectionInput(
                        modifier = modifier
                            .fillMaxWidth()
                            .animateContentSize(
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessLow
                                )
                            ),
                        value = uiState.commentText,
                        onValueChange = {
                            threadViewModel.onCommentTyping(it)
                        },
                        onSendClick = {threadViewModel.publishComment(threadUIState.selectedThread, uiState.commentText)}

                    )
                }



        }




    }

}


@Composable
fun UserThreadItem(
    modifier: Modifier = Modifier,
    threadItem: ThreadItem?
) {
    Column (modifier = modifier){
        UserProfileUI(
           name = threadItem?.userProfile?.name,
            image = threadItem?.userProfile?.profileImage,
            publicationTime = threadItem?.userProfile?.publicationTime.toString()
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
        ThreadContent(
            threadItem = threadItem!!,
            showContributeButton = false
            )
    }


}

@Composable
fun CommentItem(
    modifier: Modifier = Modifier,
    showImage: Boolean = true,
    comment: Comment = Comment(),
) {
    var expanded by remember { mutableStateOf(false) }
    var lineCount by remember { mutableIntStateOf(3) }
    var defaultTextOverflowValue by remember { mutableStateOf(TextOverflow.Ellipsis) }

    ElevatedCard(
            modifier = modifier,
            onClick = {
                expanded = !expanded
                if(expanded)
                {
                    defaultTextOverflowValue = TextOverflow.Visible
                    lineCount = Int.MAX_VALUE
                } else {
                    defaultTextOverflowValue = TextOverflow.Ellipsis
                    lineCount = 3
                } },
            shape = MaterialTheme.shapes.medium
        ) {
            Column(

                horizontalAlignment = Alignment.Start,
            ){
                UserProfileUI(
                    modifier = Modifier.wrapContentSize(),
                    name = comment.userProfile.name,
                    image = comment.userProfile.profileImage,
                    publicationTime = comment.commentDate
                )

                Text(
                    modifier = modifier.padding( end = 8.dp),
                    text = comment.commentText,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = lineCount,
                    overflow = defaultTextOverflowValue,
                )
                if(showImage)
                {
                    Image(
                        modifier = modifier.padding(start = 16.dp, end = 16.dp),
                        painter = painterResource(id = R.drawable.twitter__1_),
                        contentDescription ="image",
                        contentScale = ContentScale.Crop,
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(text = "Like")
                    }
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(text = "Respond")
                    }
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(text = "Report")
                    }

                }

            }

        }






}
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showSystemUi = true)
@Composable
fun UserThreadScreenPreview() {
    CommentItem()
}