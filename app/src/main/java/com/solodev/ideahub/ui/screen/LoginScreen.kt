package com.solodev.ideahub.ui.screen
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.solodev.ideahub.R
import com.solodev.ideahub.ui.theme.IdeaHubTheme
@ExperimentalMaterial3Api
@Composable
fun LoginScreen() {
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
            onClick = { },
            modifier = Modifier.align(Alignment.End),
        ) {
            Text(
                text = stringResource(id = R.string.login_forgot_password),
                style = MaterialTheme.typography.bodySmall,
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_large)))


        ElevatedButton(onClick = { /*TODO*/ },
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

        SocialMediaSection()


        Spacer(modifier = Modifier
            .height(dimensionResource(id = R.dimen.spacing_large))
        )

        TextButton(
            onClick = { /* Handle sign up action */ },
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputContainer(
    modifier: Modifier = Modifier,
    inputValue: String,
    onInputValueChange: (String) -> Unit = {},
    @StringRes labelValue: Int = R.string.default_label,
    leadingIconValue: @Composable (() -> Unit)? = null,
    trailingIconValue: @Composable (() -> Unit)? = null,

    )
{
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.card_height_large)),
        shape = MaterialTheme.shapes.extraLarge,

    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
            //modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_small))
        ){
            OutlinedTextField(
                value = inputValue,
                onValueChange = {newText -> onInputValueChange(newText)},
                label = { Text(text = stringResource(labelValue)) },
                modifier = Modifier
                    .wrapContentHeight().fillMaxWidth(),
                singleLine = true,
                leadingIcon = {leadingIconValue?.invoke()},
                trailingIcon = {trailingIconValue?.invoke()},
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
                )
            )
        }


    }

}
@ExperimentalMaterial3Api
@Composable
fun SocialMediaSection(modifier: Modifier = Modifier)
{
    Row(modifier.wrapContentWidth(),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing_medium)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SocialMediaIcon(icon = R.drawable.gmail)
        SocialMediaIcon(icon = R.drawable.facebook)
        SocialMediaIcon(icon = R.drawable.twitter__1_)
    }

}

@ExperimentalMaterial3Api
@Composable
fun SocialMediaIcon(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int
){
    ElevatedCard(
        onClick = {  },
        modifier = modifier
            .size(dimensionResource(id = R.dimen.icon_size_medium))
    ) {

        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier.padding(dimensionResource(id = R.dimen.padding_small))
        )
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
