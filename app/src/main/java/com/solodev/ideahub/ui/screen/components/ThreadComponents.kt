package com.solodev.ideahub.ui.screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.solodev.ideahub.R

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
            modifier = modifier.size(64.dp)
                .padding(dimensionResource(R.dimen.padding_small))
                .clip(MaterialTheme.shapes.small),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
            contentScale = ContentScale.Crop

        )

        Column(modifier = modifier.weight(1f)) {
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
    title: String = "Title",
    threadContentText: String = "It seems like you've entered the classic placeholder text known as “Lorem Ipsum.” This text is commonly used in design and typesetting to fill space when the actual content isn't available yet. The phrase “Lorem Ipsum” itself doesn’t have any specific meaning; it’s just a jumble of Latin-like words.",
    totalContributionCount: Int = 10,
    expanded: Boolean = false,
    lineCount: Int = 3,
    defaultTextOverflowValue: TextOverflow = TextOverflow.Ellipsis
){
    Text(
        text = title,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurface
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = threadContentText,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurface,
        maxLines = lineCount, // Set the maximum number of lines
        overflow = defaultTextOverflowValue // Add ellipsis if the text is too long
    )
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "20 Contributions",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        ElevatedButton(onClick = {}) {
            Text("Contribute")
        }
    }
}
