package com.solodev.ideahub.ui.screen.userThreadScreen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.solodev.ideahub.R
import com.solodev.ideahub.ui.screen.components.CommentSectionInput
import com.solodev.ideahub.ui.screen.components.ThreadContent
import com.solodev.ideahub.ui.screen.components.UserProfile

@Composable
fun UserThreadScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize()
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
                modifier = modifier.fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)

            ) {
                ElevatedCard(
                    modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
                    shape = MaterialTheme.shapes.small
                ){
                    UserThreadItem(modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)))
                }
            }

            repeat(12){
                CommentSection(
                    modifier = modifier
                        .padding(start = dimensionResource(id = R.dimen.padding_medium)
                            , end = dimensionResource(id = R.dimen.padding_medium)
                            , top = dimensionResource(id = R.dimen.padding_small)
                        ).animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    ),
                    showImage = false
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
            }

        }
        Box(
            modifier = modifier.align(Alignment.BottomCenter)
        )
        {
            CommentSectionInput(modifier = modifier.fillMaxWidth().animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ))
        }

    }

}


@Composable
fun UserThreadItem(
    modifier: Modifier = Modifier,
) {
    Column (modifier = modifier){
        UserProfile()
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
        ThreadContent(showContributeButton = false)
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentSection(
    modifier: Modifier = Modifier,
    showImage: Boolean = true,
    image : String = "null",
) {
    var expanded by remember { mutableStateOf(false) }
    var lineCount by remember { mutableStateOf(3) }
    var defaultTextOverflowValue by remember { mutableStateOf(TextOverflow.Ellipsis) }
    Box()
    {
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
                UserProfile(modifier = Modifier.wrapContentSize())

                Text(
                    modifier = modifier.padding( end = 16.dp, bottom = 28.dp),
                    text = "the time before time, when the stars were young," +
                            " the Elder Gods walked the earth" +
                            " Whispers tell of a hidden city," +
                            " shrouded in mist, where the veil " +
                            "between worlds is thin",
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

            }

        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, top = dimensionResource(id = R.dimen.padding_medium))
                .wrapContentSize()
        )
        {
            ElevatedButton(
                onClick = { /*TODO*/ },
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(painter = painterResource(id = R.drawable.thumb_up_24px), contentDescription = "null")

            }
        }
    }


}
@Preview(showSystemUi = true)
@Composable
fun UserThreadScreenPreview() {
    UserThreadScreen()
}