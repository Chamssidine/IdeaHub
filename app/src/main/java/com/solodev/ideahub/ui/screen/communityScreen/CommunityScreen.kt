package com.solodev.ideahub.ui.screen.communityScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.solodev.ideahub.R
import com.solodev.ideahub.ui.screen.CustomSearchBar

@Composable
fun CommunityScreen(
    modifier: Modifier = Modifier
){
    Column(
      modifier = modifier.fillMaxSize()
    ) {
       Box(modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))){
           CustomSearchBar()
       }
       LazyColumn() {
            item { Text(text = stringResource(id = R.string.home_idea_hub)) }
             
            item { Spacer(modifier = modifier.padding(dimensionResource(id = R.dimen.spacing_small))) }

            item{}
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