package com.solodev.ideahub.ui.screen.components

import android.util.Log
import androidx.compose.foundation.Image

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text

import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solodev.ideahub.R
import com.solodev.ideahub.model.ThreadItem


@Composable
fun UserProfileUI(
    modifier: Modifier = Modifier,
    name: String? = "Code",
    image: String? = "null",
    publicationTime: String? = "",
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    )
    {
       Log.d("UserProfileUI", name!!)
        Image(
            painter = painterResource(id = R.drawable.face_2_24px),
            contentDescription = "user_image",
            modifier = modifier
                .size(64.dp)
                .padding(dimensionResource(R.dimen.padding_small))
                .clip(MaterialTheme.shapes.small),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
            contentScale = ContentScale.Crop

        )

        Column() {
            Text(
                text = name!!,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "$publicationTime min ago",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }

}

@Composable
fun ThreadContent(
    modifier: Modifier = Modifier,
    showContributeButton: Boolean = true,
    lineCount: Int = 3,
    defaultTextOverflowValue: TextOverflow = TextOverflow.Ellipsis,

    onContribute: () -> Unit = {},
    threadItem: ThreadItem = ThreadItem()

){
    Column {
        Text(
            text = threadItem.threadTitle,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = modifier.height(8.dp))
        Text(
            text = threadItem.threadDescription,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = lineCount, // Set the maximum number of lines
            overflow = defaultTextOverflowValue // Add ellipsis if the text is too long
        )
        Spacer(modifier = modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier.fillMaxWidth()
        ) {
            Text(
                text = "${threadItem.contributionCount} ${stringResource(id = R.string.contributions)}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )

            if(showContributeButton) {
                ElevatedButton(onClick = {
                    onContribute()
                    Log.d("ThreadComponent", "clicked")
                }) {
                    Text("Contribute")
                }

            }

        }
       
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentSection(
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    ){
        Card(onClick = { /*TODO*/ }) {

            UserProfileUI()
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
            Card(onClick = { /*TODO*/ }) {
                ThreadContent(
                    modifier = modifier.padding(dimensionResource(id = R.dimen.padding_small)),

                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentSectionInput(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    onSendClick: ()-> Unit = {}
) {
    var isTyping by  rememberSaveable{ mutableStateOf(false) }
    var isCleared by  rememberSaveable{ mutableStateOf(false) }
    val scrollState = rememberScrollState()

    // Create a reference to the OutlinedTextField for observing the position of the cursor
    val textFieldFocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    LaunchedEffect(value) {
        // This will scroll the text field when the cursor moves
        scrollState.animateScrollTo(scrollState.maxValue)

    }


    Row(
        modifier = modifier.fillMaxWidth()
            .heightIn(min = 56.dp, max = 100.dp),
    ) {

        Box(modifier = modifier.fillMaxWidth())
        {
            Card(
                onClick = { /*TODO*/ },
                modifier = modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier.fillMaxWidth()
                ){
                    Box(
                        modifier = Modifier
                            .weight(3f).fillMaxWidth()
                            .heightIn(min = 56.dp, max = 80.dp) // Limit the height for scrolling
                            .verticalScroll(scrollState) // Enable scrolling
                    ) {
                        OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(dimensionResource(id = R.dimen.padding_small))
                                    .focusRequester(textFieldFocusRequester), // Ensure the text field takes full width
                            value = value,
                            textStyle = TextStyle(
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                letterSpacing = MaterialTheme.typography.bodyMedium.letterSpacing,
                                lineHeight = 20.sp

                            ),
                            onValueChange = {
                                onValueChange(it)
                                isTyping = true
                            },
                            placeholder = {
                                Text(text = stringResource(id = R.string.comment_placeholder))
                            },
                            colors = TextFieldDefaults.colors(
                                focusedPlaceholderColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                            )
                        )
                    }

                    if(isTyping) {

                        IconButton (
                            onClick = {
                            onValueChange("")
                            isTyping = false
                            isCleared = true
                            focusManager.clearFocus()
                            onSendClick()
                        } ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.send_24px),
                                    contentDescription = "ico_post_comment",
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }

                }

            }


        }

    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UserProfilePreview() {
    CommentSectionInput()
}