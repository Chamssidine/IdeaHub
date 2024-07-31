 package com.solodev.ideahub.ui.screen

import com.solodev.ideahub.ui.screen.login.LoginViewModel
import MailConfirmationScreen
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.solodev.ideahub.ui.ViewModels.ScreenManagerVM
import com.solodev.ideahub.ui.screen.communityScreen.CommunityScreen
import com.solodev.ideahub.ui.screen.components.bottomNavigationItems
import com.solodev.ideahub.ui.screen.gemini_chat.GeminiChatScreen
import com.solodev.ideahub.ui.screen.goalCreationScreen.GoalCreationScreen
import com.solodev.ideahub.ui.screen.goalScreen.GoalScreen
import com.solodev.ideahub.ui.screen.login.LoginScreen
import com.solodev.ideahub.ui.screen.sign_up.SignUpScreen
import com.solodev.ideahub.ui.screen.userProfileScreen.UserProfileScreen
import com.solodev.ideahub.ui.screen.welcomeScreen.WelcomeScreen



@ExperimentalMaterial3Api
@Composable
fun IdeaHubScreen(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel(),
    screenManagerVM: ScreenManagerVM = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    val uiState by screenManagerVM.uiState.collectAsState()
    var selectedItem by rememberSaveable { mutableStateOf(0) }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            if(uiState.showBottomNavigationBar)
            {
                NavigationBar(
                    modifier = modifier.height(60.dp)
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

        }
    ) {
            innerPadding ->
        val innerPadding = innerPadding
        NavHost(
            navController = navController,
            startDestination = uiState.destination,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.SignUp.name) {
                SignUpScreen(
                    onSignUpButtonClicked = {
                        if(uiState.isFirstLaunch)
                            navController.navigate(Routes.Welcome.name)
                        else
                            navController.navigate(Routes.GoalAndTasks.name) },
                    onLoginButtonClicked = {navController.navigate(Routes.Login.name)}
                )

            }
            composable(Routes.Login.name) {
                LoginScreen(
                    onLoginButtonClicked = {
                        navController.navigate(Routes.GoalAndTasks.name)
                        screenManagerVM.showBottomNavigationBar(true)
                    },
                    onForgotPasswordButtonClicked = {

                    },
                    onSignUpButtonClicked = {navController.navigate(Routes.SignUp.name)}
                )
            }
            composable(Routes.MailConfirmation.name) {
                MailConfirmationScreen(
                    onConfirmButtonClicked = {navController.navigate(Routes.Login.name)},
                    onChangeEmailClicked = {navController.navigate(Routes.SignUp.name)}
                )
            }

            composable(Routes.Community.name){
                CommunityScreen()

            }
            composable(Routes.Gemini.name){
                GeminiChatScreen()
            }
            composable(Routes.Profile.name){
                UserProfileScreen()
            }
            composable(Routes.GoalAndTasks.name){
                GoalScreen()
            }
            composable(Routes.GoalCreation.name){
                GoalCreationScreen(
                    onGoalCreationButtonClicked = {
                        navController.navigate(Routes.GoalAndTasks.name)
                        screenManagerVM.showBottomNavigationBar(true)
                    }
                )
            }
            composable(Routes.Welcome.name){
                WelcomeScreen(
                    onLetsGoClicked = {

                        navController.navigate(Routes.GoalCreation.name)

                    }
                )
            }
            composable(Routes.Thread.name){
                GoalScreen()
            }
            composable(Routes.ThreadHistory.name) {
                GoalScreen()
            }
        }

    }
}