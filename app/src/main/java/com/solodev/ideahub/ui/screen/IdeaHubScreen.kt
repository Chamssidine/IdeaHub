package com.solodev.ideahub.ui.screen

import com.solodev.ideahub.ui.screen.login.LoginViewModel
import MailConfirmationScreen
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.solodev.ideahub.R
import com.solodev.ideahub.ui.screen.communityScreen.CommunityScreen
import com.solodev.ideahub.ui.screen.components.bottomNavigationItems
import com.solodev.ideahub.ui.screen.gemini_chat.GeminiChatScreen
import com.solodev.ideahub.ui.screen.goalScreen.GoalScreen
import com.solodev.ideahub.ui.screen.login.LoginScreen
import com.solodev.ideahub.ui.screen.sign_up.SignUpScreen
import com.solodev.ideahub.ui.screen.userProfileScreen.UserProfileScreen


@ExperimentalMaterial3Api
@Composable
fun IdeaHubScreen(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {

    var selectedItem by rememberSaveable { mutableStateOf(0) }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                modifier = modifier.height(dimensionResource(id = R.dimen.spacing_small))
            ){
                bottomNavigationItems.forEachIndexed { index, bottomNavigationItem ->

                    NavigationBarItem(
                        selected = selectedItem == index
                        ,
                        onClick = {
                            selectedItem = index
                            navController.navigate(bottomNavigationItem.route)
                        },
                        icon = { 
                            BadgedBox(
                                badge = {
                                    if(bottomNavigationItem.hasNews)
                                    {
                                        Badge()
                                    }
                                }
                            ) {
                                
                                Icon(
                                    painter = if(selectedItem == index) painterResource(id = bottomNavigationItem.selectedIcon) else painterResource(id = bottomNavigationItem.unselectedIcon),
                                    contentDescription = bottomNavigationItem.title
                                )
                                
                            }
                        }
                    )
                }
            }
        }

    ) {

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
            composable(Routes.Home.name){
                GoalScreen(

                )
            }
            composable(Routes.community.name){
                CommunityScreen()

            }
            composable(Routes.gemini.name){
                GeminiChatScreen()
            }
            composable(Routes.profile.name){
                UserProfileScreen()
            }

        }


    }
}