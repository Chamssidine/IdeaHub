package com.solodev.ideahub.ui.screen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.solodev.ideahub.R
import com.solodev.ideahub.ui.ViewModels.ConnectionViewModel
import com.solodev.ideahub.ui.theme.IdeaHubTheme
@ExperimentalMaterial3Api
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    connectionViewModel: ConnectionViewModel = viewModel(),
    onLoginButtonClicked: () -> Unit = {},
    onForgotPasswordButtonClicked: () -> Unit = {},
    onSignUpButtonClicked: () -> Unit = {},
    onSocialMediaButtonClicked: () -> Unit = {},
) {
    var emailOrPhone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_medium)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.login_welcome),
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.spacing_large))
        )

        InputContainer(
            inputValue = emailOrPhone,
            leadingIconValue = {Icon(imageVector = Icons.Default.Phone, contentDescription = null)},
            labelValue = R.string.login_phone_email,
            onInputValueChange = {emailOrPhone = it}
            )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))

        InputContainer(inputValue = password,
            leadingIconValue = {Icon(imageVector = Icons.Default.Lock, contentDescription = null)},
             labelValue = R.string.login_password,
            onInputValueChange = {password = it}
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))


        TextButton(
            onClick = { onForgotPasswordButtonClicked() },
            modifier = Modifier.align(Alignment.End),
        ) {
            Text(
                text = stringResource(id = R.string.login_forgot_password),
                style = MaterialTheme.typography.bodySmall,
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_large)))


        ElevatedButton(onClick = { onLoginButtonClicked()},
            Modifier
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .width(dimensionResource(id = R.dimen.button_width_large))
            ) {
            Text(
                text = stringResource(id = R.string.login_button),
                style = MaterialTheme.typography.labelMedium,
                fontSize = 20.sp
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_large)))

        Text(
            text = stringResource(id = R.string.login_with_social_media),
            style = MaterialTheme.typography.bodySmall,
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_large)))
        SocialMediaSection(onSocialMediaButtonClicked =  onSocialMediaButtonClicked)
        Spacer(modifier = Modifier
            .height(dimensionResource(id = R.dimen.spacing_large))
        )

        TextButton(
            onClick = { onSignUpButtonClicked() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = stringResource(id = R.string.login_sign_up,
                ),
                style = MaterialTheme.typography.bodyMedium
            )
        }

    }
}


@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun PreviewLogin() {
    IdeaHubTheme {
        LoginScreen()
    }
}
