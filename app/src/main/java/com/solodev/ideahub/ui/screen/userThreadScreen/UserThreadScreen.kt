package com.solodev.ideahub.ui.screen.userThreadScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.solodev.ideahub.R
import com.solodev.ideahub.ui.screen.components.ThreadContent
import com.solodev.ideahub.ui.screen.components.UserProfile

@Composable
fun UserThreadScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(

        ){
            UserThreadItem()
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
        LazyColumn (
        ){
            items(4
            ) {
                CommentSection()
            }

        }
    }

}


@Composable
fun UserThreadItem(
    modifier: Modifier = Modifier,
) {
    Column {
        UserProfile()
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
        ThreadContent()
    }


}

@Composable
fun CommentSection(
    modifier: Modifier = Modifier,
) {
    ElevatedCard(

    ){
        UserProfile()
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
        ThreadContent()
    }

}
@Preview(showSystemUi = true)
@Composable
fun UserThreadScreenPreview() {
    UserThreadScreen()
}