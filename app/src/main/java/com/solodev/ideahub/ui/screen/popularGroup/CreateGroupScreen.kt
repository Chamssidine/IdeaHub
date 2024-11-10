package com.solodev.ideahub.ui.screen.popularGroup

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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text


import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.solodev.ideahub.R
import com.solodev.ideahub.ui.screen.InputContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateGroupScreen(
    CreateGroupViewModel: CreateGroupViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = { CreateGroupTopAppBar() }
    )
    { innerPadding ->
        val innerPadding = innerPadding
       Column(
           modifier = Modifier
               .fillMaxSize()
               .padding(innerPadding),
           verticalArrangement = Arrangement.SpaceBetween,
           horizontalAlignment = Alignment.CenterHorizontally
       ){
           ElevatedCard(
               modifier = Modifier
                   .padding(dimensionResource(id = R.dimen.padding_medium))
                  ,
               shape = MaterialTheme.shapes.medium

           ) {
               Column {
                   Card(
                       modifier = Modifier.fillMaxWidth(),
                       shape = MaterialTheme.shapes.medium,

                   ) {
                       Box(
                           modifier = Modifier
                               .padding(dimensionResource(id = R.dimen.padding_medium))
                               .fillMaxWidth()
                               .height(120.dp),
                           contentAlignment = Alignment.Center
                       ) {
                           Icon(
                               modifier = Modifier
                                   .wrapContentSize()
                                   .align(Alignment.Center),
                               painter = painterResource(id = R.drawable.add_a_photo_24px), contentDescription = "null")

                       }


                   }
                   Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))
                   InputContainer(
                       modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
                       inputValue = "",
                       labelValue = "Group Name",
                       onInputValueChange = {

                       }
                   )
                   Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))
                   InputContainer (
                       modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
                       inputValue = "",
                       labelValue = "Group Description",
                       onInputValueChange = {}
                   )
                   Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))
                    Text(
                        text = stringResource(id = R.string.privacy_settings),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))

                    )
                   Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))
                   ElevatedButton(
                       onClick = { /*TODO*/ },
                       modifier = Modifier
                           .fillMaxWidth()
                           .padding(dimensionResource(id = R.dimen.padding_medium))
                           .height(50.dp),
                   ){
                       Text(text = stringResource(id =R.string.select_privacy))
                   }


               }
           }
           
           Spacer(modifier = Modifier.weight(1f))
           ElevatedButton(
               onClick = { /*TODO*/ },
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(16.dp)
                   .height(50.dp),
           ) {
               Text(text = "Next")
               
           }
           Spacer(modifier = Modifier.weight(0.1f))
       }
    }
}

@Composable
fun CreateGroupTopAppBar(
    modifier: Modifier = Modifier,
    onBackPress: () -> Unit = {},
    title: String = ""
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,

    ) {
        ElevatedCard(
            shape = MaterialTheme.shapes.extraLarge,
            modifier = Modifier.size(50.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "null",
                )
            }

        }

       Text(
          text = title, style = MaterialTheme.typography.titleLarge
      )

    }
}

@Preview(showBackground = true)
@Composable
fun CreateGroupScreenPreview() {
    CreateGroupScreen()
}