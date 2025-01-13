package com.solodev.ideahub.ui.screen.userThreadScreen

import android.os.Build
import android.util.Log

import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row


import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
 
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.List
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Star

import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable

import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.solodev.ideahub.R
import com.solodev.ideahub.model.Comment
import com.solodev.ideahub.model.ThreadItem
import com.solodev.ideahub.ui.screen.components.CommentSectionInput
import com.solodev.ideahub.ui.screen.components.ThreadContent
import com.solodev.ideahub.ui.screen.components.UserProfileUI
import com.solodev.ideahub.util.UserHandler
import kotlinx.coroutines.launch
import androidx.compose.runtime.LaunchedEffect as LaunchedEffect

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ThreadDetailsScreen(
    modifier: Modifier = Modifier,
    threadViewModel: UserThreadViewModel
) {
    val uiState by threadViewModel.comments.collectAsStateWithLifecycle()
    val threadUIState by threadViewModel.threadItemUiState.collectAsStateWithLifecycle()

    val paddingMedium = dimensionResource(id = R.dimen.padding_medium)
    val paddingSmall = dimensionResource(id = R.dimen.padding_small)
    val scrollState = rememberScrollState()
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }
    var userMention by rememberSaveable { mutableStateOf("")}
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(key1 = threadUIState.selectedThread) {
        threadViewModel.observeComments(threadUIState.selectedThread)
        lazyListState.animateScrollToItem(0)
    }
    Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                ),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                state = lazyListState
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primary)
                    ) {
                        ElevatedCard(
                            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
                            shape = MaterialTheme.shapes.small
                        ) {
                            UserThreadItem(
                                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
                                threadItem = threadUIState.selectedThread
                            )
                        }
                    }
                }


                items(
                    items = threadUIState.selectedThread.comments,
                    key = { it.commentId},
                    contentType = { "comment" }
                ) { comment ->
                    CommentItem(
                        modifier = Modifier
                            .padding(
                                start = paddingMedium,
                                end = paddingMedium,
                                top = paddingSmall,
                                bottom = paddingSmall
                            )
                            .animateContentSize(
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioNoBouncy,
                                    stiffness = Spring.StiffnessLow
                                )
                            ),
                        showImage = false,
                        comment = comment,
                        onRespond = {
                                threadViewModel.onRespond(it)
                                focusRequester.requestFocus()
                                keyboardController?.show()

                        }
                    )
                }

            }

            CommentSectionInput(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(2.dp)
                    .heightIn(min = 50.dp, max = 60.dp)
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
                onSendClick = {
                    threadViewModel.publishComment(threadUIState.selectedThread, uiState.commentText)
                    coroutineScope.launch {
                    scrollState.animateScrollTo(0) // Scroll to the top
                        lazyListState.animateScrollToItem(0) // Scroll to the top
                 }
                },
                focusRequester = focusRequester

            )
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CommentItem(
    modifier: Modifier = Modifier,
    showImage: Boolean = true,
    comment: Comment = Comment(),
    onRespond: (String) -> Unit = {},
) {
    var expanded by remember { mutableStateOf(false) }
    var lineCount by remember { mutableIntStateOf(3) }
    var defaultTextOverflowValue by remember { mutableStateOf(TextOverflow.Ellipsis) }
    var showSheet  by rememberSaveable {
        mutableStateOf(false)
    }
    ElevatedCard(
            modifier = modifier.combinedClickable(
                onLongClick = {
                    showSheet = true
                    Log.d("TAY","Long click")
                },
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
                }

            )

        ,

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
                    Row(
                        modifier = Modifier
                            .clickable(
                                onClick = {

                                }
                            )
                            .padding(8.dp)
                        ,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)

                    ) {
                        Icon( painter = painterResource(id = R.drawable.thumb_up_24px), contentDescription = stringResource(id = R.string.edit_comment))
                        Text(text = stringResource(id = R.string.like), style = MaterialTheme.typography.bodyMedium)

                    }
                    Row(
                        modifier = Modifier
                            .clickable(
                                onClick = {

                                }
                            )
                            .padding(8.dp)
                        ,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)

                    ) {
                        Icon( painter = painterResource(id = R.drawable.reply_24px), contentDescription = stringResource(id = R.string.respond))
                        Text(text = stringResource(id = R.string.respond), style = MaterialTheme.typography.bodyMedium)

                    }
                    Row(
                        modifier = Modifier
                            .clickable(
                                onClick = {
                                    onRespond(comment.userProfile.name)
                                }
                            )
                            .padding(8.dp)
                        ,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)

                    ) {
                        Icon( painter = painterResource(id = R.drawable.star_24px), contentDescription = stringResource(id = R.string.star))
                        Text(text = stringResource(id = R.string.star), style = MaterialTheme.typography.bodyMedium)

                    }
                    if(comment.userProfile.id != UserHandler.getCurrentuser().id)
                    {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    onClick = {

                                    }
                                )
                                .padding(8.dp)
                            ,
                            verticalAlignment = Alignment.CenterVertically,

                        ) {
                            Text(text = stringResource(id = R.string.respond), style = MaterialTheme.typography.bodyMedium)

                        }
                    }



                }

            }

        }

    if(showSheet)
    {
        CommentSectionSheet(
            onDismiss = {
                showSheet = false
            }
        )
    }




}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentSectionSheet(
    onDelete: () -> Unit = {},
    onEdit: () -> Unit = {},
    onDismiss: () -> Unit = {}
){
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(16.dp) ,
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        onClick = {
                            onEdit()
                        }
                    )
                    .padding(
                        top = dimensionResource(id = R.dimen.padding_medium),
                        bottom = dimensionResource(id = R.dimen.padding_medium)
                    )
                ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)

            ) {
                Icon( Icons.Filled.Edit, contentDescription = stringResource(id = R.string.edit_comment))
                Text(
                    text = stringResource(id = R.string.edit_comment), style = MaterialTheme.typography.bodyMedium,
                )

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        onClick = {
                            onEdit()
                        }
                    )
                    .padding(
                        top = dimensionResource(id = R.dimen.padding_medium),
                        bottom = dimensionResource(id = R.dimen.padding_medium)
                    )
                ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)

            ) {
                Icon( painter = painterResource(id = R.drawable.keep_24px), contentDescription = stringResource(id = R.string.edit_comment))
                Text(text = stringResource(id = R.string.pin), style = MaterialTheme.typography.bodyMedium)

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        onClick = {
                            onDelete()
                        }
                    )
                    .padding(
                        top = dimensionResource(id = R.dimen.padding_medium),
                        bottom = dimensionResource(id = R.dimen.padding_medium)
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)

            ) {
                Icon( Icons.Filled.Delete, contentDescription = stringResource(id = R.string.delete), tint = MaterialTheme.colorScheme.error)

                Text(text = stringResource(id = R.string.delete), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.error)


            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        onClick = {
                            onDelete()
                        }
                    )
                    .padding(
                        top = dimensionResource(id = R.dimen.padding_medium),
                        bottom = dimensionResource(id = R.dimen.padding_medium)
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)

            ) {
                Icon( Icons.Filled.Warning, contentDescription = stringResource(id = R.string.delete), tint = MaterialTheme.colorScheme.error)

                Text(text = stringResource(id = R.string.report), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.error)


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