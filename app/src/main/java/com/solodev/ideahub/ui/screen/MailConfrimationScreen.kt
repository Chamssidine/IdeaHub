import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.solodev.ideahub.R
import com.solodev.ideahub.ui.screen.InputContainer
import com.solodev.ideahub.ui.screen.login.LoginViewModel
import com.solodev.ideahub.ui.theme.IdeaHubTheme


@Composable
fun MailConfirmationScreen(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = viewModel(),
    onConfirmButtonClicked: ()->Unit = {},
    onChangeEmailClicked: ()->Unit = {},
    onResendClicked: ()->Unit = {}
) {
    var code by rememberSaveable { mutableStateOf("") }
    Column (
        modifier = Modifier
            .fillMaxSize().padding(dimensionResource(id = R.dimen.spacing_medium)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = stringResource(R.string.mail_confirmation_title),
            style = MaterialTheme.typography.displayMedium
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))
        Text(
            text = stringResource(R.string.mail_confirmation_text),
            style = MaterialTheme.typography.labelLarge
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))

        Box(Modifier.fillMaxWidth(),contentAlignment = Alignment.Center) {

            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)

            ) {
                InputContainer(
                    inputValue = code,
                    labelValue = stringResource(R.string.type_the_confirmation_code),
                    onInputValueChange = {code = it}
                )
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(top = 16.dp, bottom = 8.dp,end = 16.dp)
            ) {
                Button(
                    onClick = { onConfirmButtonClicked()},
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("Resend")
                }
            }
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))

        ElevatedButton(onClick = { onConfirmButtonClicked() },
            Modifier
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .width(dimensionResource(id = R.dimen.button_width_large))
                .height(dimensionResource(id = R.dimen.button_height_medium)),
            elevation =  ButtonDefaults.buttonElevation (dimensionResource(id = R.dimen.elevation_small))
        ) {
            Text(
                text = stringResource(id = R.string.confirm),
                style = MaterialTheme.typography.labelMedium,
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))

        TextButton(
            onClick = { onChangeEmailClicked() },
            modifier = Modifier.wrapContentSize()

        ) {
            Text(
                text = stringResource(id = R.string.modify_email),
                style = MaterialTheme.typography.labelMedium,
            )
        }
    }

}

@Composable
@Preview(showBackground = true)
fun MailConfirmationScreenPreview() {
    IdeaHubTheme {
        MailConfirmationScreen()
    }
}

