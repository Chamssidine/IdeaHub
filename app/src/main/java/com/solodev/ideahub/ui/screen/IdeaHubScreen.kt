package com.solodev.ideahub.ui.screen

import MailConfirmationScreen
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.solodev.ideahub.ui.ViewModels.ConnectionViewModel

@ExperimentalMaterial3Api
@Composable
fun IdeaHubScreen(
    modifier: Modifier = Modifier,
    connectionViewModel: ConnectionViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {


    Scaffold(modifier = modifier.fillMaxSize()) {

            innerPadding ->
        val innerPadding = innerPadding
        NavHost(
            navController = navController,
            startDestination = Routes.Login.name
        ) {
            composable(Routes.Login.name) {
                LoginScreen(
                    onLoginButtonClicked = {navController.navigate(Routes.mail_Confirmation.name)},
                    onSignUpButtonClicked = {navController.navigate(Routes.SignUp.name)}
                )
            }
            composable(Routes.SignUp.name) {
                SignUpScreen(
                    onSignUpButtonClicked = {navController.navigate(Routes.mail_Confirmation.name)},
                    onLoginButtonClicked = {navController.navigate(Routes.Login.name)}
                )


            }
            composable(Routes.mail_Confirmation.name) {
                MailConfirmationScreen(
                    onConfirmButtonClicked = {navController.navigate(Routes.Login.name)},
                    onChangeEmailClicked = {navController.navigate(Routes.SignUp.name)}
                )
            }

        }


    }
}