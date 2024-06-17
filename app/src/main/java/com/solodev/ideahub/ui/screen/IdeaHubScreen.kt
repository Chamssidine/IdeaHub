package com.solodev.ideahub.ui.screen

import com.solodev.ideahub.ui.screen.login.LoginViewModel
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
import com.solodev.ideahub.ui.screen.login.LoginScreen
import com.solodev.ideahub.ui.screen.sign_up.SignUpScreen


@ExperimentalMaterial3Api
@Composable
fun IdeaHubScreen(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = viewModel(),
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
                    onSignUpButtonClicked = {navController.navigate(Routes.SignUp.name)},
                )
            }
            composable(Routes.SignUp.name) {
                SignUpScreen(
                    onSignUpButtonClicked = {},
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