package com.solodev.ideahub.ui.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.solodev.ideahub.R
import com.solodev.ideahub.ui.theme.IdeaHubTheme

@Composable
fun InputContainer(
    modifier: Modifier = Modifier,
    inputValue: String,
    onInputValueChange: (String) -> Unit = {},
    labelValue: String = stringResource(R.string.default_label),
    leadingIconValue: @Composable (() -> Unit)? = null,
    trailingIconValue: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    onKeyboardDone: () -> Unit = {},
    showLabel: Boolean = true,
    maxLines: Int = 1,

    ){
        Box(

            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
            ){
            OutlinedTextField(
                value = inputValue,
                onValueChange = onInputValueChange,
                label = { Text(text = labelValue) },
                singleLine = true,
                leadingIcon = {leadingIconValue?.invoke()},
                trailingIcon = {trailingIconValue?.invoke()},
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
//                    focusedBorderColor = Color.Transparent,
//                    unfocusedBorderColor = Color.Transparent,
                    focusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
                ),
                shape = MaterialTheme.shapes.extraLarge,
                isError = isError,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),

            )
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchBar(
    modifier:Modifier = Modifier,
) {
    SearchBar(
        leadingIcon = {Icon(imageVector = Icons.Default.Search, contentDescription = null)},
       query = "Search",
       onQueryChange = {},
       onSearch = {},
       active = false,
       onActiveChange = {}
   ) {

   }
}

@Composable
fun TestInput() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
    }
        InputContainer(
            inputValue = "value",
            onInputValueChange = {newText -> println(newText)},
            labelValue = stringResource(R.string.default_label),
            leadingIconValue = {Icon(imageVector = Icons.Default.Lock, contentDescription = null)},
            trailingIconValue = {Icon(imageVector = Icons.Default.Email, contentDescription = null)}
            )
}
@Composable
@Preview(showBackground = true)
fun InputContainerPreview()
{
    IdeaHubTheme {
        TestInput()
    }
}