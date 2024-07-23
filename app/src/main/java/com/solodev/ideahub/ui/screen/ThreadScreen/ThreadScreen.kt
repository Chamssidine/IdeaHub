package com.solodev.ideahub.ui.screen.ThreadScreen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.solodev.ideahub.R
import com.solodev.ideahub.ui.screen.CustomSearchBar
import com.solodev.ideahub.ui.screen.components.ThreadContent
import com.solodev.ideahub.ui.screen.components.ThreadItem
import com.solodev.ideahub.ui.screen.components.UserProfile
import com.solodev.ideahub.ui.screen.components.threadItems
import java.sql.Time

@Composable
fun ThreadScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CustomSearchBar()
            LazyColumn {
                items(threadItems.size) { threadItem ->
                    ThreadItem(
                        threadItem = threadItems[threadItem],
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThreadItem(
    modifier: Modifier = Modifier,
    threadItem: ThreadItem
) {

    var expanded by remember { mutableStateOf(false) }
    var lineCount by remember { mutableStateOf(3) }
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

            UserProfile(
                modifier = Modifier,
                name = threadItem.userProfile.name,
                image = threadItem.userProfile.image,
                publicationTime = threadItem.userProfile.publicationTime,

            )
            Spacer(modifier = Modifier.height(8.dp))
            ThreadContent(
                modifier = Modifier,
                title = threadItem.threadTitle,
                threadContentText = threadItem.threadDescription,
                totalContributionCount = threadItem.contributionCount,
                expanded = expanded, lineCount = lineCount,
                defaultTextOverflowValue = defaultTextOverflowValue
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun ThreadScreenPreview()
{
    ThreadScreen()
}