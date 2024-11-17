package com.solodev.ideahub.ui.screen.popularGroup

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState


import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.solodev.ideahub.R
import com.solodev.ideahub.model.PrivacyLevel
import com.solodev.ideahub.model.privacy
import com.solodev.ideahub.ui.screen.InputContainer
import com.solodev.ideahub.ui.screen.components.CircleShape
import kotlinx.coroutines.launch


@Composable
fun CreateGroupScreen(
    viewModel: PopularGroupViewModel = hiltViewModel<PopularGroupViewModel>(),
    onNextButtonCLicked: () -> Unit = {},
    onBackPress: () -> Unit = {}
) {
    var showBottomSheet by remember {
        mutableStateOf(false)
    }
    val uiState by viewModel.groupItemUIState.collectAsState()
    Scaffold(
        topBar = { CreateGroupTopAppBar(title = stringResource(id = R.string.create_group), onBackPress = onBackPress) }
    )
    { innerPadding ->
        val innerPadding = innerPadding
       Column(
           modifier = Modifier
               .fillMaxSize()
               .padding(innerPadding)
               .verticalScroll(rememberScrollState()),

           verticalArrangement = Arrangement.SpaceBetween,
           horizontalAlignment = Alignment.CenterHorizontally
       ){
           Column (
               horizontalAlignment = Alignment.CenterHorizontally
           ){
               ElevatedCard(
                   modifier = Modifier
                       .wrapContentSize()
                       .size(150.dp),
                   shape = CircleShape (),
                   onClick = {

                   }
                   ) {
                   Box(
                       modifier = Modifier
                        .fillMaxSize()
                           ,
                       contentAlignment = Alignment.Center
                   ) {

                       Icon( painter = painterResource(id = R.drawable.add_a_photo_24px), contentDescription = "null")

                   }


               }
               InputContainer(
                   modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)),
                   inputValue = uiState.groupName,
                   labelValue = stringResource(id = R.string.group_name),
                   onInputValueChange = {
                       viewModel.updateGroupName(it)
                   },
                   maxLines = 1,
                   isError = true,
               )
               InputContainer (
                   modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)),
                   inputValue = uiState.groupDescription,
                   labelValue = stringResource(id = R.string.group_description),
                   onInputValueChange = {
                       viewModel.updateDescription(it)
                   },
                   maxLines = 2,
                   isError = uiState.hasError,
               )
               Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))
               Text(
                   text = stringResource(id = R.string.privacy_settings),
                   style = MaterialTheme.typography.bodyLarge,
                   modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))

               )
               Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))
               TextButton(
                   onClick = {
                       showBottomSheet = true
                   },
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(dimensionResource(id = R.dimen.padding_medium))
                       .height(50.dp),
               ){
                   Text(text = if(uiState.privacy != null) uiState.privacy!!.name else stringResource(id =R.string.select_privacy))
               }


           }

           
           Spacer(modifier = Modifier.weight(1f))
           ElevatedButton(
               onClick = {
                   if(viewModel.checkInputs())
                       onNextButtonCLicked()
               },
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(16.dp)
                   .height(50.dp),
           ) {
               Text(text = "Next")
               
           }
           Spacer(modifier = Modifier.weight(0.1f))

           if(showBottomSheet){
               PrivacyBottomSheet(
                   onDissmiss = {showBottomSheet = false},
                   onPrivacySelected = {
                        viewModel.updatePrivacy(it)
                   }
               )
           }
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
            .padding(start = dimensionResource(id = R.dimen.padding_medium)),
        verticalAlignment = Alignment.CenterVertically,

    ) {
        Box(
            modifier = Modifier.background(Color.Transparent),
            contentAlignment = Alignment.Center
        ){
            IconButton(onClick = onBackPress) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "null",
                )
            }

        }
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_small)))

       Text(
          text = title, style = MaterialTheme.typography.displayMedium,
           textAlign = TextAlign.Start,
      )

    }
}

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyBottomSheet(
    onDissmiss: () -> Unit = {},
    onPrivacySelected: (privacyLevel: PrivacyLevel) -> Unit
){
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var selected by remember {
        mutableStateOf(false)
    }
    var description by remember { mutableStateOf("")}
    val options = privacy.privacy
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(PrivacyLevel.NONE) }
    ModalBottomSheet(
            onDismissRequest = {
                onDissmiss()
            },
            sheetState = sheetState
        ) {
            Column {
                 options.map{
                     index ->
                     Row(
                         Modifier
                             .fillMaxWidth()
                             .selectable(
                                 selected = (index.key == selectedOption),
                                 onClick = {
                                     onOptionSelected(index.key)
                                     onPrivacySelected(index.key)
                                     selected = true
                                     description = index.value

                                 }
                             ),
                         verticalAlignment = Alignment.CenterVertically,


                     ){
                         RadioButton(
                             selected = (index.key == selectedOption),
                             onClick = {
                                 onOptionSelected(index.key)
                                 onPrivacySelected(index.key)
                                 selected = true
                                 description = index.value
                             }
                         )
                         Text (
                             text = index.key.name,
                             style = MaterialTheme.typography.bodyMedium,
                         )
                     }

                 }
                
                if(selected)
                {
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))

                    Button(
                        onClick = {

                            scope
                                .launch { sheetState.hide() }
                                .invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        onDissmiss()
                                    }
                                }
                        },
                        modifier = Modifier
                            .padding(dimensionResource(id = R.dimen.padding_medium))
                            .fillMaxWidth()
                    ) {
                        Text(text = stringResource(id = R.string.confirm))
                    }
                }
            }
               

    }


}

@Preview(showBackground = true)

@Composable
fun CreateGroupScreenPreview() {
    CreateGroupScreen()
}