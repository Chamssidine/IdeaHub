package com.solodev.ideahub.ui.screen.popularGroup

import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.solodev.ideahub.R
import com.solodev.ideahub.ui.screen.components.CircleShape
import com.solodev.ideahub.ui.screen.components.FloatingButton
import com.solodev.ideahub.ui.screen.components.TextArea


@Composable
fun CreateGroupConfirmationScreen(
    onBackPress: () -> Unit = {},
    onConfirm: () -> Unit = {},
    viewModel: PopularGroupViewModel = hiltViewModel<PopularGroupViewModel>()
) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = { CreateGroupTopAppBar(title = stringResource(id = R.string.create_group), onBackPress = onBackPress) },
        floatingActionButton = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.padding_medium)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing_medium)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FloatingActionButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.align(Alignment.End),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.group_add_24px),
                        contentDescription = "add members"
                    )
                }
                ElevatedButton(
                    onClick = { /*TODO*/ },
                    modifier= Modifier
                        .fillMaxWidth()
                        .align(Alignment.End)
                        .height(50.dp),

                    ) {
                    Text(text = stringResource(id = R.string.create))
                }
            }

        },
        floatingActionButtonPosition = FabPosition.Center
    ){
        innerPadding ->
            val innerPadding = innerPadding
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),

            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ElevatedCard(
                    modifier = Modifier
                        .wrapContentSize()
                        .size(150.dp),
                    shape = CircleShape(),
                    onClick = {

                    }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {

                        AsyncImage(
                           model = "",
                            contentDescription = "group image",
                            modifier = Modifier.fillMaxSize()
                        )

                    }


                }
                Text(
                   text = stringResource(id = R.string.group_name),
                    style = MaterialTheme.typography.bodyLarge,

                )
                TextButton(onClick = { /*TODO*/ }) {
                    Text(text = stringResource(id = R.string.view_group_details))
                }

                GroupSettingSection()


            }

        }

    }
}

@Composable
fun GroupSettingSection(
modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        Text(
            text = stringResource(id = R.string.group_settings),
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier
        )
        TextArea(
            text = "",
            label = stringResource(id = R.string.community),
            onTextChange = {},
            modifier = modifier.fillMaxWidth()
        )
        TextArea(
            text = "",
            label = stringResource(id = R.string.max_members_count),
            onTextChange = {},
            modifier = modifier.fillMaxWidth()
        )
    }
}
@Preview
@Composable fun CreateGroupConfirmationScreenPreview() {
    CreateGroupConfirmationScreen(
        onBackPress = {},
        onConfirm = {}
    )
}
