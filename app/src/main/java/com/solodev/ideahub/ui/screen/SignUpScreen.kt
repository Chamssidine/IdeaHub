@file:OptIn(ExperimentalMaterial3Api::class)

package com.solodev.ideahub.ui.screen

import com.solodev.ideahub.ui.screen.login.LoginViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.solodev.ideahub.R
import com.solodev.ideahub.ui.theme.IdeaHubTheme

@ExperimentalMaterial3Api
@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = viewModel(),
    onSignUpButtonClicked: () -> Unit = {},
    onLoginButtonClicked: () -> Unit = {},
    onShowPasswordClicked: () -> Unit = {},
    onSignUpWithSocialMediaClicked: () -> Unit = {},
) {

    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_medium)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))
        Text(
            text = stringResource(id = R.string.create_your_account),
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_large))
        )

        InputContainer(
            inputValue = fullName,
            leadingIconValue = { Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null) },
            labelValue = stringResource(R.string.full_name),
            onInputValueChange = {fullName = it}
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))

        InputContainer(inputValue = email,
                leadingIconValue = { Icon(imageVector = Icons.Default.Email, contentDescription = null) },
            labelValue = stringResource(R.string.email),
            onInputValueChange = {email  = it}
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))


        InputContainer(inputValue = password,
            leadingIconValue = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
            trailingIconValue = { Icon(painterResource(id = R.drawable.baseline_visibility_black_24), contentDescription = null) },
            labelValue = stringResource(R.string.password),
            onInputValueChange = {password  = it}
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_large)))


        ElevatedButton(onClick = { onSignUpButtonClicked() },
            Modifier
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .width(dimensionResource(id = R.dimen.button_width_large))
                .height(dimensionResource(id = R.dimen.button_height_medium)),
            elevation =  ButtonDefaults.buttonElevation (dimensionResource(id = R.dimen.elevation_small))
        ) {
            Text(
                text = stringResource(id = R.string.create),
                style = MaterialTheme.typography.labelMedium,
                fontSize = 20.sp
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_large)))

        Text(
            text = stringResource(id = R.string.or_create_using),
            style = MaterialTheme.typography.labelLarge,
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_large)))

        SocialMediaSection(onSocialMediaButtonClicked = onSignUpWithSocialMediaClicked)
        Spacer(modifier = Modifier
            .height(dimensionResource(id = R.dimen.spacing_large))
        )

        Row (

        ) {
            Text(
                text = stringResource(id = R.string.already_have_account),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            TextButton(
                onClick = onLoginButtonClicked,
                modifier = Modifier.wrapContentSize()

            ) {
                Text(
                    text = stringResource(id = R.string.sign_in),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(end = dimensionResource(id = R.dimen.padding_large))
                )
            }
        }

    }
}
@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun Preview() {
    IdeaHubTheme {
        SignUpScreen()
    }
}


