package com.solodev.ideahub.ui.screen.components

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.solodev.ideahub.R
import com.solodev.ideahub.ui.screen.InputContainer

@Composable
fun UserProfile(
    modifier: Modifier = Modifier,
    name: String = "Code",
    image: String = "null",
    publicationTime: Int = 0,
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    )
    {

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
                text = name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "${publicationTime} min ago",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }

}

@Composable
fun ThreadContent(
    modifier: Modifier = Modifier,
    title: String = "Title",
    threadContentText: String = "It seems like you've entered the classic placeholder text known as “Lorem Ipsum.” This text is commonly used in design and typesetting to fill space when the actual content isn't available yet. The phrase “Lorem Ipsum” itself doesn’t have any specific meaning; it’s just a jumble of Latin-like words.",
    totalContributionCount: Int = 10,
    expanded: Boolean = false,
    lineCount: Int = 3,
    showContributeButton: Boolean = true,
    defaultTextOverflowValue: TextOverflow = TextOverflow.Ellipsis
){
    Text(
        text = title,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurface
    )
    Spacer(modifier = modifier.height(8.dp))
    Text(
        text = threadContentText,
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
            text = "${totalContributionCount} Contributions",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        if(showContributeButton)
        {
            ElevatedButton(onClick = {}) {
                Text("Contribute")
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

            UserProfile()
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
            Card(onClick = { /*TODO*/ }) {
                ThreadContent(modifier = modifier.padding(dimensionResource(id = R.dimen.padding_small)),showContributeButton = false)
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
) {
    Row(

    ) {

        Box()
        {
            Card(
                onClick = { /*TODO*/ },
                modifier = modifier
                    .padding(dimensionResource(id = R.dimen.padding_medium))

            ) {
                TextField(
                    value = "", onValueChange = { onValueChange(it) },
                    colors = TextFieldDefaults.colors(
                        focusedPlaceholderColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,

                        )
                )
            }
            Box(modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 20.dp)) {
                ElevatedButton(onClick = {} ) {
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